package com.lksoft.yugen.fsmlang;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;

/**
 * Created by Lake on 16/08/2016.
 */
public abstract class Function {

    // ArgTypes
    private Type[] argTypes;
    // Signature
    private String signature;

    // Interface
    public abstract String getName();
    public abstract Type[] getArgTypes();
    public abstract void execute(Array<Value> argValues, FighterExpVisitor evaluator);

    // Access
    public String getSignature(){
        return signature;
    }

    // Infer argtypes
    public Function(){
        argTypes = getArgTypes();

        signature = getName();
        for( Type t : argTypes ){
            signature += t.getIdChar();
        }
    }
}
