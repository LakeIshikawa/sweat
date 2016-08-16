package com.lksoft.yugen.fsmlang.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;
import com.lksoft.yugen.fsmlang.Functions;

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
        boolean leftOfOpp = evaluator.getFsm().pos.x < evaluator.getFsm().getOpponent().pos.x;
        evaluator.setBoolResult(evaluator.getFsm().flip ^ leftOfOpp);
    }
}
