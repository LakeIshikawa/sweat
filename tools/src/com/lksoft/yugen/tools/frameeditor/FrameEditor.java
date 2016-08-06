package com.lksoft.yugen.tools.frameeditor;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

import java.io.File;

/**
 * Created by Lake on 09/06/2016.
 */
public class FrameEditor extends Game {
    public static FrameEditor instance;

    // Stages path
    private File path;

    /**
     * Create the animation editor
     * @param path Path to prompt for file open dir
     */
    public FrameEditor(File path){
        this.path = path;
        instance = this;
    }

    @Override
    public void create() {
        VisUI.load();
        setScreen(new FrameEditorScreen(path));
    }
}
