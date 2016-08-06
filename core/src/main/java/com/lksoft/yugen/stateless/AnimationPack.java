package com.lksoft.yugen.stateless;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Comparator;

/**
 * Created by Lake on 08/06/2016.
 */
public class AnimationPack {
    // AnimationPack
    private ObjectMap<String, AnimationDef> animations = new ObjectMap<>();

    // Frames
    private Frames frames;

    /**
     * Create an empty animation pack from sprites factory
     * @param frames The frames factory
     */
    public AnimationPack(Frames frames){
        this.frames = frames;
    }

    /**
     * Adds an animation def
     * @param animationDef An animation def
     */
    public void addAnimationDef(AnimationDef animationDef) {
        animations.put(animationDef.getName(), animationDef);
    }

    /**
     * Removes an animation def
     * @param name
     */
    public void removeAnimationDef(String name) {
        animations.remove(name);
    }

    /**
     * Gets an animation sequence by name
     * @param name The unique name id
     * @return The animation sequence or null if not exist
     */
    public AnimationDef getAnimationDef(String name){
        AnimationDef sequence = animations.get(name);
        if( sequence != null ) return sequence;

        // Try single frame
        Frame frame = frames.getFrame(name);
        if( frame != null ){
            sequence = new AnimationDef(name);
            sequence.addFrame(new AnimationFrame(frame, -1));
            animations.put(name, sequence);
        }

        return sequence;
    }

    /**
     * @return The animationdef list
     */
    public Array<AnimationDef> getAnimations() {
        Array<AnimationDef> sorted = animations.values().toArray();
        sorted.sort();
        return sorted;
    }

    /**
     * @return Frames
     */
    public Frames getFrames() {
        return frames;
    }

    /**
     * Dispose associated resources
     */
    public void dispose() {
        frames.dispose();
    }
}
