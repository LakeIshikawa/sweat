package com.lksoft.yugen.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.lksoft.yugen.stateful.Stage;

/**
 * Created by Lake on 09/06/2016.
 *
 * Screen for testing how the stage looks at runtime
 */
public class TestScreen implements Screen {

    // Screen to go back to
    private EditorScreen backTo;
    // Stage
    private Stage stage;

    /**
     * Create a test screen for a stage
     * @param editorScreen
     * @param stage
     */
    public TestScreen(EditorScreen editorScreen, Stage stage) {
        this.backTo = editorScreen;
        this.stage = stage;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.update();
        stage.render();

        // Go back
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            StageEditor.instance.setScreen(backTo);
        }
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
