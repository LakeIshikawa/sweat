package com.lksoft.sweat.stateless;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Comparator;

/**
 * Created by Lake on 08/06/2016.
 */
public class SpritePack {
    // Atlas
    private TextureAtlas atlas;
    // SpriteDef definitions
    private ObjectMap<String, SpriteDef> frameDefs = new ObjectMap<>();

    /**
     * Create an empty spriteDef pack
     * @param atlas
     */
    public SpritePack(TextureAtlas atlas){
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
     * Obtains b1 spriteDef by id
     * @param id SpriteDef id
     * @return The spriteDef object or null if not present
     */
    public SpriteDef getSpriteDef(String id){
        return frameDefs.get(id);
    }

    /**
     * @return All frames in an array
     */
    public Array<SpriteDef> getSpriteDefs(){
        Array<SpriteDef> res = frameDefs.values().toArray();
        res.sort(new Comparator<SpriteDef>() {
            @Override
            public int compare(SpriteDef o1, SpriteDef o2) {
                return o1.region.name.toLowerCase().compareTo(o2.region.name.toLowerCase());
            }
        });
        return res;
    }

    /**
     * Adds a spriteDef
     * @param spriteDef
     */
    public void addSpriteDef(SpriteDef spriteDef){
        // No overwriting
        if( !frameDefs.containsKey(spriteDef.region.name) ) {
            frameDefs.put(spriteDef.region.name, spriteDef);
        }
    }

    /**
     * Removes a spriteDef
     * @param name
     */
    public void removeSpriteDef(String name) {
        frameDefs.remove(name);
    }
}
