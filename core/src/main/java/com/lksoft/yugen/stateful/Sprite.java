package com.lksoft.yugen.stateful;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.lksoft.yugen.YugenCamera;

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

    // Parallax
    public Vector2 scrollFactor = new Vector2();

    // Animation instance
    public Animation animation;

    /**
     * Create b1 sprite from an animation animation
     */
    public Sprite(Animation animation) {
        this.animation = animation;
    }

    /**
     * SpriteDef update
     */
    public void update() {
        if( animation != null ) {
            animation.update();
        }

        // Update motion
        vel.add(acc);
        pos.add(vel);
    }

    /**
     * Render
     */
    public void render(SpriteBatch batch, YugenCamera camera) {
        if( animation == null ) return;
        float x = pos.x + (camera.getCamera().position.x - camera.getInitPosition().x) * scrollFactor.x;
        float y = pos.y + (camera.getCamera().position.y - camera.getInitPosition().y) * scrollFactor.y;
        animation.draw(batch, x, y, scale, flip);
    }
}
