package com.lksoft.yugen.fsmlang.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Stallman on 16/08/2016.
 */
public class SetCamera implements Functions.Function {
    @Override
    public String getSignature() {
        return "setCamera";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate ID arg
        fcall.accept(evaluator);
        if( evaluator.getError() != null ) return;

        // 2-args
        if( evaluator.getResults().size == 2 ) {
            Value px = evaluator.getResults().get(0);
            Value py = evaluator.getResults().get(1);
            if (px.getType() != Type.FLOAT || py.getType() != Type.FLOAT) {
                evaluator.setError("setCamera expects 2 FLOAT but got : " + px.getType() + "," + py.getType());
                return;
            }

            Yugen.i.getCamera().setPosition((int)px.getFloatValue(), (int)py.getFloatValue());
        }
        else {
            evaluator.setError("loadFSM: wrong number of parameters");
        }
    }
}
