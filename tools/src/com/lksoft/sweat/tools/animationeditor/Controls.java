package com.lksoft.sweat.tools.animationeditor;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by Stallman on 12/08/2016.
 */
public interface Controls extends InputProcessor {

    // Render overlay
    void render();

    // Copy
    void copy();

    // Paste
    void paste();
}
