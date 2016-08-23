package com.lksoft.yugen.tools.settingseditor;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

import java.io.File;

/**
 * Created by Lake on 09/06/2016.
 */
public class SettingsEditor extends Game {
    public static SettingsEditor instance;

    // Stages path
    private File path;

    /**
     * Create the sprite editor
     * @param path Path to prompt for file open dir
     */
    public SettingsEditor(File path){
        this.path = path;
        instance = this;
    }

    @Override
    public void create() {
        VisUI.load();
        setScreen(new SettingsEditorScreen(path));
    }
}
