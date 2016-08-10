package com.lksoft.yugen.stateful;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lksoft.yugen.stateless.AnimationDef;
import com.lksoft.yugen.stateless.AnimationFrame;

/**
 * Created by Lake on 08/06/2016.
 */
public class Animation {

    private AnimationDef sequence;
    private int ticks;
    private int length;
    private int loopTime;
    private int cycles;

    /**
     * Create an animation instance from b1 sequence
     * @param sequence The animation sequence data
     */
    public Animation(AnimationDef sequence){
        this.sequence = sequence;

        // Calculate length
        length = sequence.getLength();
        // Get loop time
        loopTime = sequence.getLoopTime();

        // Always needs at least one update call to work
        ticks = -1;
    }

    /**
     * Frame update
     */
    public void update(){
        ticks = getTicks() + 1;

        // End of animation
        if( getLength() != -1 ){
            if( getTicks() >= getLength() ) {
                ticks = getLoopTime();
            }
            // If last frame, update cycle
            if( getTicks() >= getLength()-1 ) {
                cycles++;
            }
        }
    }

    /**
     * Render to b1 batch
     * @param batch The batch to render to
     */
    public void draw(SpriteBatch batch, float x, float y, float scale, boolean flip){
        AnimationFrame frame = getCurrentFrame();
        if( frame != null ) {
            frame.draw(batch, x, y, scale, flip);
        }
    }

    public AnimationDef getAnimationDef() {
        return sequence;
    }
    public AnimationFrame getCurrentFrame() { return getAnimationDef().getFrameAt(getTicks()); }
    public int getTicks() {
        return ticks;
    }
    public int getLength() {
        return length;
    }
    public int getLoopTime() {
        return loopTime;
    }

    public int getCycles() {
        return cycles;
    }
}
