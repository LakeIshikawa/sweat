package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by Lake on 08/06/2016.
 */
public class AnimationPack implements Json.Serializable {
    // Cache for json parsing (is this the only way?)
    public static SpritePack currentSpritePack;

    // AnimationPack
    private ObjectMap<String, AnimationDef> animations = new ObjectMap<>();

    // SpritePack
    private SpritePack spritePack;

    // For JSON
    public AnimationPack(){}

    /**
     * Create an empty animation pack from sprites factory
     * @param spritePack The spritePack factory
     */
    public AnimationPack(SpritePack spritePack){
        this.spritePack = spritePack;
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
    public AnimationDef getAnimationDef(String name) {
        AnimationDef sequence = animations.get(name);
        if( sequence != null ) return sequence;

        // Try single spriteDef
        SpriteDef spriteDef = spritePack.getSpriteDef(name);
        if( spriteDef != null ){
            sequence = new AnimationDef(name);
            sequence.addFrame(new AnimationFrame(spriteDef, -1));
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
     * @return SpritePack
     */
    public SpritePack getSpritePack() {
        return spritePack;
    }

    /**
     * Dispose associated resources
     */
    public void dispose() {
        spritePack.dispose();
    }

    @Override
    public void write(Json json) {
        // Animations
        json.writeValue("animations", animations, ObjectMap.class, AnimationDef.class);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        spritePack = currentSpritePack;
        animations = json.readValue("animations", ObjectMap.class, AnimationDef.class, jsonData);
    }

    /**
     * Reads an animation pack from file
     * @param anm .anm file
     * @param spritePack A loaded sprite pack
     * @return A loaded animation pack
     */
    public static AnimationPack read(FileHandle anm, SpritePack spritePack) {
        Json json = new Json();
        json.setIgnoreUnknownFields(true);

        currentSpritePack = spritePack;
        AnimationPack res = json.fromJson(AnimationPack.class, anm);
        return res;
    }

    /**
     * Serialize to .anm file
     * @param anm
     */
    public void write(FileHandle anm) {
        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        String jstring = json.prettyPrint(this);
        anm.writeString(jstring, false);
    }
}
