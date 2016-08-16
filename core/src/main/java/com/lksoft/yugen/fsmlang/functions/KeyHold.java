package com.lksoft.yugen.fsmlang.functions;

import com.badlogic.gdx.Gdx;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.stateless.Settings;

/**
 * Created by Lake on 13/06/2016.
 */
public class KeyHold implements Functions.Function {

    @Override
    public String getSignature() {
        return "keyhold";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate ID arg
        fcall.accept(evaluator);
        // Take first arg
        Value v = evaluator.getResults().get(0);
        if( evaluator.getError() != null ) return;

        if( v.getType() != Type.STRING ){
            evaluator.setError("keyhold function expects String type but got: "+ v.getType());
            return;
        }

        Settings.KeySettings keys = evaluator.getFsm().getKeySettings();
        boolean facingRight = !evaluator.getFsm().flip;
        switch (v.getStringValue()){
            case "U":  evaluator.setBoolResult(Gdx.input.isKeyPressed(keys.up)); break;
            case "D":  evaluator.setBoolResult(Gdx.input.isKeyPressed(keys.down)); break;
            case "B1": evaluator.setBoolResult(Gdx.input.isKeyPressed(keys.b1)); break;
            case "B2": evaluator.setBoolResult(Gdx.input.isKeyPressed(keys.b2)); break;
            case "B3": evaluator.setBoolResult(Gdx.input.isKeyPressed(keys.b3)); break;
            case "B4": evaluator.setBoolResult(Gdx.input.isKeyPressed(keys.b4)); break;
            case "B5": evaluator.setBoolResult(Gdx.input.isKeyPressed(keys.b5)); break;
            case "B6": evaluator.setBoolResult(Gdx.input.isKeyPressed(keys.b6)); break;
            case "F":
                boolean l = Gdx.input.isKeyPressed(keys.left);
                boolean r = Gdx.input.isKeyPressed(keys.right);

                evaluator.setBoolResult( (facingRight && r) || (!facingRight && l));
                break;
            case "B":
                l = Gdx.input.isKeyPressed(keys.left);
                r = Gdx.input.isKeyPressed(keys.right);

                evaluator.setBoolResult( (facingRight && l) || (!facingRight && r));
                break;
        }
    }
}
