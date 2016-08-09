package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

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
    public AnimationPack read(FramePack framePack){
        AnimationPack result = new AnimationPack(framePack);

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
                AnimationFrame animFrame = null;
                if( current != null ){
                    Frame frame = framePack.getFrame(data[0]);
                    if( frame != null ) {
                        current.addFrame(animFrame = new AnimationFrame(frame, Integer.parseInt(data[1])));
                    }
                }

                // Parse rectangles
                if( data.length > 2 && animFrame != null) {
                    String[] rectsSplit = data[2].split("\\|\\|");

                    // Allow all 3 cases
                    if( rectsSplit.length == 2){
                        if( !rectsSplit[0].isEmpty() ) {
                            addRectangles(animFrame.damageCollisions, rectsSplit[0]);
                        }
                        addRectangles(animFrame.hitCollisions, rectsSplit[1]);
                    }
                    else {
                        addRectangles(animFrame.damageCollisions, rectsSplit[0]);
                    }
                }
            }
        }

        // Last one
        if( current != null ) result.addAnimationDef(current);
        return result;
    }

    /**
     * Parse and add rectangles to specified array
     * @param array
     * @param string
     */
    private void addRectangles(Array<Rectangle> array, String string) {
        String[] split = string.split(";");
        for( String s : split){
            Rectangle r = new Rectangle().fromString(s);

            // Intify
            r.x = Math.round(r.x);
            r.y = Math.round(r.y);
            r.width = Math.round(r.width);
            r.height = Math.round(r.height);

            array.add(r);
        }
    }
}
