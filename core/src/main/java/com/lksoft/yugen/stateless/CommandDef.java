package com.lksoft.yugen.stateless;

import com.badlogic.gdx.utils.Array;

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

}
