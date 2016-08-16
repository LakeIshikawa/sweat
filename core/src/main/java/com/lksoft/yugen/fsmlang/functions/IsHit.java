package com.lksoft.yugen.fsmlang.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Stallman on 10/08/2016.
 */
public class IsHit implements Functions.Function {
    @Override
    public String getSignature() {
        return "ishit";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        evaluator.setBoolResult(evaluator.getTargetFsm().getCurrentHit() != null);
    }
}
