package com.lksoft.yugen.fsmlang.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Lake on 13/06/2016.
 */
public class Facing implements Functions.Function {

    @Override
    public String getSignature() {
        return "facing";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate ID arg
        fcall.accept(evaluator);
        if( evaluator.getError() != null ) return;

        // Take first arg
        Value v = evaluator.getResults().get(0);

        switch (v.getType()){
            case FSM:
                boolean leftOfOpp = evaluator.getTargetFsm().pos.x < v.getFsmValue().pos.x;
                evaluator.setBoolResult(evaluator.getTargetFsm().flip ^ leftOfOpp);
                break;
            default:
                evaluator.setError("facing function expects FSM but got : " + v.getType());
                return;
        }
    }
}