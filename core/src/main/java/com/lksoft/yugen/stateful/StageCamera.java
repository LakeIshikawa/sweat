package com.lksoft.yugen.stateful;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lksoft.yugen.stateless.StageDef;

/**
 * Created by Lake on 09/06/2016.
 */
public abstract class StageCamera {

    // Stage parent
    protected Stage stage;

    // Viewport
    protected Viewport viewport;

    // Camera start position
    private Vector2 cameraStart = new Vector2();

    /**
     * Create b1 stage camera from b1 stage layout
     * @param stageDef The stage layout
     */
    public StageCamera(StageDef stageDef){
        viewport = new FitViewport(stageDef.getCameraW(), stageDef.getCameraH());
        getCameraStart().set(0, stageDef.getCameraOffsetY());
        viewport.getCamera().position.set(0, stageDef.getCameraOffsetY(), 0);
    }

    /**
     * Set the parent stage
     * @param stage
     */
    void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Change screen size
     * @param width
     * @param height
     */
    public void resize(int width, int height){
        viewport.update(width, height);
    }

    // Camera control
    public abstract void update(Stage stage);

    /**
     * @return Combined viewport matrix
     */
    public Matrix4 getCombinedMatrix() {
        return viewport.getCamera().combined;
    }

    /**
     * @return Camera position
     */
    public Vector3 getPosition() {
        return viewport.getCamera().position;
    }

    /**
     * @return Camera start position
     */
    public Vector2 getCameraStart() {
        return cameraStart;
    }
}
