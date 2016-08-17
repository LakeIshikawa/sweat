package com.lksoft.yugen.stateless;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by Stallman on 09/08/2016.
 */
public class HitPack {

    /**
     * A hit definition
     */
    public static class HitDef {
        private ObjectMap<String, Object> values = new ObjectMap<>();
        private String name;

        public HitDef(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

        public Object get(String name){
            return values.get(name);
        }

        public void set(String name, Object value){
            values.put(name, value);
        }
    }

    // Definitions
    private ObjectMap<String, HitDef> defs = new ObjectMap<>();

    /**
     * Gets a hit def
     * @param name
     * @return
     */
    public HitDef get(String name){
        return defs.get(name);
    }

    /**
     * Sets a hit def
     * @param name
     * @param def
     */
    public void set(String name, HitDef def){
        defs.put(name, def);
    }

    /**
     * Merge (overwriting same-name hits)
     * @param hitPack
     */
    public void merge(HitPack hitPack) {
        defs.putAll(hitPack.defs);
    }
}
