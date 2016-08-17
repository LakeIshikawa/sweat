package com.lksoft.yugen.fsmlang;

import com.badlogic.gdx.utils.ObjectMap;
import com.lksoft.yugen.fsmlang.functions.*;

/**
 * Created by Lake on 12/06/2016.
 *
 * System functions
 */
public class Functions {

    // Functions map
    final static private ObjectMap<String, Function> functions = new ObjectMap<>();

    /**
     * Register b1 function
     * @param function Function behavior
     */
    public static void registerFunction(Function function){
        functions.put(function.getSignature(), function);
    }

    /**
     * Get registered function
     * @param signature Function signature
     * @return The function or null
     */
    public static Function getFunction(String signature){
        return functions.get(signature);
    }

    // System functions
    static {
        registerFunction(new KeyHold());
        registerFunction(new KeyPress());
        registerFunction(new Abs());
        registerFunction(new Facing());
        registerFunction(new HasAnim());
        registerFunction(new HasHit());
        registerFunction(new HasKeys());
        registerFunction(new IsHit());
        registerFunction(new HitHas());
        registerFunction(new HitGet());
        registerFunction(new StrHas());
        registerFunction(new ClearHit());
        registerFunction(new Pause());
        registerFunction(new LoadFSM());
        registerFunction(new CreateFSM());
        registerFunction(new DestroyFSM());
        registerFunction(new GetFSM());
        registerFunction(new FrameHeight());
        registerFunction(new InitCamera());
        registerFunction(new SetCamera());
        registerFunction(new AddCollisionTarget());
    }
}
