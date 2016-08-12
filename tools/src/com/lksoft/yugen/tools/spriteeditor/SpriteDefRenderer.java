package com.lksoft.yugen.tools.spriteeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lksoft.yugen.stateless.SpriteDef;

/**
 * Created by Lake on 04/08/2016.
 */
public class SpriteDefRenderer {

    // Current spriteDef
    private SpriteDef spriteDef;

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
     * @param spriteDef A spriteDef
     */
    public void setSpriteDef(SpriteDef spriteDef){
        this.spriteDef = spriteDef;
    }

    /**
     * Render the animation def
     */
    public void render() {
        // Draw sprites
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        if( spriteDef != null ) {
            spriteDef.draw(batch, 0, 0, 1, 1, false, 0);
        }

        batch.end();

        // Geometry
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Origin
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.line(-10, 0, 10, 0);
        shapeRenderer.line(0, -10, 0, 10);

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
}
