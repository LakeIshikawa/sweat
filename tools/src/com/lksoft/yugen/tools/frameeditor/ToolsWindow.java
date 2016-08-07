package com.lksoft.yugen.tools.frameeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.lksoft.yugen.tools.animationeditor.AnimationEditorScreen;

/**
 * Created by Lake on 05/08/2016.
 */
public class ToolsWindow extends VisWindow {
    // Editor screen
    private FrameEditorScreen editorScreen;

    /**
     * Create tools window
     * @param editorScreen
     */
    public ToolsWindow(final FrameEditorScreen editorScreen) {
        super("Tools");
        this.editorScreen = editorScreen;
        TableUtils.setSpacingDefaults(this);

        final VisTextButton copy = new VisTextButton("Copy");
        final VisTextButton paste = new VisTextButton("Paste");
        add(copy).growX();
        add(paste).growX();

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
    }
}
