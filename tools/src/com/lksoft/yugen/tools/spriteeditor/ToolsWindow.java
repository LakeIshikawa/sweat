package com.lksoft.yugen.tools.spriteeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 * Created by Lake on 05/08/2016.
 */
public class ToolsWindow extends VisWindow {
    // Editor screen
    private SpriteEditorScreen editorScreen;

    /**
     * Create tools window
     * @param editorScreen
     */
    public ToolsWindow(final SpriteEditorScreen editorScreen) {
        super("Tools");
        this.editorScreen = editorScreen;
        TableUtils.setSpacingDefaults(this);

        final VisTextButton copy = new VisTextButton("Copy");
        final VisTextButton paste = new VisTextButton("Paste");
        final VisTextButton pasteToAll = new VisTextButton("Paste to All");
        add(copy).growX();
        add(paste).growX();
        add(pasteToAll).growX();

        copy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.copy();
            }
        });
        paste.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.paste();
            }
        });
        paste.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.pasteToAll();
            }
        });
    }
}
