package com.lksoft.sweat.tools.sceneeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lksoft.sweat.stateless.SceneDef;

import java.util.Comparator;

/**
 * Created by Lake on 08/06/2016.
 *
 * Renderer for scene def
 */
public class SceneFsmDefRenderer {
    // Some colors
    public static final Color FLOOR = Color.GREEN;
    public static final Color ORIGIN = Color.BLACK;
    public static final Color CAMERA = Color.WHITE;
    private static final Color SELECTION = new Color(0, 0, 1, 0.3f);


    // The stage layout to render
    private SceneDef sceneDef;

    // Viewport
    private Viewport viewport = new ScreenViewport();

    // Batch
    private SpriteBatch batch = new SpriteBatch();
    // Shape
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    // Sorted defs
    private Array<SceneDef.SceneFsmDef> sorted = new Array<>();
    private ObjectMap<SceneDef.SceneFsmDef, Integer> positions = new ObjectMap<>();

    // Touch unprojection
    private Vector2 touch = new Vector2();
    private Rectangle bounds = new Rectangle();


    /**
     * Create b1 renderer for b1 stage layout
     * @param sceneDef The stage layout to render
     */
    public SceneFsmDefRenderer(SceneDef sceneDef) {
        this.sceneDef = sceneDef;
        shapeRenderer.setAutoShapeType(true);
    }

    /**
     * Render the stage layout
     * @param selection
     */
    public void render(SceneDef.SceneFsmDef selection) {
        // Draw sprites
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        positions.clear();
        for( int i=0; i<sceneDef.layout.size; i++ ){
            positions.put(sceneDef.layout.get(i), i);
        }
        sorted.clear();
        sorted.addAll(sceneDef.layout);

        sorted.sort(new Comparator<SceneDef.SceneFsmDef>() {
            @Override
            public int compare(SceneDef.SceneFsmDef o1, SceneDef.SceneFsmDef o2) {
                if( o1.layer < o2.layer ) return -1;
                if( o1.layer > o2.layer ) return 1;
                if( positions.get(o1) < positions.get(o2) ) return -1;
                return 1;
            }
        });
        for(SceneDef.SceneFsmDef fsmDef : sorted) {
            fsmDef.loadAnimationDef().getFrameAt(0).draw(batch, fsmDef.x, fsmDef.y, 1, false);
        }

        batch.end();

        // Draw lines
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        // Draw floor line
        shapeRenderer.setColor(FLOOR);
        shapeRenderer.line(sceneDef.area_l, 0, sceneDef.area_r, 0);

        // Draw origin
        shapeRenderer.setColor(ORIGIN);
        shapeRenderer.line(-30, 0, 30, 0);
        shapeRenderer.line(0, -30, 0, 30);

        // Draw bounds
        shapeRenderer.rect(sceneDef.area_l, sceneDef.area_b,
                sceneDef.area_r - sceneDef.area_l, sceneDef.area_t - sceneDef.area_b);

        // Draw camera
        shapeRenderer.setColor(CAMERA);
        int cameraX = -sceneDef.camera_width/2 + sceneDef.camera_x;
        int cameraY = -sceneDef.camera_height/2 + sceneDef.camera_y;
        shapeRenderer.rect(cameraX, cameraY, sceneDef.camera_width, sceneDef.camera_height);

        // Draw selection
        if( selection != null ){
            shapeRenderer.setColor(SELECTION);
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

            selection.getBounds(bounds);
            shapeRenderer.rect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
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
