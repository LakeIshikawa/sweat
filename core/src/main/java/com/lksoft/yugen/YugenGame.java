package com.lksoft.yugen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.lksoft.yugen.stateful.Fsm;
import com.lksoft.yugen.stateless.FsmDef;
import com.lksoft.yugen.stateless.FsmDefReader;
import com.lksoft.yugen.stateless.Settings;

import java.io.IOException;

public class YugenGame extends Game {
    // Files to load for vs test mode
    private String fsmFile;
    // Debug
    private boolean debug;

    /**
     * Start yugen
     */
    public YugenGame(String fsmFile, boolean debug) {
        this.fsmFile = fsmFile;
        this.debug = debug;
    }

    @Override
	public void create () {
        try {
            Yugen yugen = new Yugen(Gdx.files.internal(fsmFile), debug);
            // Set screen
            setScreen(new YugenScreen(yugen));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
