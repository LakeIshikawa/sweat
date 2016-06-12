package com.lksoft.yugen.desktop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by Lake on 06/06/2016.
 */
public class YugenTexturePacker {
    // Settings
    static TexturePacker.Settings settings;

    public static void main(String[] args){
        // Pack images
        settings = new TexturePacker.Settings();
        settings.maxWidth = 4096;
        settings.maxHeight = 4096;
        settings.filterMin = Texture.TextureFilter.Linear;
        settings.filterMag = Texture.TextureFilter.Linear;
        settings.stripWhitespaceX = true;
        settings.stripWhitespaceY = true;
        settings.useIndexes = false;

        makeChar("valkyrie");
        makeChar("yui");

        makeStage("lionking");
    }

    private static void makeChar(String name) {
        TexturePacker.process(settings, "../../rawres/"+name, "chars/"+name, name);
    }

    private static void makeStage(String name) {
        TexturePacker.process(settings, "../../rawres/"+name, "stages/"+name, name);
    }
}
