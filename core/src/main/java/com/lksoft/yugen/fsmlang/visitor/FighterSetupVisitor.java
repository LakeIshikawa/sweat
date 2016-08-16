package com.lksoft.yugen.fsmlang.visitor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.stateless.FsmDef;
import com.lksoft.yugen.stateless.FsmDefReader;
import com.lksoft.yugen.stateless.FsmState;

import java.io.IOException;

/**
 * Created by Lake on 11/06/2016.
 */
public class FighterSetupVisitor extends FsmBaseVisitor<Void> {

    // Fsm
    private FsmDef fighter;

    // Status
    private FsmState currentState = null;
    private FsmState.FighterTrigger currentTrigger = null;
    private Array<FsmParser.StatementContext> currentStatements = null;

    /**
     * Create visitor to setup fighter (create states etc.)
     * @param fighter Fsm to setup
     */
    public FighterSetupVisitor(FsmDef fighter){
        this.fighter = fighter;
    }

    @Override
    public Void visitIncludeOpt(FsmParser.IncludeOptContext ctx){
        if( ctx.children == null ) return null;

        // Parse fsm
        String path = ctx.STRING().getText().replaceAll("\"", "");
        FileHandle fsm = Gdx.files.internal(path);
        try {
            new FsmDefReader(fsm).read(fighter);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    public Void visitStateNormal(FsmParser.StateNormalContext ctx) {
        currentState = new FsmState(ctx.ID().getText());
        ctx.triggers().accept(this);

        fighter.getStates().put(currentState.name, currentState);
        return null;
    }

    @Override
    public Void visitStateStart(FsmParser.StateStartContext ctx) {
        currentState = new FsmState(ctx.ID().getText());
        ctx.triggers().accept(this);

        fighter.getStates().put(currentState.name, currentState);
        fighter.setInitialState(currentState.name);
        return null;
    }

    @Override
    public Void visitSystemTrigger(FsmParser.SystemTriggerContext ctx) {
        currentStatements = new Array<>();
        if( ctx.statementsOpt() != null ) {
            ctx.statementsOpt().accept(this);
        }

        switch (ctx.ID().getText()) {
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
        if( ctx.statementsOpt() != null ) {
            ctx.statementsOpt().accept(this);
        }
        FsmState.FighterTrigger trigger = new FsmState.FighterTrigger(currentStatements);
        trigger.addCondition(0, ctx.e());

        if( currentState != null ) currentState.triggers.add(trigger);
        else fighter.getTriggers().add(trigger);
        return null;
    }

    @Override
    public Void visitMultiCondTrigger(FsmParser.MultiCondTriggerContext ctx) {
        currentStatements = new Array<>();
        if (ctx.statementsOpt() != null) {
            ctx.statementsOpt().accept(this);
        }

        currentTrigger = new FsmState.FighterTrigger(currentStatements);
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
}
