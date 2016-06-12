package com.lksoft.yugen.stateless;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsm.FighterExecuteVisitor;
import com.lksoft.yugen.fsm.FighterExpVisitor;
import com.lksoft.yugen.fsm.Type;

/**
 * Created by Lake on 11/06/2016.
 */
public class FighterState {
    // State type
    public enum StateType {
        S, // Stand
        C, // Crouch
        A  // Air
    }

    // Custom trigger
    public static class FighterTrigger {
        // Condition
        public Array<Array<FsmParser.EContext>> conditions = new Array<>();
        public Array<FsmParser.StatementContext> statements;

        public FighterTrigger(Array<FsmParser.StatementContext> statements) {
            this.statements = statements;
        }

        public void addCondition(int num, FsmParser.EContext condition){
            if( num > conditions.size-1 ) conditions.setSize(num+1);
            for( int i=0; i<conditions.size; i++ ){
                if( conditions.get(i) == null ) conditions.set(i, new Array<FsmParser.EContext>());
            }
            conditions.get(num).add(condition);
        }

        /**
         * Executes the trigger
         * @param visitor
         */
        public void run(FighterExecuteVisitor visitor, FighterExpVisitor evaluator) {
            // Check conditions
            for( Array<FsmParser.EContext> orCond : conditions ){
                boolean pass = true;
                for( FsmParser.EContext andCond : orCond ){
                    evaluator.evaluate(andCond);

                    // Error check
                    if( evaluator.getError() != null ){
                        Gdx.app.error("FSM", "ERROR: "+evaluator.getError());
                        return;
                    }
                    else if( evaluator.getResult().getType() != Type.BOOL ){
                        Gdx.app.error("FSM", "ERROR: expected bool in trigger condition but got "+evaluator.getResult().getType());
                        return;
                    }

                    if( !evaluator.getResult().getBoolValue() ){
                        pass = false;
                        break;
                    }
                }

                if( pass ){
                    // Execute statements
                    for(FsmParser.StatementContext s : statements ){
                        s.accept(visitor);
                    }

                    return;
                }
            }
        }
    }

    // Name
    public String name;

    // State type
    public StateType type;

    // System triggers
    public Array<FsmParser.StatementContext> enterTrigger;
    public Array<FsmParser.StatementContext> exitTrigger;

    // Custom triggers
    public Array<FighterTrigger> triggers = new Array<>();

    /**
     * Create named state
     * @param name State name
     * @param type State type
     */
    public FighterState(String name, StateType type){
        this.name = name;
        this.type = type;
    }
}
