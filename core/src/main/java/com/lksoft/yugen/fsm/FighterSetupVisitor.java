package com.lksoft.yugen.fsm;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.stateless.FighterDef;
import com.lksoft.yugen.stateless.FighterState;

/**
 * Created by Lake on 11/06/2016.
 */
public class FighterSetupVisitor extends FsmBaseVisitor<Void> {

    // Fighter
    private FighterDef fighter;

    // Status
    private FighterState currentState = null;
    private FighterState.FighterTrigger currentTrigger = null;
    private Array<FsmParser.StatementContext> currentStatements = null;
    private float currentFloat;

    /**
     * Create visitor to setup fighter (create states etc.)
     * @param fighter Fighter to setup
     */
    public FighterSetupVisitor(FighterDef fighter){
        this.fighter = fighter;
    }

    @Override
    public Void visitParam(FsmParser.ParamContext ctx) {
        if( ctx.children == null ) return null;

        ctx.assignment().accept(this);
        return null;
    }

    @Override
    public Void visitStateless(FsmParser.StatelessContext ctx) {
        if( ctx.children == null ) return null;

        currentState = null;
        ctx.triggers().accept(this);
        return null;
    }

    @Override
    public Void visitStates(FsmParser.StatesContext ctx) {
        if( ctx.children == null ) return null;
        return visitChildren(ctx);
    }

    @Override
    public Void visitState(FsmParser.StateContext ctx) {
        currentState = new FighterState(ctx.ID(0).getText(),
                FighterState.StateType.valueOf(ctx.ID(1).getText()));
        ctx.triggers().accept(this);

        fighter.getStates().put(currentState.name, currentState);
        return null;
    }

    @Override
    public Void visitSystemTrigger(FsmParser.SystemTriggerContext ctx) {
        currentStatements = new Array<>();
        ctx.statements().accept(this);

        switch (ctx.ID().getText()){
            case "Enter":
            case "enter":
                currentState.enterTrigger = currentStatements;
                break;

            case "Exit":
            case "exit":
                currentState.exitTrigger = currentStatements;
                break;
        }
        return null;
    }

    @Override
    public Void visitSingleCondTrigger(FsmParser.SingleCondTriggerContext ctx) {
        currentStatements = new Array<>();
        ctx.statements().accept(this);
        FighterState.FighterTrigger trigger = new FighterState.FighterTrigger(currentStatements);
        trigger.addCondition(0, ctx.e());

        if( currentState != null ) currentState.triggers.add(trigger);
        else fighter.getTriggers().add(trigger);
        return null;
    }

    @Override
    public Void visitMultiCondTrigger(FsmParser.MultiCondTriggerContext ctx) {
        currentStatements = new Array<>();
        ctx.statements().accept(this);

        currentTrigger = new FighterState.FighterTrigger(currentStatements);
        ctx.triglist().accept(this);

        if( currentState != null ) currentState.triggers.add(currentTrigger);
        else fighter.getTriggers().add(currentTrigger);
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

    @Override
    public Void visitAssignment(FsmParser.AssignmentContext ctx) {
        currentFloat = 0;
        ctx.e().accept(this);

        fighter.getParams().put(ctx.ID().getText(), currentFloat);
        return null;
    }

    @Override
    public Void visitFloatLiteral(FsmParser.FloatLiteralContext ctx) {
        currentFloat = Float.parseFloat(ctx.getText());
        return null;
    }

    @Override
    public Void visitIntLiteral(FsmParser.IntLiteralContext ctx) {
        currentFloat = Integer.parseInt(ctx.getText());
        return null;
    }
}
