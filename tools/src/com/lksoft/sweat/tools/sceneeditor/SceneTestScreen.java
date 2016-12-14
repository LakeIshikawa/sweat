package com.lksoft.sweat.tools.sceneeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lksoft.sweat.Resources;
import com.lksoft.sweat.Sweat;
import com.lksoft.sweat.stateless.SceneDef;

import java.io.IOException;

/**
 * Created by Lake on 09/06/2016.
 *
 * Screen for testing how the stage looks at runtime
 */
public class SceneTestScreen implements Screen {

    // Scene to test
    private FileHandle scnFile;
    // Screen to go back to
    private SceneEditorScreen backTo;

    // Batch
    private SpriteBatch batch = new SpriteBatch();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    // Sweat
    private Sweat sweat = new Sweat();

    // Scene
    private SceneDef sceneDef;


    /**
     * Create b1 test screen for b1 stage
     * @param sceneEditorScreen
     */
    public SceneTestScreen(SceneEditorScreen sceneEditorScreen, FileHandle scnFile) {
        this.backTo = sceneEditorScreen;
        this.scnFile = scnFile;
    }

    @Override
    public void show() {
        try {
            sceneDef = sweat.loadScene(Resources.toBin(scnFile));
            sweat.getCamera().init(sceneDef.camera_x, sceneDef.camera_y, sceneDef.camera_width, sceneDef.camera_height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sweat.update();

        batch.setProjectionMatrix(sweat.getCamera().getCamera().combined);
        batch.begin();
        sweat.render(batch);
        batch.end();

        // Draw lines
        shapeRenderer.setProjectionMatrix(sweat.getCamera().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        // Draw floor line
        shapeRenderer.setColor(SceneFsmDefRenderer.FLOOR);
        shapeRenderer.line(sceneDef.area_l, 0, sceneDef.area_r, 0);

        // Draw origin
        shapeRenderer.setColor(SceneFsmDefRenderer.ORIGIN);
        shapeRenderer.line(-30, 0, 30, 0);
        shapeRenderer.line(0, -30, 0, 30);

        // Draw bounds
        shapeRenderer.rect(sceneDef.area_l, sceneDef.area_b,
                sceneDef.area_r - sceneDef.area_l, sceneDef.area_t - sceneDef.area_b);
        shapeRenderer.end();

        // Go back
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            SceneEditor.instance.setScreen(backTo);
        }

        // Camera movement
        if( Gdx.input.isKeyPressed(Input.Keys.RIGHT) ){
            sweat.getCamera().getCamera().translate(10, 0, 0);
            sweat.getCamera().getCamera().update();
        }
        if( Gdx.input.isKeyPressed(Input.Keys.LEFT) ){
            sweat.getCamera().getCamera().translate(-10, 0, 0);
            sweat.getCamera().getCamera().update();
        }
        if( Gdx.input.isKeyPressed(Input.Keys.UP) ){
            sweat.getCamera().getCamera().translate(0, 10, 0);
            sweat.getCamera().getCamera().update();
        }
        if( Gdx.input.isKeyPressed(Input.Keys.DOWN) ){
            sweat.getCamera().getCamera().translate(0, -10, 0);
            sweat.getCamera().getCamera().update();
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
        sweat.clear();

    }

    @Override
    public void dispose() {

    }
}
