package com.lksoft.yugen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.lksoft.yugen.screens.FightScreen;
import com.lksoft.yugen.stateful.FightCamera;
import com.lksoft.yugen.stateful.Fighter;
import com.lksoft.yugen.stateful.Stage;
import com.lksoft.yugen.stateless.*;

import java.io.IOException;
import java.util.HashMap;

public class Yugen extends Game {
    private static Yugen self;
    public static Yugen i(){return self;}

    // Files to load for vs test mode
    private String stageFile;
    private String p1File;
    private String p2File;

    // Settings
    private Settings settings;

    // Physics
    private HashMap<String, PhysicsDef> physics = new HashMap<>();

    /**
     * Start normal mode
     */
    public Yugen() {
        self = this;
    }

    /**
     * Start vs mode
     */
    public Yugen(String stageFile, String p1File, String p2File) {
        this();
        this.stageFile = stageFile;
        this.p1File = p1File;
        this.p2File = p2File;
    }

    @Override
	public void create () {
        try {
            // Parse settings
            settings = new Settings(Gdx.files.internal("shared/settings.def"));

            // Parse physics
            FileHandle physicsFolder = Gdx.files.internal("shared/physics/");
            for( FileHandle f : physicsFolder.list() ){
                if( f.extension().equalsIgnoreCase("phy") ){
                    physics.put(f.nameWithoutExtension(), new PhysicsDef(f));
                }
            }

            // Set game mode
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

    public Settings getSettings() {
        return settings;
    }
    public PhysicsDef getPhysicsDef(String name){ return physics.get(name); }
}
