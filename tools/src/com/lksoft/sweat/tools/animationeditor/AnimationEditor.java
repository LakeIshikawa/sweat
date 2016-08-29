package com.lksoft.sweat.tools.animationeditor;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

/**
 * Created by Lake on 09/06/2016.
 */
public class AnimationEditor extends Game {
    public static AnimationEditor instance;

    /**
     * Create the animation editor
     */
    public AnimationEditor(){
        instance = this;
    }

    @Override
    public void create() {
        VisUI.load();
        setScreen(new AnimationEditorScreen());
    }
}
