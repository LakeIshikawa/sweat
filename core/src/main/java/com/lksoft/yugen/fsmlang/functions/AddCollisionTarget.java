package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.fsmlang.Function;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Lake on 16/08/2016.
 */
public class AddCollisionTarget extends Function {
    @Override
    public String getName() {
        return "addCollisionTarget";
    }

    @Override
    public Type[] getArgTypes() {
        return new Type[]{Type.FSM};
    }

    @Override
    public void execute(Array<Value> argValues, FighterExpVisitor evaluator) {
        evaluator.getTargetFsm().addCollisionTarget(argValues.get(0).getFsmValue());
    }

}
