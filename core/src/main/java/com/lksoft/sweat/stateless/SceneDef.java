package com.lksoft.sweat.stateless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.lksoft.sweat.Resources;
import com.lksoft.sweat.stateful.FsmResources;

import java.io.IOException;

/**
 * Created by Stallman on 19/08/2016.
 */
public class SceneDef {

    // An fsm definition
    public static class SceneFsmDef {
        public String scriptPath;
        public String animation;
        public int x,y;
        public float scrollFactorX;
        public float scrollFactorY;
        public int layer;

        // Load Animation Def
        public AnimationDef loadAnimationDef(){
            return loadAnimationPack().getAnimationDef(animation);
        }

        // Load AnimationPack for this def
        public AnimationPack loadAnimationPack(){
            try {
                Class<?> scriptClass = Resources.loadFSMClass(Gdx.files.internal(scriptPath));
                FsmResources res = scriptClass.getAnnotation(FsmResources.class);
                return Resources.loadAnimationPack(res.anm());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Get frame bounds
        public void getBounds(Rectangle bounds) {
            AnimationFrame.Component c = loadAnimationDef().getFrames().first().components.first();
            int dx = x + c.x - c.spriteDef.originX;
            int dy = y + c.y - c.spriteDef.originY;
            int w = c.spriteDef.region.originalWidth;
            int h = c.spriteDef.region.originalHeight;
            bounds.set(dx, dy, w ,h);
        }

        // Get script file name
        public CharSequence getScriptFileName() {
            return Gdx.files.internal(scriptPath).nameWithoutExtension();
        }
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
        String jstring = json.prettyPrint(this);
        scnFile.writeString(jstring, false);
    }
}
