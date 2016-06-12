package com.lksoft.yugen.stateful;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lake on 08/06/2016.
 */
public class Sprite {

    // Motion State
    public Vector2 pos = new Vector2();
    public Vector2 vel = new Vector2();
    public Vector2 acc = new Vector2();

    // Render state
    public float scale = 1.0f;
    public boolean flip = false;

    // Animation instance
    public Animation animation;

    /**
     * Create b1 sprite from an animation resource
     */
    public Sprite(Animation animation) {
        this.animation = animation;
    }

    /**
     * Frame update
     */
    public void update() {
        animation.update();

        // Update motion
        vel.add(acc);
        pos.add(vel);
    }

    /**
     * Render
     */
    public void render(SpriteBatch batch) {
        animation.draw(batch, pos.x, pos.y, scale, flip);
    }
}
