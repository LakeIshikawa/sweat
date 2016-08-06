package com.lksoft.yugen.stateless;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Comparator;

/**
 * Created by Lake on 08/06/2016.
 */
public class FramePack {
    // Atlas
    private TextureAtlas atlas;
    // Frame definitions
    private ObjectMap<String, Frame> frameDefs = new ObjectMap<>();

    /**
     * Create an empty frame pack
     * @param atlas
     */
    public FramePack(TextureAtlas atlas){
        this.atlas = atlas;
    }

    /**
     * Dispose associated resources
     */
    public void dispose() {
        atlas.dispose();
    }

    /**
     * @return The Atlas
     */
    public TextureAtlas getAtlas(){
        return atlas;
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

    /**
     * Adds a frame
     * @param frame
     */
    public void addFrame(Frame frame){
        // No overwriting
        if( !frameDefs.containsKey(frame.region.name) ) {
            frameDefs.put(frame.region.name, frame);
        }
    }

    /**
     * Removes a frame
     * @param name
     */
    public void removeFrame(String name) {
        frameDefs.remove(name);
    }
}
