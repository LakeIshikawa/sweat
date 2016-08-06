package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Lake on 04/08/2016.
 */
public class AnimationPackReader {

    // File
    private FileHandle anmFile;

    /**
     * Creates a reader for an animation pack
     * @param anmFile
     */
    public AnimationPackReader(FileHandle anmFile){
        this.anmFile = anmFile;
    }

    /**
     * Read the animation pack
     * @return An animation pack
     */
    public AnimationPack read(Frames frames){
        AnimationPack result = new AnimationPack(frames);

        // Parse animations
        String text = anmFile.readString();
        String[] lines = text.split("\\n");

        AnimationDef current = null;
        for( String line : lines ){
            line = line.trim();

            // Anim def
            if( line.startsWith("[Anim ") ){
                // Commit last animation
                if( current != null ){
                    result.addAnimationDef(current);
                }

                // Create new animation
                String name = line.substring(6, line.indexOf(']'));
                current = new AnimationDef(name);
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

        // Last one
        if( current != null ) result.addAnimationDef(current);
        return result;
    }
}
