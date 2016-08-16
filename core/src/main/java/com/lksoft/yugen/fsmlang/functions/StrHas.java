package com.lksoft.yugen.fsmlang.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Stallman on 10/08/2016.
 */
public class StrHas implements Functions.Function {

    @Override
    public String getSignature() {
        return "strhas";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate arg
        fcall.accept(evaluator);
        if( evaluator.getError() != null ) return;

        // Take first arg
        Value v1 = evaluator.getResults().get(0);
        Value v2 = evaluator.getResults().get(1);

        if( v1.getType() != Type.STRING || v2.getType() != Type.STRING ){
            evaluator.setError("strhas function expects string, string type but got:" + v1.getType()+","+v2.getType());
            return;
        }

        evaluator.setBoolResult(v1.getStringValue().contains(v2.getStringValue()));
    }
}
