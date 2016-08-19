package com.lksoft.yugen.stateless;

import com.lksoft.yugen.CommandBaseVisitor;
import com.lksoft.yugen.CommandParser;

/**
 * Created by Stallman on 19/08/2016.
 */
public class CommandSetupVisitor extends CommandBaseVisitor<CommandDef> {

    private CommandDef currentCommand;
    private CommandDef.CommandToken currentToken;

    @Override
    public CommandDef visitCommand(CommandParser.CommandContext ctx) {
        currentCommand = new CommandDef();
        currentCommand.time = Integer.parseInt(ctx.INT().getText());
        ctx.commandseq().accept(this);

        return currentCommand;
    }

    @Override
    public CommandDef visitCtoken(CommandParser.CtokenContext ctx) {
        currentToken = new CommandDef.CommandToken();
        currentCommand.sequence.add(currentToken);
        ctx.exclusiveOpt().accept(this);
        ctx.modOpt().accept(this);
        ctx.timeOpt().accept(this);
        ctx.keylist().accept(this);
        return null;
    }

    @Override
    public CommandDef visitExclusiveOpt(CommandParser.ExclusiveOptContext ctx) {
        currentToken.exclusive = ctx.children != null;
        return null;
    }

    @Override
    public CommandDef visitHoldMod(CommandParser.HoldModContext ctx) {
        currentToken.hold = true;
        return null;
    }

    @Override
    public CommandDef visitReleaseMod(CommandParser.ReleaseModContext ctx) {
        currentToken.release = true;
        return null;
    }

    @Override
    public CommandDef visitTimeOpt(CommandParser.TimeOptContext ctx) {
        if( ctx.INT() != null ){
            currentToken.time = Integer.parseInt(ctx.INT().getText());
        }
        return null;
    }

    @Override
    public CommandDef visitKeylist(CommandParser.KeylistContext ctx) {
        currentToken.keys.add(CommandDef.CommandKey.valueOf(ctx.KEY().getText()));

        // Recursion
        if( ctx.keylist() != null ){
            ctx.keylist().accept(this);
        }
        return null;
    }
}
