package com.lksoft.yugen.fsm;

import com.badlogic.gdx.Gdx;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.Yugen;

import java.util.HashMap;

/**
 * Created by Lake on 12/06/2016.
 *
 * System functions
 */
public class Functions {

    /**
     * Fsm function interface
     */
    public interface Function{
        void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall);
    }

    // Functions map
    final static private HashMap<String, Function> functions = new HashMap<>();

    /**
     * Register b1 function
     * @param name Function name (signature, no overloading)
     * @param function Function behavior
     */
    public static void registerFunction(String name, Function function){
        functions.put(name, function);
    }

    /**
     * Get registered function
     * @param name Function name
     * @return The function or null
     */
    public static Function getFunction(String name){
        return functions.get(name);
    }

    // System functions
    static {
        registerFunction("keyhold", new Function() {
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
        });
    }
}
