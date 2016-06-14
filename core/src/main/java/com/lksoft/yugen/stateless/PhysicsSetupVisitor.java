package com.lksoft.yugen.stateless;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;

/**
 * Created by Lake on 13/06/2016.
 */
public class PhysicsSetupVisitor extends FsmBaseVisitor<Void> {

    private FighterState.FighterTrigger currentTrigger = null;
    private Array<FsmParser.StatementContext> currentStatements = null;
    private PhysicsDef physicsDef;

    /**
     * Create a setup visitor for a physic def
     * @param physicsDef
     */
    public PhysicsSetupVisitor(PhysicsDef physicsDef){
        this.physicsDef = physicsDef;
    }

    @Override
    public Void visitSingleCondTrigger(FsmParser.SingleCondTriggerContext ctx) {
        currentStatements = new Array<>();
        if( ctx.statementsOpt() != null ) {
            ctx.statementsOpt().accept(this);
        }
        FighterState.FighterTrigger trigger = new FighterState.FighterTrigger(currentStatements);
        trigger.addCondition(0, ctx.e());

        physicsDef.getTriggers().add(trigger);
        return null;
    }

    @Override
    public Void visitMultiCondTrigger(FsmParser.MultiCondTriggerContext ctx) {
        currentStatements = new Array<>();
        if( ctx.statementsOpt() != null ) {
            ctx.statementsOpt().accept(this);
        }

        currentTrigger = new FighterState.FighterTrigger(currentStatements);
        ctx.triglist().accept(this);

        physicsDef.getTriggers().add(currentTrigger);
        return null;
    }

    @Override
    public Void visitTrigel(FsmParser.TrigelContext ctx) {
        currentTrigger.addCondition(Integer.parseInt(ctx.INT().getText())-1, ctx.e());
        return null;
    }

    @Override
    public Void visitStatements(FsmParser.StatementsContext ctx) {
        currentStatements.add(ctx.statement());
        if( ctx.statements() != null ) {
            ctx.statements().accept(this);
        }
        return null;
    }
}
