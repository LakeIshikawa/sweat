package com.lksoft.yugen.stateless;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmParser;

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
