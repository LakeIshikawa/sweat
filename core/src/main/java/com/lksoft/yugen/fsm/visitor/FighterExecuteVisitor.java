package com.lksoft.yugen.fsm.visitor;

import com.badlogic.gdx.Gdx;
import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsm.Type;
import com.lksoft.yugen.stateful.Fighter;

/**
 * Created by Lake on 11/06/2016.
 */
public class FighterExecuteVisitor extends FsmBaseVisitor<Void> {

    // Target fighter
    private Fighter fighter;

    // Expression evaluator
    FighterExpVisitor evaluator;

    /**
     * Create execution visitor
     * @param fighter Fighter
     */
    public FighterExecuteVisitor(Fighter fighter, FighterExpVisitor evaluator) {
        this.fighter = fighter;
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
            case "anim": if( !checkError(lhs, Type.ANIM, evaluator) ) fighter.changeAnimation(evaluator.getResult().getAnimationValue()); break;
            case "physics": if( !checkError(lhs, Type.PHYSICS, evaluator) ) fighter.changePhysics(evaluator.getResult().getPhysicsValue()); break;
            case "vel.x": if( !checkError(lhs, Type.FLOAT, evaluator) ) fighter.setVelX(evaluator.getResult().getFloatValue()); break;
            case "vel.y": if( !checkError(lhs, Type.FLOAT, evaluator) ) fighter.setVelY(evaluator.getResult().getFloatValue()); break;
            case "pos.x": if( !checkError(lhs, Type.FLOAT, evaluator) ) fighter.setPosX(evaluator.getResult().getFloatValue()); break;
            case "pos.y": if( !checkError(lhs, Type.FLOAT, evaluator) ) fighter.setPosY(evaluator.getResult().getFloatValue()); break;
            case "facing": if( !checkError(lhs, Type.BOOL, evaluator) ) fighter.setFacing(evaluator.getResult().getBoolValue()); break;

            // Set a variable if not a system assignment
            default:
                fighter.setVar(lhs, evaluator.getResult());
                break;
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

    @Override
    public Void visitStateChangeStmt(FsmParser.StateChangeStmtContext ctx) {
        fighter.changeState(ctx.ID().getText());
        return null;
    }
}
