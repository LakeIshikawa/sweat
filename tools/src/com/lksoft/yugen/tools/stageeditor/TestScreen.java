package com.lksoft.yugen.tools.stageeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

/**
 * Created by Lake on 09/06/2016.
 *
 * Screen for testing how the stage looks at runtime
 */
public class TestScreen implements Screen {

    // Screen to go back to
    private StageEditorScreen backTo;


    /**
     * Create b1 test screen for b1 stage
     * @param stageEditorScreen
     */
    public TestScreen(StageEditorScreen stageEditorScreen) {
        this.backTo = stageEditorScreen;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Go back
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            StageEditor.instance.setScreen(backTo);
        }
    }

    @Override
    public void resize(int width, int height) {

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
