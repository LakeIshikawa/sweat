package com.lksoft.yugen.fsmlang.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Lake on 13/06/2016.
 */
public class Pause implements Functions.Function {

    @Override
    public String getSignature() {
        return "pause";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate arg
        fcall.accept(evaluator);
        if( evaluator.getError() != null ) return;

        // Take first arg
        Value v = evaluator.getResults().get(0);

        switch ( v.getType() ){
            case INT:
                evaluator.getFsm().setPause(v.getIntValue());
                break;

            default:
                evaluator.setError("pause function expects int type but got:" + v.getType());
                break;
        }
    }
}
