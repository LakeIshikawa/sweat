package com.lksoft.yugen.fsmlang.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Lake on 13/06/2016.
 */
public class ClearHit implements Functions.Function {

    @Override
    public String getSignature() {
        return "clearhit";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        evaluator.getTargetFsm().setCurrentHit(null);
    }
}
