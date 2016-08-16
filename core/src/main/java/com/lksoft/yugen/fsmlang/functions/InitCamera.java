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
public class InitCamera implements Functions.Function {
    @Override
    public String getSignature() {
        return "initCamera";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate ID arg
        fcall.accept(evaluator);
        if( evaluator.getError() != null ) return;

        // 2-args
        if( evaluator.getResults().size == 4 ) {
            Value px = evaluator.getResults().get(0);
            Value py = evaluator.getResults().get(1);
            Value ww = evaluator.getResults().get(2);
            Value wh = evaluator.getResults().get(3);
            if (px.getType() != Type.FLOAT || py.getType() != Type.FLOAT || ww.getType() != Type.FLOAT || wh.getType() != Type.FLOAT) {
                evaluator.setError("initCamera expects 4 FLOAT but got : " + px.getType() + "," + py.getType() + "," + ww.getType() +"," + wh.getType());
                return;
            }

            Yugen.i.getCamera().init((int)px.getFloatValue(), (int)py.getFloatValue(), (int)ww.getFloatValue(), (int)wh.getFloatValue());
        }
        else {
            evaluator.setError("loadFSM: wrong number of parameters");
        }
    }
}
