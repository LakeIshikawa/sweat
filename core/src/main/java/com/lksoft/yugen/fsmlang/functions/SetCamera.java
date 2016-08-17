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
public class SetCamera extends Function {
    @Override
    public String getName() {
        return "setCamera";
    }

    @Override
    public Type[] getArgTypes() {
        return new Type[]{Type.FLOAT, Type.FLOAT};
    }

    @Override
    public void execute(Array<Value> argValues, FighterExpVisitor evaluator) {
        Yugen.i.getCamera().setPosition((int)argValues.get(0).getFloatValue(), (int)argValues.get(1).getFloatValue());
    }
}
