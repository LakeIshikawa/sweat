package com.lksoft.sweat.tools.sceneeditor;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

/**
 * Created by Lake on 09/06/2016.
 */
public class SceneEditor extends Game {
    public static SceneEditor instance;

    /**
     * Create the stage editor
     */
    public SceneEditor(){
        instance = this;
    }

    @Override
    public void create() {
        VisUI.load();
        setScreen(new SceneEditorScreen());
    }
}
