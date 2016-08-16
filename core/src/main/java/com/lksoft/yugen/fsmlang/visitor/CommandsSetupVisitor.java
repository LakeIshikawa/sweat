package com.lksoft.yugen.fsmlang.visitor;

import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.stateless.CommandDef;
import com.lksoft.yugen.stateless.Commands;

/**
 * Created by Lake on 18/06/2016.
 */
public class CommandsSetupVisitor extends FsmBaseVisitor<Void> {
    // Commands to setup
    private Commands commands;

    // Current def
    private CommandDef currentCommand;
    // Current token
    private CommandDef.CommandToken currentToken;

    /**
     * Create commands setup visitor
     * @param commands
     */
    public CommandsSetupVisitor(Commands commands){
        this.commands = commands;
    }

    @Override
    public Void visitCommandline(FsmParser.CommandlineContext ctx) {
        String name = ctx.ID().getText();
        currentCommand = new CommandDef();
        currentCommand.time = Integer.parseInt(ctx.INT().getText());
        ctx.commandseq().accept(this);

        commands.addCommand(name, currentCommand);
        return null;
    }

    @Override
    public Void visitCtoken(FsmParser.CtokenContext ctx) {
        currentToken = new CommandDef.CommandToken();
        currentCommand.sequence.add(currentToken);
        ctx.exclusiveOpt().accept(this);
        ctx.modOpt().accept(this);
        ctx.timeOpt().accept(this);
        ctx.keylist().accept(this);
        return null;
    }

    @Override
    public Void visitExclusiveOpt(FsmParser.ExclusiveOptContext ctx) {
        currentToken.exclusive = ctx.children != null;
        return null;
    }

    @Override
    public Void visitHoldMod(FsmParser.HoldModContext ctx) {
        currentToken.hold = true;
        return null;
    }

    @Override
    public Void visitReleaseMod(FsmParser.ReleaseModContext ctx) {
        currentToken.release = true;
        return null;
    }

    @Override
    public Void visitTimeOpt(FsmParser.TimeOptContext ctx) {
        if( ctx.INT() != null ){
            currentToken.time = Integer.parseInt(ctx.INT().getText());
        }
        return null;
    }

    @Override
    public Void visitKeylist(FsmParser.KeylistContext ctx) {
        currentToken.keys.add(CommandDef.CommandKey.valueOf(ctx.KEY().getText()));

        // Recursion
        if( ctx.keylist() != null ){
            ctx.keylist().accept(this);
        }
        return null;
    }
}
