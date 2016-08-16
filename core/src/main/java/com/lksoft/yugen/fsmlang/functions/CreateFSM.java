package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.Gdx;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;
import com.lksoft.yugen.stateful.Fsm;
import com.lksoft.yugen.stateless.FsmDef;

import java.io.IOException;

/**
 * Created by Stallman on 16/08/2016.
 */
public class CreateFSM implements Functions.Function {
    @Override
    public String getSignature() {
        return "createFSM";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate ID arg
        fcall.accept(evaluator);
        if( evaluator.getError() != null ) return;

        // 1-arg
        if( evaluator.getResults().size == 1 ) {
            Value name = evaluator.getResults().get(0);
            if (name.getType() != Type.STRING) {
                evaluator.setError("createFSM expects 1 STRING but got : " + name.getType());
                return;
            }

            Yugen.i.createFSM(name.getStringValue());
        }
        else {
            evaluator.setError("createFSM: wrong number of parameters");
        }
    }
}
