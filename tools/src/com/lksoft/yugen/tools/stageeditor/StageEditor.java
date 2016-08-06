package com.lksoft.yugen.tools.stageeditor;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

import java.io.File;

/**
 * Created by Lake on 09/06/2016.
 */
public class StageEditor extends Game {
    public static StageEditor instance;

    // Stages path
    private File stagesPath;

    /**
     * Create the stage editor
     * @param stagesPath Path to prompt for stage file open dir
     */
    public StageEditor(File stagesPath){
        this.stagesPath = stagesPath;
        instance = this;
    }

    @Override
    public void create() {
        VisUI.load();
        setScreen(new StageEditorScreen(stagesPath));
    }
}
