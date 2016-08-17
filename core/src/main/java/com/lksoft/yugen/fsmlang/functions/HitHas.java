package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.fsmlang.Function;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Stallman on 10/08/2016.
 */
public class HitHas extends Function {
    @Override
    public String getName() {
        return "hithas";
    }

    @Override
    public Type[] getArgTypes() {
        return new Type[]{Type.STRING};
    }

    @Override
    public void execute(Array<Value> argValues, FighterExpVisitor evaluator) {
        evaluator.setBoolResult(evaluator.getTargetFsm().getHitDef(argValues.get(0).getStringValue()) != null);
    }
}
