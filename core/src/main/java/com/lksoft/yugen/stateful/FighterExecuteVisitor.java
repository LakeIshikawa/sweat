package com.lksoft.yugen.stateful;

import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;

/**
 * Created by Lake on 11/06/2016.
 */
public class FighterExecuteVisitor extends FsmBaseVisitor<Void> {

    // Target fighter
    private Fighter fighter;

    // Results
    private String idResult;

    /**
     * Create execution visitor
     * @param fighter Fighter
     */
    public FighterExecuteVisitor(Fighter fighter) {
        this.fighter = fighter;
    }

    @Override
    public Void visitAssignment(FsmParser.AssignmentContext ctx) {
        String lhs = ctx.ID().getText();

        // System assignments
        switch (lhs){
            case "anim":
                ctx.e().accept(this);
                fighter.changeAnimation(idResult);
                break;
        }

        return null;
    }

    @Override
    public Void visitIdLiteral(FsmParser.IdLiteralContext ctx) {
        idResult = ctx.getText();
        return null;
    }
}
