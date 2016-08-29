package com.lksoft.sweat.tools.sceneeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;

/**
 * Created by Lake on 04/08/2016.
 */
public class SceneEditorMenuBar extends MenuBar {

    private SceneEditorScreen editorScreen;

    /**
     * Create the main manu bar
     * @param animationEditorScreen
     */
    public SceneEditorMenuBar(SceneEditorScreen animationEditorScreen) {
        this.editorScreen = animationEditorScreen;

        Menu fileMenu = new Menu("File");
        fileMenu.addItem(new MenuItem("New", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.newStage();
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

        Menu spritesMenu = new Menu("Fsm");
        spritesMenu.addItem(new MenuItem("Add", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.addFsm();
            }
        }));
        addMenu(spritesMenu);
    }
}
