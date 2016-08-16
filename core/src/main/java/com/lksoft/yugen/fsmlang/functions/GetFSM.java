package com.lksoft.yugen.fsmlang.functions;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.fsmlang.visitor.FighterExpVisitor;
import com.lksoft.yugen.stateful.Fsm;

/**
 * Created by Stallman on 16/08/2016.
 */
public class GetFSM implements Functions.Function {

    @Override
    public String getSignature() {
        return "getFSM";
    }

    @Override
    public void execute(FighterExpVisitor evaluator, FsmParser.FcallContext fcall) {
        // Evaluate ID arg
        fcall.accept(evaluator);
        if( evaluator.getError() != null ) return;

        // 2-args
        if( evaluator.getResults().size == 1 ) {
            Value fsm = evaluator.getResults().get(0);
            if (fsm.getType() != Type.STRING) {
                evaluator.setError("getFSM expects 1 STRING but got : " + fsm.getType());
                return;
            }

            Fsm res = Yugen.i.getFSM(fsm.getStringValue());
            if( res == null ){
                evaluator.setError("FSM not found : " + fsm.getStringValue());
                return;
            }
            evaluator.setFsmResult(res);
        }
        else {
            evaluator.setError("getFSM: wrong number of parameters");
        }
    }
}
