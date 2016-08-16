package com.lksoft.yugen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Lake on 11/06/2016.
 */
public class YugenScreen implements Screen {

    // The engine
    private Yugen yugen;

    // Viewport
    private YugenCamera camera = new YugenCamera();
    // Batch
    private SpriteBatch batch = new SpriteBatch();

    // For collision check
    private Rectangle p1Rect = new Rectangle();
    private Rectangle p2Rect = new Rectangle();

    // Debug rendering
    private boolean debug = false;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    /**
     * Create yugen screen
     * @param yugen The engine
     */
    public YugenScreen(Yugen yugen) {
        this.yugen = yugen;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.getCamera().combined);
        batch.begin();
        yugen.update();
        yugen.render(batch, camera);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.update(width, height);
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
