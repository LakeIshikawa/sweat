package com.lksoft.sweat.tools.settingseditor;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

/**
 * Created by Lake on 09/06/2016.
 */
public class SettingsEditor extends Game {
    public static SettingsEditor instance;

    /**
     * Create the sprite editor
     */
    public SettingsEditor(){
        instance = this;
    }

    @Override
    public void create() {
        VisUI.load();
        setScreen(new SettingsEditorScreen());
    }
}
