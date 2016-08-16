package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.Gdx;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

import java.io.IOException;

/**
 * Created by Stallman on 16/08/2016.
 */
public class DestroyFSM implements Functions.Function {

    @Override
    public String getSignature() {
        return "destroyFSM";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate ID arg
        fcall.accept(evaluator);
        if( evaluator.getError() != null ) return;

        // 2-args
        if( evaluator.getResults().size == 1 ) {
            Value fsm = evaluator.getResults().get(0);
            if (fsm.getType() != Type.STRING) {
                evaluator.setError("destroyFSM expects 1 STRING but got : " + fsm.getType());
                return;
            }

            Yugen.i.destroyFSM(fsm.getStringValue());
        }
        else {
            evaluator.setError("destroyFSM: wrong number of parameters");
        }
    }
}