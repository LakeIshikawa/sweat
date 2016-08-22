package com.lksoft.yugen.tools.sceneeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.stateless.SceneDef;

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

    // Yugen
    private Yugen yugen = new Yugen();

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
            sceneDef = yugen.loadScene(scnFile);
            yugen.getCamera().init(sceneDef.camera_x, sceneDef.camera_y, sceneDef.camera_width, sceneDef.camera_height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        yugen.update();

        batch.setProjectionMatrix(yugen.getCamera().getCamera().combined);
        batch.begin();
        yugen.render(batch);
        batch.end();

        // Draw lines
        shapeRenderer.setProjectionMatrix(yugen.getCamera().getCamera().combined);
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
            yugen.getCamera().getCamera().translate(10, 0, 0);
            yugen.getCamera().getCamera().update();
        }
        if( Gdx.input.isKeyPressed(Input.Keys.LEFT) ){
            yugen.getCamera().getCamera().translate(-10, 0, 0);
            yugen.getCamera().getCamera().update();
        }
        if( Gdx.input.isKeyPressed(Input.Keys.UP) ){
            yugen.getCamera().getCamera().translate(0, 10, 0);
            yugen.getCamera().getCamera().update();
        }
        if( Gdx.input.isKeyPressed(Input.Keys.DOWN) ){
            yugen.getCamera().getCamera().translate(0, -10, 0);
            yugen.getCamera().getCamera().update();
        }
    }

    @Override
    public void resize(int width, int height) {
        yugen.getCamera().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        yugen.clear();

    }

    @Override
    public void dispose() {

    }
}
