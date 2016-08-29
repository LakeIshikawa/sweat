package com.lksoft.sweat.tools.animationeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lksoft.sweat.stateless.AnimationFrame;

/**
 * Created by Lake on 04/08/2016.
 */
public class AnimationFrameRenderer {

    // Current spriteDef
    private AnimationFrame frame;

    // Viewport
    private Viewport viewport = new ScreenViewport();

    // Batch
    private SpriteBatch batch = new SpriteBatch();
    // Shape
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    // Touch unprojection
    private Vector2 touch = new Vector2();

    /**
     * Sets spriteDef to render
     * @param frame A spriteDef
     */
    public void setAnimationFrame(AnimationFrame frame){
        this.frame = frame;
    }

    /**
     * Render the animation def
     */
    public void render() {
        if( frame == null ) return;

        // Draw sprites
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        frame.draw(batch, 0, 0, 1, false);

        batch.end();

        // Geometry
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Origin
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.line(-10, 0, 10, 0);
        shapeRenderer.line(0, -10, 0, 10);

        // Origin of every component
        shapeRenderer.setColor(Color.ORANGE);
        for(AnimationFrame.Component c : frame.components ){
            shapeRenderer.line(c.x-10, c.y, c.x+10, c.y);
            shapeRenderer.line(c.x, c.y-10, c.x, c.y+10);
        }

        // Collision
        for(Rectangle r : frame.damageCollisions){
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(r.x, r.y, r.width, r.height);
        }
        for(Rectangle r : frame.hitCollisions){
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(r.x, r.y, r.width, r.height);
        }

        shapeRenderer.end();
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

    /**
     * @return Unprojected touch position
     */
    public Vector2 getTouch(int x, int y) {
        touch.x = x;
        touch.y = y;
        return viewport.unproject(touch);
    }

    /**
     * @return The ortho camera
     */
    public OrthographicCamera getCamera() {
        return (OrthographicCamera) viewport.getCamera();
    }
}
