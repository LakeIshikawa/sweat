package com.lksoft.yugen.fsm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;
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
            case "anim":
                // Error check
                if( evaluator.getResult().getType() != Type.ANIM ){
                    Gdx.app.error("FSM", "ERROR: anim = ... expected Anim but got " + evaluator.getResult().getType());
                    return null;
                }

                fighter.changeAnimation(evaluator.getResult().getAnimationValue());
                break;

            case "vel.x":
                // Error check
                if( evaluator.getResult().getType() != Type.FLOAT ){
                    Gdx.app.error("FSM", "ERROR: vel.x = ... expected Float but got " + evaluator.getResult().getType());
                    return null;
                }

                fighter.setVelX(evaluator.getResult().getFloatValue());
                break;

            case "vel.y":
                // Error check
                if( evaluator.getResult().getType() != Type.FLOAT ){
                    Gdx.app.error("FSM", "ERROR: vel.y = ... expected Float but got " + evaluator.getResult().getType());
                    return null;
                }

                fighter.setVelY(evaluator.getResult().getFloatValue());
                break;

            // Set a variable if not a system assignment
            default:
                fighter.setVar(lhs, evaluator.getResult());
                break;
        }

        return null;
    }

    @Override
    public Void visitStateChangeStmt(FsmParser.StateChangeStmtContext ctx) {
        fighter.changeState(ctx.ID().getText());
        return null;
    }
}
