package com.lksoft.yugen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lksoft.yugen.screens.FightScreen;
import com.lksoft.yugen.stateful.FightCamera;
import com.lksoft.yugen.stateful.Fighter;
import com.lksoft.yugen.stateful.Stage;
import com.lksoft.yugen.stateless.FighterDef;
import com.lksoft.yugen.stateless.StageDef;
import com.lksoft.yugen.stateless.StageDefReader;

import java.io.IOException;

public class Yugen extends Game {

    // Files to load for vs test mode
    private String stageFile;
    private String p1File;
    private String p2File;

    /**
     * Start normal mode
     */
    public Yugen() {}

    /**
     * Start vs mode
     */
    public Yugen(String stageFile, String p1File, String p2File) {
        this.stageFile = stageFile;
        this.p1File = p1File;
        this.p2File = p2File;
    }

    @Override
	public void create () {
        try {
            if( stageFile == null ){
                // TODO Set title screen!
            } else {
                StageDef layout = new StageDefReader(Gdx.files.internal(stageFile)).read();
                Fighter p1 = new Fighter(new FighterDef(Gdx.files.internal(p1File)));
                Fighter p2 = new Fighter(new FighterDef(Gdx.files.internal(p2File)));
                Stage stage = new Stage(layout, p1, p2, new FightCamera(layout));

                setScreen(new FightScreen(stage));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
