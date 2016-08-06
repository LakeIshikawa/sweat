package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;

/**
 * Created by Lake on 04/08/2016.
 */
public class AnimationEditorMenuBar extends MenuBar {

    private AnimationEditorScreen editorScreen;

    /**
     * Create the main manu bar
     * @param animationEditorScreen
     */
    public AnimationEditorMenuBar(AnimationEditorScreen animationEditorScreen) {
        this.editorScreen = animationEditorScreen;

        Menu fileMenu = new Menu("File");
        fileMenu.addItem(new MenuItem("New", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.newAnimationPack();
            }
        }));
        fileMenu.addItem(new MenuItem("Open", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.open();
            }
        }));
        fileMenu.addItem(new MenuItem("Save", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.save();
            }
        }));
        addMenu(fileMenu);
    }
}
