package com.lksoft.yugen;

import com.badlogic.gdx.Game;

import java.io.IOException;

public class YugenGame extends Game {
    // Debug
    private boolean debug;
    // Compile scripts
    boolean compileScripts;

    /**
     * Start yugen
     */
    public YugenGame(boolean debug) {
        this.debug = debug;
    }

    @Override
	public void create () {
        try {
            Yugen yugen = new Yugen(debug);
            // Set screen
            setScreen(new YugenScreen(yugen));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
