package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.Function;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Lake on 13/06/2016.
 */
public class Facing extends Function {

    @Override
    public String getName() {
        return "facing";
    }

    @Override
    public Type[] getArgTypes() {
        return new Type[]{Type.FSM};
    }

    @Override
    public void execute(Array<Value> argValues, FighterExpVisitor evaluator) {
        boolean leftOfOpp = evaluator.getTargetFsm().pos.x < argValues.get(0).getFsmValue().pos.x;
        evaluator.setBoolResult(evaluator.getTargetFsm().flip ^ leftOfOpp);
    }
}
