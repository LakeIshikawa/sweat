package com.lksoft.yugen.tools.settingseditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.stateless.Settings;

import java.io.File;

/**
 * Created by Lake on 07/06/2016.
 */
public class SettingsEditorScreen implements Screen {

    // Chooser Path
    private File path;

    // UI
    private Stage stage;

    // GUI components
    private SettingsWindow settingsWindow;

    // Screen size info
    private int lastW, lastH;

    /**
     * Create spriteDef editor
     * @param path
     */
    public SettingsEditorScreen(File path){
        this.path = path;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // -- Create GUI components
        float w = stage.getViewport().getWorldWidth();
        float h = stage.getViewport().getWorldHeight();

        // Settings window
        settingsWindow = new SettingsWindow(this);
        settingsWindow.setSize(450, 500);
        settingsWindow.setPosition(w/2-settingsWindow.getWidth()/2, h/2 - settingsWindow.getHeight()/2);

        // Load settings
        Settings settings = Settings.read(Gdx.files.internal(Yugen.SETTINGS_FILE));
        settingsWindow.setSettings(settings);

        stage.addActor(settingsWindow);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render ui
        stage.act();
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
