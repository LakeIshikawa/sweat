package com.lksoft.yugen.tools.stageeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lksoft.yugen.stateless.SpriteDef;
import com.lksoft.yugen.stateless.StageDef;

/**
 * Created by Lake on 08/06/2016.
 *
 * Renderer for stage layout
 */
public class StageDefRenderer {
    // Some colors
    private static final Color FLOOR = Color.GREEN;
    private static final Color ORIGIN = Color.BLACK;
    private static final Color CAMERA = Color.WHITE;
    private static final Color SELECTION = new Color(0, 0, 1, 0.3f);


    // The stage layout to render
    private StageDef stageDef;

    // Viewport
    private Viewport viewport = new ScreenViewport();

    // Batch
    private SpriteBatch batch = new SpriteBatch();
    // Shape
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    // Touch unprojection
    private Vector2 touch = new Vector2();


    /**
     * Create b1 renderer for b1 stage layout
     * @param stageDef The stage layout to render
     */
    public StageDefRenderer(StageDef stageDef) {
        this.stageDef = stageDef;
        shapeRenderer.setAutoShapeType(true);
    }

    /**
     * Render the stage layout
     * @param selection
     */
    public void render(SpriteDef selection) {
        // Draw sprites
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        for(int l = 0; l< stageDef.getLayers().length; l++ ) {
            for (SpriteDef def : stageDef.getLayers()[l]) {
                def.getResource().getFrameAt(0).draw(batch, def.getStartX(), def.getStartY(), 1, false);
            }
        }

        batch.end();

        // Draw lines
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        // Draw floor line
        shapeRenderer.setColor(FLOOR);
        shapeRenderer.line(stageDef.getAreaL(), 0, stageDef.getAreaR(), 0);

        // Draw origin
        shapeRenderer.setColor(ORIGIN);
        shapeRenderer.line(-30, 0, 30, 0);
        shapeRenderer.line(0, -30, 0, 30);

        // Draw bounds
        shapeRenderer.rect(stageDef.getAreaL(), stageDef.getAreaB(),
                stageDef.getAreaR() - stageDef.getAreaL(), stageDef.getAreaT() - stageDef.getAreaB());

        // Draw camera
        shapeRenderer.setColor(CAMERA);
        int cameraY = -stageDef.getCameraH()/2 + stageDef.getCameraOffsetY();
        shapeRenderer.rect(-stageDef.getCameraW()/2, cameraY, stageDef.getCameraW(), stageDef.getCameraH());

        // Draw selection
        if( selection != null ){
            shapeRenderer.setColor(SELECTION);
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            Rectangle b = selection.getBounds();
            shapeRenderer.rect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
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
     * Moves the camera relative to current position
     * @param dx
     * @param dy
     */
    public void moveCamera(int dx, int dy) {
        viewport.getCamera().translate(dx, dy, 0);
        viewport.getCamera().update();
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
