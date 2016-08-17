package com.lksoft.yugen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Lake on 15/08/2016.
 */
public class YugenCamera extends FitViewport {

    // Init position
    private Vector2 initPosition;

    /**
     * Create a new camera with default size positioned to 0,0
     */
    public YugenCamera() {
        super(640, 400);
    }

    /**
     * Initialize camera with parameters.
     * Initial position will control parallax for sprites
     * @param x Initial position
     * @param y Initial position
     * @param worldWidth Camera's world space width
     * @param worldHeight Camera's world space height
     */
    public void init(int x, int y, int worldWidth, int worldHeight){
        setWorldSize(worldWidth, worldHeight);
        initPosition = new Vector2(x, y);
        getCamera().position.set(x, y, 0);
        getCamera().update();
    }

    /**
     * Set camera position
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        getCamera().position.set(x, y, 0);
        getCamera().update();
    }

    // Accessor
    public Vector2 getInitPosition() {
        return initPosition;
    }
}
