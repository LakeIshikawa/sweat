package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

/**
 * Created by Lake on 08/06/2016.
 */
public class Frames {
    // Atlas
    private TextureAtlas atlas;
    // Frame definitions
    private HashMap<String, Frame> frameDefs = new HashMap<>();

    /**
     * Create a frame factory from an atlas and a .frm file
     * @param atlas The texture atlas
     * @param frmFile The .frm file
     */
    public Frames(TextureAtlas atlas, FileHandle frmFile) {
        this.atlas = atlas;

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
                frameDefs.put(split[0],
                        new Frame(region, Integer.parseInt(split[1]), Integer.parseInt(split[2])));
            }
        }
    }

    /**
     * Dispose associated resources
     */
    public void dispose() {
        atlas.dispose();
    }

    /**
     * Obtains a frame by id
     * @param id Frame id
     * @return The frame object or null if not present
     */
    public Frame getFrame(String id){
        return frameDefs.get(id);
    }
}
