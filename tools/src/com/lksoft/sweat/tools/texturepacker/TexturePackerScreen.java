package com.lksoft.sweat.tools.texturepacker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Lake on 07/06/2016.
 */
public class TexturePackerScreen implements Screen {

    // UI
    private Stage stage;

    // GUI components
    private TexturePackerWindow texturePackerWindow;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // -- Create GUI components
        float w = stage.getViewport().getWorldWidth();
        float h = stage.getViewport().getWorldHeight();

        // Settings window
        texturePackerWindow = new TexturePackerWindow(this);
        texturePackerWindow.setSize(500, 150);
        texturePackerWindow.setPosition(w/2- texturePackerWindow.getWidth()/2, h/2 - texturePackerWindow.getHeight()/2);

        stage.addActor(texturePackerWindow);
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
