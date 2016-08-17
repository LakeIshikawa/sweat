package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.fsmlang.Function;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Lake on 13/06/2016.
 */
public class Abs extends Function {
    @Override
    public String getName() {
        return "abs";
    }

    @Override
    public Type[] getArgTypes() {
        return new Type[]{Type.FLOAT};
    }

    @Override
    public void execute(Array<Value> values, FighterExpVisitor evaluator) {
        evaluator.setFloatResult(Math.abs(values.get(0).getFloatValue()));
    }
}
