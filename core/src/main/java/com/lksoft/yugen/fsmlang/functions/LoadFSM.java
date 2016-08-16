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
public class LoadFSM implements Functions.Function {
    @Override
    public String getSignature() {
        return "loadFSM";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate ID arg
        fcall.accept(evaluator);
        if( evaluator.getError() != null ) return;

        // 2-args
        if( evaluator.getResults().size == 2 ) {
            Value path = evaluator.getResults().get(0);
            Value name = evaluator.getResults().get(1);
            if (path.getType() != Type.STRING || name.getType() != Type.STRING) {
                evaluator.setError("loadFSM expects 2 STRINGS but got : " + path.getType() + "," + name.getType());
                return;
            }

            try {
                Yugen.i.loadFSM(Gdx.files.internal(path.getStringValue()), name.getStringValue());
            } catch (IOException e) {
                evaluator.setError("loadFSM: Can't find fsm file " + path.getStringValue());
            }
        }
        else {
            evaluator.setError("loadFSM: wrong number of parameters");
        }
    }
}
