package com.lksoft.sweat.tools.animationeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 * Created by Lake on 05/08/2016.
 */
public class ToolsWindow extends VisWindow {
    // Editor screen
    private AnimationEditorScreen editorScreen;

    // Control button group
    private ButtonGroup controlGroup;

    /**
     * Create tools window
     * @param editorScreen
     */
    public ToolsWindow(final AnimationEditorScreen editorScreen) {
        super("Tools");
        this.editorScreen = editorScreen;
        TableUtils.setSpacingDefaults(this);

        final VisTextButton components = new VisTextButton("Regions", "toggle");
        final VisTextButton collisions = new VisTextButton("CollRects", "toggle");
        controlGroup = new ButtonGroup(components, collisions);
        components.setChecked(true);

        // Listener
        ChangeListener toggleListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( controlGroup.getChecked() == components ){
                    editorScreen.setControls(new ComponentControls(editorScreen));
                } else {
                    editorScreen.setControls(new CollisionControls(editorScreen));
                }
            }
        };
        components.addListener(toggleListener);
        collisions.addListener(toggleListener);

        final VisTextButton copy = new VisTextButton("Copy");
        final VisTextButton paste = new VisTextButton("Paste");
        final VisTextButton play = new VisTextButton("PLAY", "toggle");

        add(components).growX();
        add(collisions).growX();
        add(copy).growX();
        add(paste).growX();
        add(play).growX();

        copy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.getCurrentControls().copy();
            }
        });
        paste.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.getCurrentControls().paste();
            }
        });
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( play.isChecked() ) {
                    editorScreen.play();
                } else {
                    editorScreen.stop();
                }
            }
        });
    }
}
