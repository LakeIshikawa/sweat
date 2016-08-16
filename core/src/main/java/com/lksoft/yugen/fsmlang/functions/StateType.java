package com.lksoft.yugen.fsmlang.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Lake on 18/06/2016.
 */
public class StateType implements Functions.Function {

    @Override
    public String getSignature() {
        return "stype";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        evaluator.setStringResult(evaluator.getFsm().getCurrentState().type.toString());
    }
}
