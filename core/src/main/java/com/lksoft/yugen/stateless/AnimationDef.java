package com.lksoft.yugen.stateless;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Lake on 08/06/2016.
 */
public class AnimationDef {

    private String name;
    private Array<AnimationFrame> frames = new Array<>();
    private int loopStartPos = 0;

    /**
     * Create an animation
     * @param name Script name of the animation
     */
    public AnimationDef(String name){
        this.name = name;
    }

    public Array<AnimationFrame> getFrames() {
        return frames;
    }

    public void addFrame(AnimationFrame frame){
        frames.add(frame);
    }

    public int getLoopStartPos() {
        return loopStartPos;
    }

    public String getName() {
        return name;
    }

    public void setLoopStartPos(int loopStartPos) {
        this.loopStartPos = loopStartPos;
    }

    /**
     * Calculates the total length of the animation sequence
     * @return Length in ticks
     */
    public int getLength() {
        int length = 0;
        for( AnimationFrame frame : getFrames() ){
            // Infinite case
            if( frame.lengthTicks == -1 ) return -1;
            length += frame.lengthTicks;
        }
        return length;
    }

    /**
     * Calculates the loop time in ticks
     * @return Loop time in ticks
     */
    public int getLoopTime() {
        int time = 0;
        for( int i=0; i<loopStartPos; i++ ){
            // Infinite time case
            if( frames.get(i).lengthTicks == -1 ) return -1;

            time += frames.get(i).lengthTicks;
        }

        return time;
    }

    /**
     * Gets the frame for the specified number of ticks
     * @param ticks Time in ticks
     * @return The frame for that time, or null if not existing
     */
    public AnimationFrame getFrameAt(int ticks) {
        int acc = 0;
        for( AnimationFrame frame : frames ){
            acc += frame.lengthTicks;

            // Exit case
            if( ticks < acc ) return frame;

            // Infinite time case
            if( frame.lengthTicks == -1 ) return frame;
        }

        return null;
    }
}
