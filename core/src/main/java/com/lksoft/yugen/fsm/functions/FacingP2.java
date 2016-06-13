package com.lksoft.yugen.fsm.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsm.FighterExpVisitor;
import com.lksoft.yugen.fsm.Functions;

/**
 * Created by Lake on 13/06/2016.
 */
public class FacingP2 implements Functions.Function {

    @Override
    public String getSignature() {
        return "facingP2";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        boolean leftOfOpp = evaluator.getFighter().pos.x < evaluator.getFighter().getOpponent().pos.x;
        evaluator.setBoolResult(evaluator.getFighter().flip ^ leftOfOpp);
    }
}
