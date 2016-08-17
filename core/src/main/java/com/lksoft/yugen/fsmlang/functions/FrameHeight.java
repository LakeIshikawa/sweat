package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.Function;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Stallman on 16/08/2016.
 */
public class FrameHeight extends Function {
    @Override
    public String getName() {
        return "frameHeight";
    }

    @Override
    public Type[] getArgTypes() {
        return new Type[]{Type.ANIM};
    }

    @Override
    public void execute(Array<Value> argValues, FighterExpVisitor evaluator) {
        evaluator.setFloatResult(argValues.get(0).getAnimationValue().getFrameAt(0).components.first().spriteDef.region.originalHeight);
    }
}
