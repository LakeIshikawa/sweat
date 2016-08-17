package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.fsmlang.Function;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

import java.io.IOException;

/**
 * Created by Stallman on 16/08/2016.
 */
public class LoadFSM extends Function {
    @Override
    public String getName() {
        return "loadFSM";
    }

    @Override
    public Type[] getArgTypes() {
        return new Type[]{Type.STRING, Type.STRING};
    }

    @Override
    public void execute(Array<Value> argValues, FighterExpVisitor evaluator) {
        try {
            Yugen.i.loadFSM(Gdx.files.internal(argValues.get(0).getStringValue()), argValues.get(1).getStringValue());
        } catch (IOException e) {
            evaluator.setError("loadFSM: Can't find fsm file " + argValues.get(0).getStringValue());
        }
    }
}
