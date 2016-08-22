package com.lksoft.yugen.tools.sceneeditor;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.io.File;

/**
 * Created by Lake on 07/06/2016.
 */
public class SceneEditorLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;

        File stagesPath = new File(".");
        if( arg.length > 0 ) stagesPath = new File(arg[0]);
        new LwjglApplication(new SceneEditor(stagesPath), config);
    }
}
