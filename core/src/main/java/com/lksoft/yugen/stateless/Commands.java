package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;

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
