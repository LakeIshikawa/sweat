package com.lksoft.yugen.stateful;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ObjectMap;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExecuteVisitor;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;
import com.lksoft.yugen.stateless.*;

/**
 * Created by Lake on 11/06/2016.
 */
public class Fsm extends Sprite {

    // Fsm def
    private FsmDef fsmDef;

    // Current state
    private FsmState currentState;

    // Hit status
    private HitPack.HitDef currentHit;

    // Attacking hit
    private HitPack.HitDef attackHit;

    // Keys
    private Settings.KeySettings keySettings;

    // The memory
    private ObjectMap<String, Value> memory = new ObjectMap<>();

    // Combo detector
    private CommandDetector commandDetector = new CommandDetector(this);

    // Expression evaluator
    private FighterExpVisitor evaluator;
    // Execution visitor (shared)
    private FighterExecuteVisitor executor;

    // Pause time
    private int pauseTime = 0;

    // Rectangle for collision rendering
    private Rectangle collRect = new Rectangle();

    // Flag for noticing statechange
    private boolean stateChanged;

    /**
     * Create an fsm from a def with a name
     */
    public Fsm(FsmDef fsmDef, String name) {
        super(null);
        this.fsmDef = fsmDef;
        this.executor = new FighterExecuteVisitor(this);
        this.evaluator = executor.getEvaluator();

        // Set system variables
        setVar("ctrl", Type.BOOL, true);
        setVar("time", Type.INT, 0);
        setVar("statetime", Type.INT, 0);
        setVar("name", Type.STRING, name);
        setFacing(false);
        setAnimationPack(fsmDef.getAnimationPack());
        setPosX(0);
        setPosY(0);
        setVelX(0);
        setVelY(0);
        setScrollFactorX(0);
        setScrollFactorY(0);

        // Set layer
        setVar("layer", Type.INT, 0);
        setLayer(5);
    }

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
            case HIT: setVar(name, value.getType(), value.getHitValue()); break;
            case FSM: setVar(name, value.getType(), value.getFsmValue()); break;
            case KEYS: setVar(name, value.getType(), value.getKeysValue()); break;
            case ANIMPACK: setVar(name, value.getType(), value.getAnimPackValue()); break;
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
        // Set initial state
        if( currentState == null && fsmDef.getInitialState() != null ) {
            changeState(fsmDef.getInitialState());
        }

        // Pause!
        if( pauseTime > 0 ){
            pauseTime--;
            return;
        }

        stateChanged = false;

        // Update input
        commandDetector.update();

        // Evaluate all triggers
        if( currentState != null ) {
            for (FsmState.FighterTrigger t : currentState.triggers) {
                t.run(executor, evaluator);
                if (stateChanged) break;
            }

            // Also stateless triggers
            for (FsmState.FighterTrigger t : getFsmDef().getTriggers()) {
                t.run(executor, evaluator);
            }
        }

        super.update();

        // Update system vars
        setVar("pos.x", Type.FLOAT, pos.x);
        setVar("pos.y", Type.FLOAT, pos.y);
        setVar("vel.x", Type.FLOAT, vel.x);
        setVar("vel.y", Type.FLOAT, vel.y);
        if( animation != null ) {
            setVar("animTime", Type.INT, animation.getTicks());
            setVar("animCycles", Type.INT, animation.getCycles());
        }
        setVar("time", Type.INT, getVar("time").getIntValue()+1);
        setVar("statetime", Type.INT, getVar("statetime").getIntValue()+1);
    }

    /**
     * Renders collision rectangles
     * @param shapeRenderer
     */
    public void renderCollision(ShapeRenderer shapeRenderer) {
        if( animation == null ) return;
        shapeRenderer.setColor(Color.WHITE);
        for( Rectangle r : animation.getCurrentFrame().damageCollisions ){
            collRect.set(r);
            getRectWorld(collRect);
            shapeRenderer.rect(collRect.x, collRect.y, collRect.width, collRect.height);
        }

        shapeRenderer.setColor(Color.RED);
        for( Rectangle r : animation.getCurrentFrame().hitCollisions ){
            collRect.set(r);
            getRectWorld(collRect);
            shapeRenderer.rect(collRect.x, collRect.y, collRect.width, collRect.height);
        }
    }

    /**
     * Change state
     * @param stateName
     */
    public void changeState(String stateName) {
        FsmState newState = getFsmDef().getStates().get(stateName);
        if( currentState == newState ) return;
        if( newState == null ){
            Gdx.app.error("FSM", "No such state:"+stateName);
            return;
        }
        stateChanged = true;

        Gdx.app.log(getVar("name").getStringValue(), "-> "+stateName);

        // Execute exit triggers
        if( currentState != null && currentState.exitTrigger != null ) {
            for (FsmParser.StatementContext s : currentState.exitTrigger) {
                s.accept(executor);
            }
        }

        // Change state
        currentState = newState;
        setVar("statetime", Type.INT, 0);

        // Execute enter triggers
        if( currentState.enterTrigger != null ) {
            for (FsmParser.StatementContext s : currentState.enterTrigger) {
                s.accept(executor);
            }
        }

        // Error
        if( executor.getEvaluator().getError() != null ){
            Gdx.app.error("FSM", executor.getEvaluator().getError());
        }
    }

    /**
     * Change animation
     * @param def Animation def
     */
    public void changeAnimation(AnimationDef def){
        if( def == null ) return;
        if(animation != null &&  def == animation.getAnimationDef() ) return;
        animation = new Animation(def);
        setVar("anim", Type.ANIM, def);
        setVar("animTime", Type.INT, 0);
        setVar("animCycles", Type.INT, 0);
    }

    /**
     * Gets an animation def belonging to this fighter
     * @param animName Name of the animation
     * @return The animation, or null if non present
     */
    public AnimationDef getAnimationDef(String animName){
        return getFsmDef().getAnimationPack().getAnimationDef(animName);
    }

    /**
     * Sets the animation pack
     * @param pack
     */
    public void setAnimationPack(AnimationPack pack){
        if( pack != null ) {
            fsmDef.setAnimationPack(pack);
            setVar("animpack", Type.ANIMPACK, pack);
        }
    }

    /**
     * Gets a hit def belonging to this fighter
     * @param hitName Name of the hitdef
     * @return The hitdef, or null if non present
     */
    public HitPack.HitDef getHitDef(String hitName){
        return getFsmDef().getHitPack().get(hitName);
    }

    /**
     * Gets a command def belonging to the fighter
     * @param name Name of the command
     * @return The command, or null if not present
     */
    public CommandDef getCommand(String name) {
        return getFsmDef().getCommands().getCommandDef(name);
    }

    /**
     * Match specified command agains input
     * @param commandDef The command def to match
     * @return true if matching input
     */
    public boolean matchCommand(CommandDef commandDef) {
        boolean match = commandDetector.matchCommand(commandDef);
        if( match ){
            commandDetector.clearHistory();
        }
        return match;
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

    /**
     * Set scale
     * @param scale
     */
    public void setScale(float scale){
        this.scale = scale;
        setVar("scale", Type.FLOAT, scale);
    }

    /**
     * @return Current layer
     */
    public int getLayer() {
        return getVar("layer").getIntValue();
    }

    /**
     * Sets current display layer
     * @param layer
     */
    public void setLayer(int layer) {
        int old = getLayer();
        setVar("layer", Type.INT, layer);
        Yugen.i.layerChanged(this, old);
    }

    /**
     * Sets X scroll factor
     * @param value
     */
    public void setScrollFactorX(float value) {
        scrollFactor.x = value;
        setVar("scrollFactor.x", Type.FLOAT, value);
    }

    /**
     * Sets Y scroll factor
     * @param value
     */
    public void setScrollFactorY(float value) {
        scrollFactor.y = value;
        setVar("scrollFactor.y", Type.FLOAT, value);
    }

    /**
     * @return The currently attacking hit def
     */
    public HitPack.HitDef getAttackHit(){
        return attackHit;
    }

    /**
     * Sets the attack hit
     * @param hit
     */
    public void setAttackHit(HitPack.HitDef hit){
        this.attackHit = hit;
        setVar("attackhit", Type.HIT, attackHit);
    }

    /**
     * Get key settings for the player of this fsm
     * @return
     */
    public Settings.KeySettings getKeySettings() {
        return keySettings;
    }

    /**
     * Sets the key settings
     * @param keySettings
     */
    public void setKeySettings(Settings.KeySettings keySettings) {
        this.keySettings = keySettings;
        setVar("keys", Type.KEYS, keySettings);
    }

    /**
     * Set hit status to fighter (the fighter has been hit)
     * @param hit Hit def info
     */
    public void setCurrentHit(HitPack.HitDef hit){
        this.currentHit = hit;
    }

    /**
     * @return Current hit status (being hit)
     */
    public HitPack.HitDef getCurrentHit(){
        return currentHit;
    }

    /**
     * Transform a local rectangle to world coordinates
     * @param r Collision rectangle (Will be modified)
     */
    public void getRectWorld(Rectangle r) {
        if( flip ){
            r.set(pos.x - scale*(r.x + r.width), pos.y + scale*r.y, scale*r.width, scale*r.height);
        } else {
            r.set(pos.x + scale*r.x, pos.y + scale*r.y, scale*r.width, scale*r.height);
        }
    }

    /**
     * Pause the FSM for the specified number of ticks
     * @param ticks
     */
    public void setPause(int ticks){
        pauseTime = ticks;
    }

    public FsmDef getFsmDef() {
        return fsmDef;
    }
    public FsmState getCurrentState(){
        return currentState;
    }
}
