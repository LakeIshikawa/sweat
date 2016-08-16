package com.lksoft.yugen.fsmlang.visitor;

import com.badlogic.gdx.Gdx;
import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.stateful.Fsm;

/**
 * Created by Lake on 11/06/2016.
 */
public class FighterExecuteVisitor extends FsmBaseVisitor<Void> {

    // Target fsm
    private Fsm fsm;

    // Expression evaluator
    FighterExpVisitor evaluator;

    // Switch value
    private Value switchValue = new Value(Type.INT, 0);

    /**
     * Create execution visitor
     * @param fsm Fsm
     */
    public FighterExecuteVisitor(Fsm fsm, FighterExpVisitor evaluator) {
        this.fsm = fsm;
        this.evaluator = evaluator;
    }

    @Override
    public Void visitAssignment(FsmParser.AssignmentContext ctx) {
        String lhs = ctx.ID().getText();
        ctx.e().accept(evaluator);

        // Error
        if( evaluator.getError() != null ){
            Gdx.app.error("FSM", "ERROR: " + evaluator.getError());
            return null;
        }

        // System assignments
        switch (lhs){
            case "anim": if( !checkError(lhs, Type.ANIM, evaluator) ) fsm.changeAnimation(evaluator.getResult().getAnimationValue()); break;
            case "keys": if( !checkError(lhs, Type.KEYS, evaluator) ) fsm.setKeySettings(evaluator.getResult().getKeysValue()); break;
            case "vel.x": if( !checkError(lhs, Type.FLOAT, evaluator) ) fsm.setVelX(evaluator.getResult().getFloatValue()); break;
            case "vel.y": if( !checkError(lhs, Type.FLOAT, evaluator) ) fsm.setVelY(evaluator.getResult().getFloatValue()); break;
            case "pos.x": if( !checkError(lhs, Type.FLOAT, evaluator) ) fsm.setPosX(evaluator.getResult().getFloatValue()); break;
            case "pos.y": if( !checkError(lhs, Type.FLOAT, evaluator) ) fsm.setPosY(evaluator.getResult().getFloatValue()); break;
            case "facing": if( !checkError(lhs, Type.BOOL, evaluator) ) fsm.setFacing(evaluator.getResult().getBoolValue()); break;
            case "layer": if( !checkError(lhs, Type.INT, evaluator) ) fsm.setLayer(evaluator.getResult().getIntValue()); break;
            case "attackhit": if( !checkError(lhs, Type.HIT, evaluator) ) fsm.setAttackHit(evaluator.getResult().getHitValue()); break;

            // Set a variable if not a system assignment
            default:
                fsm.setVar(lhs, evaluator.getResult());
                break;
        }

        return null;
    }

    @Override
    public Void visitStateChangeStmt(FsmParser.StateChangeStmtContext ctx) {
        fsm.changeState(ctx.ID().getText());
        return null;
    }

    @Override
    public Void visitSwitchcase(FsmParser.SwitchcaseContext ctx){
        // Evaluate exp
        ctx.e().accept(evaluator);

        // Error
        if( evaluator.getError() != null ){
            Gdx.app.error("FSM", "ERROR: " + evaluator.getError());
            return null;
        }

        // Visit case labels
        switchValue.copyFrom(evaluator.getResult());

        // Visit cases
        ctx.caselist().accept(this);
        return null;
    }

    @Override
    public Void visitScase(FsmParser.ScaseContext ctx){
        ctx.e().accept(evaluator);
        // Error
        if( evaluator.getError() != null ){
            Gdx.app.error("FSM", "ERROR: " + evaluator.getError());
            return null;
        }

        // Compare to switch value
        if( switchValue.equals(evaluator.getResult()) ){
            // Run statements
            ctx.statements().accept(this);
        }

        return null;
    }

    @Override
    public Void visitIfThen(FsmParser.IfThenContext ctx){
        // Evaluate if
        ctx.e().accept(evaluator);
        // Error
        if( evaluator.getError() != null ){
            Gdx.app.error("FSM", "ERROR: " + evaluator.getError());
            return null;
        }

        // Must be boolean
        if( evaluator.getResult().getType() != Type.BOOL ){
            Gdx.app.error("FSM", "ERROR: If expression must be boolean");
            return null;
        }

        if( evaluator.getResult().getBoolValue() ){
            ctx.statements().accept(this);
        }
        return null;
    }

    @Override
    public Void visitIfThenElse(FsmParser.IfThenElseContext ctx){
        // Evaluate if
        ctx.e().accept(evaluator);
        // Error
        if( evaluator.getError() != null ){
            Gdx.app.error("FSM", "ERROR: " + evaluator.getError());
            return null;
        }

        // Must be boolean
        if( evaluator.getResult().getType() != Type.BOOL ){
            Gdx.app.error("FSM", "ERROR: If expression must be boolean");
            return null;
        }

        if( evaluator.getResult().getBoolValue() ){
            ctx.statements().get(0).accept(this);
        } else {
            ctx.statements().get(1).accept(this);
        }
        return null;
    }

    @Override
    public Void visitFCallStmt(FsmParser.FCallStmtContext ctx) {
        // Execute
        Functions.Function function = Functions.getFunction(ctx.fcall().ID().getText());
        if( function != null ){
            function.execute(evaluator, ctx.fcall());
        } else {
            evaluator.setError("Unkown function: " + ctx.fcall().ID().getText());
        }
        return null;
    }

    /**
     * Checks exp error
     * @param lhs
     * @param evaluator
     * @return
     */
    private boolean checkError(String lhs, Type type, FighterExpVisitor evaluator) {
        if( evaluator.getResult().getType() != type ){
            Gdx.app.error("FSM", "ERROR: " + lhs + " = ... expected " + type + " but got " + evaluator.getResult().getType());
            return true;
        }
        return false;
    }
}
