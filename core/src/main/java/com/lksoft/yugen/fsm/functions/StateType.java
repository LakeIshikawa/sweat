package com.lksoft.yugen.fsm.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsm.Functions;
import com.lksoft.yugen.fsm.Value;
import com.lksoft.yugen.fsm.visitor.FighterExpVisitor;

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
        evaluator.setStringResult(evaluator.getFighter().getCurrentState().type.toString());
    }
}
