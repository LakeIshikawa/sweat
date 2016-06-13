package com.lksoft.yugen.fsm.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsm.FighterExpVisitor;
import com.lksoft.yugen.fsm.Functions;
import com.lksoft.yugen.fsm.Value;

/**
 * Created by Lake on 13/06/2016.
 */
public class Abs implements Functions.Function {
    @Override
    public String getSignature() {
        return "abs";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate ID arg
        fcall.accept(evaluator);
        // Take first arg
        Value v = evaluator.getResults().get(0);

        switch (v.getType()){
            case ID:
            case STRING:
            case BOOL:
            case ANIM:
            case PHYSICS:
                evaluator.setError("abs function expects INT or FLOAT but got : " + v.getType());
                return;
            case FLOAT: evaluator.setFloatResult(Math.abs(v.getFloatValue())); break;
            case INT: evaluator.setIntResult(Math.abs(v.getIntValue())); break;
        }
    }
}
