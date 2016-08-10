package com.lksoft.yugen.fsm.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsm.Functions;
import com.lksoft.yugen.fsm.Value;
import com.lksoft.yugen.fsm.visitor.FighterExpVisitor;

/**
 * Created by Stallman on 10/08/2016.
 */
public class HitHas implements Functions.Function {
    @Override
    public String getSignature() {
        return "hithas";
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
                evaluator.setBoolResult(evaluator.getFighter().getHitDef(v.getStringValue()) != null);
                break;

            default:
                evaluator.setError("hithas function expects string type but got:" + v.getType());
                break;
        }
    }
}
