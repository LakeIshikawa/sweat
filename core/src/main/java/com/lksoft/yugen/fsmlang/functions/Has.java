package com.lksoft.yugen.fsmlang.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Lake on 18/06/2016.
 */
public class Has implements Functions.Function {

    @Override
    public String getSignature() {
        return "has";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate arg
        fcall.accept(evaluator);
        if( evaluator.getError() != null ) return;

        // Take first arg
        Value v = evaluator.getResults().get(0);

        switch ( v.getType() ){
            case ANIM:
                evaluator.setBoolResult(v.getAnimationValue() != null);
                break;
            case HIT:
                evaluator.setBoolResult(v.getHitValue() != null);
                break;
            case FSM:
                evaluator.setBoolResult(v.getFsmValue() != null);
                break;
            case KEYS:
                evaluator.setBoolResult(v.getKeysValue() != null);
                break;
            default:
                evaluator.setError("has function expects resource type but got:" + v.getType());
                break;
        }
    }
}
