package com.lksoft.yugen.fsm;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsm.functions.*;
import com.lksoft.yugen.fsm.visitor.FighterExpVisitor;

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
        String getSignature();
        void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall);
    }

    // Functions map
    final static private HashMap<String, Function> functions = new HashMap<>();

    /**
     * Register b1 function
     * @param function Function behavior
     */
    public static void registerFunction(Function function){
        functions.put(function.getSignature(), function);
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
        registerFunction(new KeyHold());
        registerFunction(new KeyPress());
        registerFunction(new Abs());
        registerFunction(new FacingP2());
        registerFunction(new Has());
        registerFunction(new StateType());
    }
}
