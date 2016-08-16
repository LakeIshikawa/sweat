package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.lksoft.yugen.FsmLexer;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.visitor.CommandsSetupVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * Created by Lake on 18/06/2016.
 */
public class Commands {

    // Commands table
    private ObjectMap<String, CommandDef> commands = new ObjectMap<>();

    /**
     * Create commands resources from cmd file
     * @param cmdFile .cmd file
     */
    public Commands(FileHandle cmdFile) throws IOException {
        // Parse script
        FsmLexer lexer = new FsmLexer(new ANTLRInputStream(cmdFile.read()));
        FsmParser parser = new FsmParser(new CommonTokenStream(lexer));
        FsmParser.CmdContext commands = parser.cmd();

        // Create params and states
        CommandsSetupVisitor visitor = new CommandsSetupVisitor(this);
        commands.accept(visitor);
    }

    /**
     * Adds a command
     * @param name
     * @param command
     */
    public void addCommand(String name, CommandDef command){
        commands.put(name, command);
    }

    /**
     * Gets a command by name
     * @param name
     * @return
     */
    public CommandDef getCommandDef(String name){
        return commands.get(name);
    }

    /**
     * Merge another commands to this
     * @param customCommands
     */
    public void merge(Commands customCommands) {
        this.commands.putAll(customCommands.commands);
    }
}
