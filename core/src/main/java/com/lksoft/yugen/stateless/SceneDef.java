package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Stallman on 19/08/2016.
 */
public class SceneDef {

    // An fsm definition
    public static class SceneFsmDef {
        public String scriptPath;
        public String resource;
        public int x,y;
        public float scrollFactorX;
        public float scrollFactorY;
        public int layer;
    }

    // A list of fsm definitions
    public Array<SceneFsmDef> layout = new Array<>();

    // Bounds and camera settings
    public int camera_width = 640;
    public int camera_height = 360;
    public int camera_x = 0;
    public int camera_y = 0;

    public int area_r = 768;
    public int area_l = -768;
    public int area_t = 500;
    public int area_b = -200;

    /**
     * Read from .scn file
     * @param scnFile
     * @return
     */
    public static SceneDef read(FileHandle scnFile){
        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        return json.fromJson(SceneDef.class, scnFile);
    }

    /**
     * Write to file
     * @param scnFile
     */
    public void write(FileHandle scnFile){
        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        json.toJson(this, SceneDef.class, scnFile);
    }
}
