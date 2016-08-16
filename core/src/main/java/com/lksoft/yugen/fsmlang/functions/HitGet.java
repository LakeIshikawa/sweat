package com.lksoft.yugen.fsmlang.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Stallman on 10/08/2016.
 */
public class HitGet implements Functions.Function {
    @Override
    public String getSignature() {
        return "hitget";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate arg
        fcall.accept(evaluator);
        if( evaluator.getError() != null ) return;

        // Take first arg
        Value v = evaluator.getResults().get(0);

        switch ( v.getType() ){
            case STRING:
                Value value = evaluator.getTargetFsm().getCurrentHit().get(evaluator.getResult().getStringValue());
                evaluator.getResult().copyFrom(value);
                break;

            default:
                evaluator.setError("hitget function expects string type but got:" + v.getType());
                break;
        }
    }
}
