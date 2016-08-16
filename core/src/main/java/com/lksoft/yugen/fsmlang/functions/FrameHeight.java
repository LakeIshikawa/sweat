package com.lksoft.yugen.fsmlang.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Stallman on 16/08/2016.
 */
public class FrameHeight implements Functions.Function {
    @Override
    public String getSignature() {
        return "frameHeight";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate ID arg
        fcall.accept(evaluator);
        if( evaluator.getError() != null ) return;

        // 2-args
        if( evaluator.getResults().size == 1 ) {
            Value fsm = evaluator.getResults().get(0);
            if (fsm.getType() != Type.ANIM) {
                evaluator.setError("frameHeight expects 1 ANIM but got : " + fsm.getType());
                return;
            }

            evaluator.setFloatResult(fsm.getAnimationValue().getFrameAt(0).components.first().spriteDef.region.originalHeight);
        }
        else {
            evaluator.setError("getFSM: wrong number of parameters");
        }
    }
}
