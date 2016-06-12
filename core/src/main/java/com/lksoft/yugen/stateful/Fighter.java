package com.lksoft.yugen.stateful;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.stateless.AnimationSequence;
import com.lksoft.yugen.stateless.FighterDef;
import com.lksoft.yugen.stateless.FighterState;

import java.io.IOException;

/**
 * Created by Lake on 11/06/2016.
 */
public class Fighter extends Sprite {

    // Fighter def
    private FighterDef fighterDef;

    // Fighter layer
    private int layer = 1;
    // Current state
    private FighterState currentState;

    // Execution visitor (shared)
    private FighterExecuteVisitor visitor = new FighterExecuteVisitor(this);

    /**
     * Create a fighter by parsing a .def script file
     */
    public Fighter(FighterDef fighterDef) throws IOException {
        super(null);
        this.fighterDef = fighterDef;

        // Set idle state
        changeState("idle");
    }

    @Override
    public void update(){
        // Evaluate all triggers
        for(FighterState.FighterTrigger t : currentState.triggers) {
            //t.run(visitor);
        }

        // Also stateless triggers


        super.update();
    }

    /**
     * Change state
     * @param stateName
     */
    public void changeState(String stateName) {
        // Execute exit triggers
        if( currentState != null && currentState.exitTrigger != null ) {
            for (FsmParser.StatementContext s : currentState.exitTrigger) {
                s.accept(visitor);
            }
        }

        // Change state
        currentState = fighterDef.getStates().get(stateName);

        // Execute enter triggers
        if( currentState.enterTrigger != null ) {
            for (FsmParser.StatementContext s : currentState.enterTrigger) {
                s.accept(visitor);
            }
        }
    }

    /**
     * Change animation
     * @param animName Animation name (must exist)
     */
    public void changeAnimation(String animName){
        AnimationSequence sequence = fighterDef.getAnimations().getAnimationSequence(animName);
        if( sequence != null ){
            animation = new Animation(sequence);
        }
    }

    public int getLayer() {
        return layer;
    }
    public void setLayer(int layer) {
        this.layer = layer;
    }
}
