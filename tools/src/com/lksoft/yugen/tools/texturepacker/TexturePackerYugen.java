package com.lksoft.yugen.tools.texturepacker;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

/**
 * Created by Lake on 09/06/2016.
 */
public class TexturePackerYugen extends Game {
    public static TexturePackerYugen instance;

    /**
     * Create the sprite editor
     */
    public TexturePackerYugen(){
        instance = this;
    }

    @Override
    public void create() {
        VisUI.load();
        setScreen(new TexturePackerScreen());
    }
}
