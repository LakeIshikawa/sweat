package com.lksoft.yugen.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lksoft.yugen.stateful.Stage;
import com.lksoft.yugen.stateful.StageCamera;
import com.lksoft.yugen.stateless.StageDef;

/**
 * Created by Lake on 09/06/2016.
 */
public class ManualCamera extends StageCamera {

    /**
     * Create a stage camera from a stage layout
     *
     * @param stageDef The stage layout
     */
    public ManualCamera(StageDef stageDef) {
        super(stageDef);
    }

    @Override
    public void update(Stage stage) {
        // Move camera around
        if( Gdx.input.isKeyPressed(Input.Keys.RIGHT) ){
            viewport.getCamera().translate(10, 0, 0);
            viewport.getCamera().update();
        }
        if( Gdx.input.isKeyPressed(Input.Keys.LEFT) ){
            viewport.getCamera().translate(-10, 0, 0);
            viewport.getCamera().update();
        }
        if( Gdx.input.isKeyPressed(Input.Keys.UP) ){
            viewport.getCamera().translate(0, 10, 0);
            viewport.getCamera().update();
        }
        if( Gdx.input.isKeyPressed(Input.Keys.DOWN) ){
            viewport.getCamera().translate(0, -10, 0);
            viewport.getCamera().update();
        }
    }
}
