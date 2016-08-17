package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.fsmlang.Function;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Lake on 13/06/2016.
 */
public class Pause extends Function {

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public Type[] getArgTypes() {
        return new Type[]{Type.INT};
    }

    @Override
    public void execute(Array<Value> argValues, FighterExpVisitor evaluator) {
        evaluator.getTargetFsm().setPause(argValues.get(0).getIntValue());
    }
}
