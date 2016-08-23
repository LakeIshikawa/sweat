package com.lksoft.yugen.tools.settingseditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.VisDialog;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.lksoft.yugen.stateless.Settings;

/**
 * Created by Stallman on 23/08/2016.
 */
public class PressKeyDialog extends VisDialog implements InputProcessor {

    private final SettingsWindow settingsWindow;

    Settings.KeySettings keys;
    int i;
    VisSelectBox<Settings.Key>[] boxes;

    /**
     * Create dialog
     */
    public PressKeyDialog(SettingsWindow settingsWindow) {
        super("Key input");
        this.settingsWindow = settingsWindow;
        centerWindow();
        setModal(true);
        text("Press any key.");

        Gdx.input.setInputProcessor(this);
    }

    // Show
    public void show(Stage stage, Settings.KeySettings keys, int i, VisSelectBox<Settings.Key>[] boxes){
        this.keys = keys;
        this.i = i;
        this.boxes = boxes;
        super.show(stage);
    }

    @Override
    public boolean keyDown(int keycode) {
        Settings.Key key = null;

        for(Settings.Key k : Settings.Key.values()){
            if( k.key == keycode ){
                settingsWindow.setKey(keys, i, k, boxes);
            }
        }

        hide();
        Gdx.input.setInputProcessor(getStage());
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
