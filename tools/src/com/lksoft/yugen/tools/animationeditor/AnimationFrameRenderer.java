package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lksoft.yugen.stateless.Frame;

/**
 * Created by Lake on 04/08/2016.
 */
public class AnimationFrameRenderer {

    // Current frame
    private Frame frame;

    // Viewport
    private Viewport viewport = new ScreenViewport();

    // Batch
    private SpriteBatch batch = new SpriteBatch();
    // Shape
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    /**
     * Sets frame to render
     * @param frame A frame
     */
    public void setFrame(Frame frame){
        this.frame = frame;
    }

    /**
     * Render the animation def
     */
    public void render() {
        // Draw sprites
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        if( frame != null ) {
            frame.draw(batch, 0, 0, 1, false);
        }

        batch.end();
    }

    /**
     * Resizes the viewport
     * @param width
     * @param height
     */
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * @return The viewport
     */
    public Viewport getViewport() {
        return viewport;
    }
}
