package com.lksoft.sweat.tools.animationeditor;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by Lake on 07/06/2016.
 */
public class AnimationEditorLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        new LwjglApplication(new AnimationEditor(), config);
    }
}
