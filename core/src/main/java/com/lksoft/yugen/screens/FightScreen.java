package com.lksoft.yugen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.lksoft.yugen.stateful.Stage;

/**
 * Created by Lake on 11/06/2016.
 */
public class FightScreen implements Screen {

    // The stage
    private Stage stage;

    /**
     * Create fight screen
     * @param stage The stage to play
     */
    public FightScreen(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.update();
        stage.render();
    }

    @Override
    public void resize(int width, int height) {
        stage.resize(width, height);
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

    }
}
