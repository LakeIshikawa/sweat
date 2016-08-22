package com.lksoft.yugen.stateless;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.CommandLexer;
import com.lksoft.yugen.CommandParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * Created by Lake on 16/06/2016.
 */
public class CommandDef {

    /**
     * Command key
     */
    public enum CommandKey {
        F, B, U, D, B1, B2, B3, B4, B5, B6
    }

    /**
     * One command token
     */
    public static class CommandToken {
        public Array<CommandKey> keys = new Array<>();
        public boolean hold;
        public boolean release;
        public boolean exclusive;
        public int time = -1;
    }

    /**
     * Command token sequence
     */
    public Array<CommandToken> sequence = new Array<>();

    /**
     * Maximum allowed time
     */
    public int time;

    /**
     * Parse command from string
     * Ex: !F, 30.$D+B1
     * @param commandString
     */
    public static CommandDef parse(String commandString){
        CommandLexer lexer = new CommandLexer(new ANTLRInputStream(commandString));
        CommandParser parser = new CommandParser(new CommonTokenStream(lexer));
        CommandParser.CommandContext ctx = parser.command();

        CommandSetupVisitor visitor = new CommandSetupVisitor();
        return ctx.accept(visitor);
    }

}
