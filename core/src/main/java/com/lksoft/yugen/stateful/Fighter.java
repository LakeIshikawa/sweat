package com.lksoft.yugen.stateful;

import com.badlogic.gdx.Gdx;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsm.FighterExecuteVisitor;
import com.lksoft.yugen.fsm.FighterExpVisitor;
import com.lksoft.yugen.fsm.Type;
import com.lksoft.yugen.fsm.Value;
import com.lksoft.yugen.stateless.AnimationDef;
import com.lksoft.yugen.stateless.FighterDef;
import com.lksoft.yugen.stateless.FighterState;
import com.lksoft.yugen.stateless.PhysicsDef;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lake on 11/06/2016.
 */
public class Fighter extends Sprite {

    // Fighter def
    private FighterDef fighterDef;
    // Stage
    private Stage stage;

    // Fighter layer
    private int layer = 1;
    // Current state
    private FighterState currentState;

    // Current physics
    private PhysicsDef currentPhysics;

    // The memory
    private HashMap<String, Value> memory = new HashMap<>();

    // Expression evaluator
    private FighterExpVisitor evaluator = new FighterExpVisitor(this);
    // Execution visitor (shared)
    private FighterExecuteVisitor executor;


    // Flag for noticing statechange
    private boolean stateChanged;

    /**
     * Create b1 fighter by parsing b1 .def script file
     */
    public Fighter(FighterDef fighterDef) throws IOException {
        super(null);
        this.fighterDef = fighterDef;
        this.executor = new FighterExecuteVisitor(this, evaluator);

        // Set system variables
        setVar("ctrl", Type.BOOL, true);
        setVar("alive", Type.BOOL, true);
        setVar("time", Type.INT, 0);
        setVelX(0);
        setVelY(0);

        // Params
        for(Map.Entry<String, Float> e : fighterDef.getParams().entrySet()){
            setVar(e.getKey(), Type.FLOAT, e.getValue());
        }

        // Set idle state
        changeState("idle");
    }

    /**
     * Sets the stage
     * @param stage The parent stage
     */
    void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * @return Whether this fighter is p1 or not
     */
    public boolean isP1(){
        return stage.getP1() == this;
    }

    /**
     * Gets the opponent
     * @return The opponent fighter
     */
    public Fighter getOpponent() { return isP1() ? stage.getP2() : stage.getP1(); }

    /**
     * Sets variable value
     * @param name
     * @param value
     */
    public void setVar(String name, Type type, Object value){
        if( memory.containsKey(name) ) {
            memory.get(name).set(type, value);
        }
        else {
            memory.put(name, new Value(type, value));
        }
    }

    /**
     * Set variable value (copy)
     * @param name
     * @param value
     */
    public void setVar(String name, Value value){
        switch (value.getType()){
            case ID: setVar(name, value.getType(), value.getIdValue()); break;
            case STRING: setVar(name, value.getType(), value.getStringValue()); break;
            case BOOL: setVar(name, value.getType(), value.getBoolValue()); break;
            case FLOAT: setVar(name, value.getType(), value.getFloatValue()); break;
            case INT: setVar(name, value.getType(), value.getIntValue()); break;
            case ANIM: setVar(name, value.getType(), value.getAnimationValue()); break;
            case PHYSICS: setVar(name, value.getType(), value.getPhysicsValue()); break;
        }
    }

    /**
     * Gets var value
     * @param name
     * @return
     */
    public Value getVar(String name){
        return memory.get(name);
    }

    @Override
    public void update(){
        stateChanged = false;

        // Evaluate all triggers
        for(FighterState.FighterTrigger t : currentState.triggers) {
            t.run(executor, evaluator);
            if( stateChanged ) break;
        }

        if( !stateChanged ) {
            // Also stateless triggers
            for (FighterState.FighterTrigger t : getFighterDef().getTriggers()) {
                t.run(executor, evaluator);
            }
        }

        // Run physics triggers
        if( currentPhysics != null ) {
            for (FighterState.FighterTrigger t : currentPhysics.getTriggers()) {
                t.run(executor, evaluator);
            }
        }

        super.update();

        // Update system vars
        setVar("vel.x", Type.FLOAT, vel.x);
        setVar("vel.y", Type.FLOAT, vel.y);
        setVar("pos.x", Type.FLOAT, pos.x);
        setVar("pos.y", Type.FLOAT, pos.y);
        setVar("facing", Type.BOOL, flip);
        setVar("animTime", Type.INT, animation.getTicks());
        setVar("animCycles", Type.INT, animation.getCycles());
        setVar("time", Type.INT, getVar("time").getIntValue()+1);
    }

    /**
     * Change state
     * @param stateName
     */
    public void changeState(String stateName) {
        FighterState newState = getFighterDef().getStates().get(stateName);
        if( currentState == newState ) return;
        stateChanged = true;

        Gdx.app.log("FSM", "-> "+stateName);

        // Execute exit triggers
        if( currentState != null && currentState.exitTrigger != null ) {
            for (FsmParser.StatementContext s : currentState.exitTrigger) {
                s.accept(executor);
            }
        }

        // Change state
        currentState = newState;

        // Execute enter triggers
        if( currentState.enterTrigger != null ) {
            for (FsmParser.StatementContext s : currentState.enterTrigger) {
                s.accept(executor);
            }
        }
    }

    /**
     * Change animation
     * @param def Animation def
     */
    public void changeAnimation(AnimationDef def){
        if(animation != null &&  def == animation.getAnimationDef() ) return;
        animation = new Animation(def);
        setVar("anim", Type.ANIM, def);
        setVar("animTime", Type.INT, 0);
        setVar("animCycles", Type.INT, 0);
    }

    /**
     * Change physics
     * @param physicsDef Physics def
     */
    public void changePhysics(PhysicsDef physicsDef) {
        this.currentPhysics = physicsDef;
    }

    /**
     * Gets an animation def belonging to this fighter
     * @param animName Name of the animation
     * @return The animation, or null if non present
     */
    public AnimationDef getAnimationDef(String animName){
        return getFighterDef().getAnimations().getAnimationDef(animName);
    }

    /**
     * Sets X velocity
     * @param value
     */
    public void setVelX(float value) {
        vel.x = value;
        setVar("vel.x", Type.FLOAT, value);
    }

    /**
     * Sets Y velocity
     * @param value
     */
    public void setVelY(float value) {
        vel.y = value;
        setVar("vel.y", Type.FLOAT, value);
    }

    /**
     * Sets X position
     * @param value
     */
    public void setPosX(float value) {
        pos.x = value;
        setVar("pos.x", Type.FLOAT, value);
    }

    /**
     * Sets Y velocity
     * @param value
     */
    public void setPosY(float value) {
        pos.y = value;
        setVar("pos.y", Type.FLOAT, value);
    }

    /**
     * Set facing right or left
     * @param left True for left
     */
    public void setFacing(boolean left){
        flip = left;
        setVar("facing", Type.BOOL, flip);
    }

    public int getLayer() {
        return layer;
    }
    public void setLayer(int layer) {
        this.layer = layer;
    }
    public FighterDef getFighterDef() {
        return fighterDef;
    }
}
