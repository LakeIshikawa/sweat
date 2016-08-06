package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Lake on 04/08/2016.
 */
public class FramePackReader {

    // File
    private FileHandle frmFile;

    /**
     * Creates a reader for a frame pack
     * @param frmFile
     */
    public FramePackReader(FileHandle frmFile){
        this.frmFile = frmFile;
    }

    /**
     * Read the frame pack
     * @return A frame pack
     */
    public FramePack read(TextureAtlas atlas){
        FramePack result = new FramePack(atlas);

        // Parse the spr file and create sprite definitions
        String text = frmFile.readString();

        String[] lines = text.split("\\n");
        for( String line : lines ){
            line = line.trim();
            if( line.isEmpty() ) continue;

            String[] split = line.split("\\s+");

            // Find region
            TextureAtlas.AtlasRegion region = atlas.findRegion(split[0]);
            if( region != null ){
                Frame frame = new Frame(region, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                result.addFrame(frame);

                // Parse rectangles
                if( split.length > 3 ) {
                    String[] rectsSplit = split[3].split("\\|\\|");

                    // Allow all 3 cases
                    if( rectsSplit.length == 2){
                        if( !rectsSplit[0].isEmpty() ) {
                            addRectangles(frame.damageCollisions, rectsSplit[0]);
                        }
                        addRectangles(frame.hitCollisions, rectsSplit[1]);
                    }
                    else {
                        addRectangles(frame.damageCollisions, rectsSplit[0]);
                    }
                }
            }
        }
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
            array.add(new Rectangle().fromString(s));
        }
    }
}
