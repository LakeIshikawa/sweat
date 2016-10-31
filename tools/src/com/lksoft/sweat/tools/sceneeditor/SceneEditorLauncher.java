package com.lksoft.sweat.tools.sceneeditor;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lksoft.sweat.desktop.ResourceBuilder;

/**
 * Created by Lake on 07/06/2016.
 *
 * Launcher for the Scene Editor
 */
public class SceneEditorLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        // Build resources
        ResourceBuilder builder = new ResourceBuilder();
        builder.buildResources();
        new LwjglApplication(new SceneEditor(), config);
    }
}
