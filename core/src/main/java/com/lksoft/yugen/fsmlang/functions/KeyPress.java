package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.fsmlang.Function;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;
import com.lksoft.yugen.stateless.Settings;

/**
 * Created by Lake on 13/06/2016.
 */
public class KeyPress extends Function {

    @Override
    public String getName() {
        return "keypress";
    }

    @Override
    public Type[] getArgTypes() {
        return new Type[]{Type.STRING};
    }

    @Override
    public void execute(Array<Value> argValues, FighterExpVisitor evaluator) {
        Settings.KeySettings keys = evaluator.getTargetFsm().getKeySettings();
        if( keys == null ) {
            evaluator.setBoolResult(false);
            return;
        }

        boolean facingRight = !evaluator.getTargetFsm().flip;
        switch (argValues.get(0).getStringValue()){
            case "U":  evaluator.setBoolResult(Gdx.input.isKeyJustPressed(keys.up)); break;
            case "D":  evaluator.setBoolResult(Gdx.input.isKeyJustPressed(keys.down)); break;
            case "B1": evaluator.setBoolResult(Gdx.input.isKeyJustPressed(keys.b1)); break;
            case "B2": evaluator.setBoolResult(Gdx.input.isKeyJustPressed(keys.b2)); break;
            case "B3": evaluator.setBoolResult(Gdx.input.isKeyJustPressed(keys.b3)); break;
            case "B4": evaluator.setBoolResult(Gdx.input.isKeyJustPressed(keys.b4)); break;
            case "B5": evaluator.setBoolResult(Gdx.input.isKeyJustPressed(keys.b5)); break;
            case "B6": evaluator.setBoolResult(Gdx.input.isKeyJustPressed(keys.b6)); break;
            case "F":
                boolean l = Gdx.input.isKeyJustPressed(keys.left);
                boolean r = Gdx.input.isKeyJustPressed(keys.right);

                evaluator.setBoolResult( (facingRight && r) || (!facingRight && l));
                break;
            case "B":
                l = Gdx.input.isKeyJustPressed(keys.left);
                r = Gdx.input.isKeyJustPressed(keys.right);

                evaluator.setBoolResult( (facingRight && l) || (!facingRight && r));
                break;
        }
    }
}
