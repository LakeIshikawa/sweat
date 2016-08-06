package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.io.File;

/**
 * Created by Lake on 07/06/2016.
 */
public class AnimationEditorLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;

        File path = new File(".");
        if( arg.length > 0 ) path = new File(arg[0]);
        new LwjglApplication(new AnimationEditor(path), config);
    }
}
