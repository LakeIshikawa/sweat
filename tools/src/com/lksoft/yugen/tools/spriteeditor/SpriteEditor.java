package com.lksoft.yugen.tools.spriteeditor;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

import java.io.File;

/**
 * Created by Lake on 09/06/2016.
 */
public class SpriteEditor extends Game {
    public static SpriteEditor instance;

    /**
     * Create the sprite editor
     */
    public SpriteEditor(){
        instance = this;
    }

    @Override
    public void create() {
        VisUI.load();
        setScreen(new SpriteEditorScreen());
    }
}
