package com.lksoft.sweat;

import com.badlogic.gdx.Game;

import java.io.IOException;

public class SweatGame extends Game {
    // Debug
    private boolean debug;
    // Compile scripts
    boolean compileScripts;

    /**
     * Start yugen
     */
    public SweatGame(boolean debug) {
        this.debug = debug;
    }

    @Override
	public void create () {
        try {
            Sweat sweat = new Sweat(debug);
            // Set screen
            setScreen(new SweatScreen(sweat));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
