package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.fsmlang.Function;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Lake on 18/06/2016.
 */
public class HasHit extends Function {

    @Override
    public String getName() {
        return "has";
    }

    @Override
    public Type[] getArgTypes() {
        return new Type[]{Type.HIT};
    }

    @Override
    public void execute(Array<Value> argValues, FighterExpVisitor evaluator) {
        evaluator.setBoolResult(argValues.get(0).getHitValue() != null);
    }
}
