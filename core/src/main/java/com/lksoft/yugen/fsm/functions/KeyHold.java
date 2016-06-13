package com.lksoft.yugen.fsm.functions;

import com.badlogic.gdx.Gdx;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.fsm.FighterExpVisitor;
import com.lksoft.yugen.fsm.Functions;
import com.lksoft.yugen.fsm.Type;
import com.lksoft.yugen.fsm.Value;

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

        if( v.getType() != Type.STRING ){
            evaluator.setError("keyhold function expects String type but got: "+ v.getType());
            return;
        }

        boolean p1 = evaluator.getFighter().isP1();
        boolean facingRight = !evaluator.getFighter().flip;
        switch (v.getStringValue()){
            case "U":
                if( p1 ) evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP1Keys().up));
                else evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP2Keys().up));
                break;
            case "D":
                if( p1 ) evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP1Keys().down));
                else evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP2Keys().down));
                break;
            case "B1":
                if( p1 ) evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP1Keys().b1));
                else evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP2Keys().b1));
                break;
            case "B2":
                if( p1 ) evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP1Keys().b2));
                else evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP2Keys().b2));
                break;
            case "B3":
                if( p1 ) evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP1Keys().b3));
                else evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP2Keys().b3));
                break;
            case "B4":
                if( p1 ) evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP1Keys().b4));
                else evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP2Keys().b4));
                break;
            case "B5":
                if( p1 ) evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP1Keys().b5));
                else evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP2Keys().b5));
                break;
            case "B6":
                if( p1 ) evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP1Keys().b6));
                else evaluator.setBoolResult(Gdx.input.isKeyPressed(Yugen.i().getSettings().getP2Keys().b6));
                break;
            case "F":
                boolean l, r;
                if( p1 ) {
                    l = Gdx.input.isKeyPressed(Yugen.i().getSettings().getP1Keys().left);
                    r = Gdx.input.isKeyPressed(Yugen.i().getSettings().getP1Keys().right);
                } else {
                    l = Gdx.input.isKeyPressed(Yugen.i().getSettings().getP2Keys().left);
                    r = Gdx.input.isKeyPressed(Yugen.i().getSettings().getP2Keys().right);
                }

                evaluator.setBoolResult( (facingRight && r) || (!facingRight && l));
                break;
            case "B":
                if( p1 ) {
                    l = Gdx.input.isKeyPressed(Yugen.i().getSettings().getP1Keys().left);
                    r = Gdx.input.isKeyPressed(Yugen.i().getSettings().getP1Keys().right);
                } else {
                    l = Gdx.input.isKeyPressed(Yugen.i().getSettings().getP2Keys().left);
                    r = Gdx.input.isKeyPressed(Yugen.i().getSettings().getP2Keys().right);
                }

                evaluator.setBoolResult( (facingRight && l) || (!facingRight && r));
                break;
        }
    }
}
