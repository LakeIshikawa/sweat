package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.lksoft.yugen.stateful.Sprite;

import java.util.HashMap;

/**
 * Created by Lake on 08/06/2016.
 */
public class Animations {
    // Animations
    private HashMap<String, AnimationSequence> animations = new HashMap<>();

    // Frames
    private Frames frames;

    /**
     * Create an animation factory from sprites factory and a .anm file
     * @param frames The frames factory
     * @param anmFile The .anm file
     */
    public Animations(Frames frames, FileHandle anmFile) {
        this.frames = frames;

        // Parse animations
        String text = anmFile.readString();
        String[] lines = text.split("\\n");

        AnimationSequence current = null;
        for( String line : lines ){
            line = line.trim();

            // Anim def
            if( line.startsWith("[Anim ") ){
                // Commit last animation
                if( current != null ){
                    animations.put(current.getName(), current);
                }

                // Create new animation
                String name = line.substring(6, line.indexOf(']'));
                current = new AnimationSequence(name);
            }

            // Loopstart def
            else if( line.startsWith("loopstart") ){
                current.setLoopStartPos(current.getFrames().size);
            }

            // Frame def
            else if( !line.isEmpty() ){
                String[] data = line.split("\\s+");
                if( current != null ){
                    Frame frame = frames.getFrame(data[0]);
                    if( frame != null ) {
                        current.addFrame(new AnimationFrame(frame, Integer.parseInt(data[1])));
                    }
                }
            }
        }
    }

    /**
     * Dispose associated resources
     */
    public void dispose() {
        frames.dispose();
    }

    /**
     * Gets an animation sequence by name
     * @param name The unique name id
     * @return The animation sequence or null if not exist
     */
    public AnimationSequence getAnimationSequence(String name){
        AnimationSequence sequence = animations.get(name);
        if( sequence != null ) return sequence;

        // Try single frame
        Frame frame = frames.getFrame(name);
        if( frame != null ){
            sequence = new AnimationSequence(name);
            sequence.addFrame(new AnimationFrame(frame, -1));
            animations.put(name, sequence);
        }

        return sequence;
    }

    /**
     * @return Frames
     */
    public Frames getFrames() {
        return frames;
    }
}
