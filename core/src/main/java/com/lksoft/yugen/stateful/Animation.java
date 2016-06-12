package com.lksoft.yugen.stateful;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lksoft.yugen.stateless.AnimationFrame;
import com.lksoft.yugen.stateless.AnimationSequence;

/**
 * Created by Lake on 08/06/2016.
 */
public class Animation {

    private AnimationSequence sequence;
    private int ticks;
    private int length;
    private int loopTime;

    /**
     * Create an animation instance from a sequence
     * @param sequence The animation sequence data
     */
    public Animation(AnimationSequence sequence){
        this.sequence = sequence;

        // Calculate length
        length = sequence.getLength();
        // Get loop time
        loopTime = sequence.getLoopTime();
    }

    /**
     * Frame update
     */
    public void update(){
        ticks++;

        // End of animation
        if( length != -1 && ticks >= length ){
            ticks = loopTime;
        }
    }

    /**
     * Render to a batch
     * @param batch The batch to render to
     */
    public void draw(SpriteBatch batch, float x, float y, float scale, boolean flip){
        AnimationFrame frame = sequence.getFrameAt(ticks);
        if( frame != null ) {
            frame.draw(batch, x, y, scale, flip);
        }
    }
}
