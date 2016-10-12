package com.lksoft.sweat.tools.spriteeditor;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lksoft.sweat.desktop.ResourceBuilder;

/**
 * Created by Lake on 07/06/2016.
 */
public class SpriteEditorLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        ResourceBuilder builder = new ResourceBuilder();
        builder.buildResources();
        new LwjglApplication(new SpriteEditor(), config);
    }
}
