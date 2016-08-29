package com.lksoft.sweat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Lake on 11/06/2016.
 */
public class SweatScreen implements Screen {

    // The engine
    private Sweat sweat;

    // Batch
    private SpriteBatch batch = new SpriteBatch();
    // Shape batch
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    /**
     * Create sweat screen
     * @param sweat The engine
     */
    public SweatScreen(Sweat sweat) {
        this.sweat = sweat;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sweat.update();

        // No camera init, no party
        if( sweat.getCamera().getInitPosition() != null ) {
            batch.setProjectionMatrix(sweat.getCamera().getCamera().combined);
            batch.begin();
            sweat.render(batch);
            batch.end();

            shapeRenderer.setProjectionMatrix(sweat.getCamera().getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            sweat.renderDebug(shapeRenderer);
            shapeRenderer.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        sweat.getCamera().update(width, height);
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
