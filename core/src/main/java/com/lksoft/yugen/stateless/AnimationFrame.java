package com.lksoft.yugen.stateless;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Lake on 08/06/2016.
 */
public class AnimationFrame {

    // The frame
    public Frame frame;

    // The length in ticks
    public int lengthTicks;

    // Collisions
    public Array<Rectangle> damageCollisions = new Array<>();
    public Array<Rectangle> hitCollisions = new Array<>();

    /**
     * Create an animation frame
     * @param frame The frame
     * @param lengthTicks The length in ticks
     */
    public AnimationFrame(Frame frame, int lengthTicks){
        this.frame = frame;
        this.lengthTicks = lengthTicks;
    }

    /**
     * Draw Wrapper
     */
    public void draw(SpriteBatch batch, float x, float y, float scale, boolean flip) {
        frame.draw(batch, x, y, scale, flip);
    }
}
