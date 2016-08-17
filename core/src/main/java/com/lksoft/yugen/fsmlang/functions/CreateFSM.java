package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.fsmlang.Function;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Stallman on 16/08/2016.
 */
public class CreateFSM extends Function {
    @Override
    public String getName() {
        return "createFSM";
    }

    @Override
    public Type[] getArgTypes() {
        return new Type[]{Type.STRING};
    }

    @Override
    public void execute(Array<Value> argValues, FighterExpVisitor evaluator) {
        evaluator.setFsmResult(Yugen.i.createFSM(argValues.get(0).getStringValue()));
    }
}
