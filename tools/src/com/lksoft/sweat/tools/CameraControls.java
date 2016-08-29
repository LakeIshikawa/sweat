package com.lksoft.sweat.tools;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lake on 22/08/2016.
 */
public class CameraControls implements InputProcessor {
    // Camera
    private OrthographicCamera camera;

    // Camera controls
    private Vector2 panTouchPos;
    private Vector2 camStartPos;

    public CameraControls(OrthographicCamera camera){
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Middle button = pan
        if( button == Input.Buttons.MIDDLE ){
            panTouchPos = new Vector2(screenX, screenY);
            camStartPos = new Vector2(camera.position.x, camera.position.y);
            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Middle button = pan
        if( button == Input.Buttons.MIDDLE ){
            panTouchPos = null;
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Middle button = pan
        if( panTouchPos != null ){
            Vector2 curPos = new Vector2(panTouchPos);
            curPos.sub(new Vector2(screenX, screenY));
            camera.position.set(camStartPos.x + curPos.x, camStartPos.y - curPos.y, 0);
            camera.update();
            return true;
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.zoom += 0.05f * amount;
        camera.update();
        return true;
    }
}
