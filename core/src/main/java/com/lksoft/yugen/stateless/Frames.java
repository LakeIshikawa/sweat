package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Comparator;

/**
 * Created by Lake on 08/06/2016.
 */
public class Frames {
    // Atlas
    private TextureAtlas atlas;
    // Frame definitions
    private ObjectMap<String, Frame> frameDefs = new ObjectMap<>();

    /**
     * Create b1 frame factory from an atlas and b1 .frm file
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
     * Obtains b1 frame by id
     * @param id Frame id
     * @return The frame object or null if not present
     */
    public Frame getFrame(String id){
        return frameDefs.get(id);
    }

    /**
     * @return All frames in an array
     */
    public Array<Frame> getFrames(){
        Array<Frame> res = frameDefs.values().toArray();
        res.sort(new Comparator<Frame>() {
            @Override
            public int compare(Frame o1, Frame o2) {
                return o1.region.name.toLowerCase().compareTo(o2.region.name.toLowerCase());
            }
        });
        return res;
    }
}
