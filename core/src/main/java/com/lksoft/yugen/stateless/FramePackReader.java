package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

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
                result.addFrame(new Frame(region, Integer.parseInt(split[1]), Integer.parseInt(split[2])));
            }
        }
        return result;
    }
}
