package com.lksoft.yugen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.io.IOException;

public class YugenGame extends Game {
    // Files to load for vs test mode
    private String fsmFile;
    // Debug
    private boolean debug;
    // Compile scripts
    boolean compileScripts;

    /**
     * Start yugen
     */
    public YugenGame(String fsmFile, boolean debug, boolean compileScripts) {
        this.fsmFile = fsmFile;
        this.debug = debug;
        this.compileScripts = compileScripts;
    }

    @Override
	public void create () {
        try {
            Yugen yugen = new Yugen(Gdx.files.internal(fsmFile), debug, compileScripts);
            // Set screen
            setScreen(new YugenScreen(yugen));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
