package com.lksoft.sweat.stateless;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by Lake on 08/06/2016.
 */
public class AnimationFrame {

    // Single region contained in a spriteDef
    public static class Component implements Json.Serializable {
        public SpriteDef spriteDef;
        public int x, y;
        public float scaleX = 1f, scaleY = 1f;
        public float rotation = 0;
        public BlendMode blendMode = BlendMode.NORMAL;

        public Component(){}
        public Component(SpriteDef spriteDef){
            this.spriteDef = spriteDef;
        }

        // Draw
        public void draw(SpriteBatch batch, float dx, float dy, float scale, boolean flip){
            // Change blend mode if necessary
            batch.setBlendFunction(blendMode.src, blendMode.dst);
            float sx = scale * scaleX;
            float sy = scale * scaleY;

            float offx = scale*x;
            float offy = scale*y;
            if( flip ){
                offx *= -1;
            }
            spriteDef.draw(batch, dx + offx, dy + offy, sx, sy, flip, rotation);
        }

        @Override
        public void write(Json json) {
            if( spriteDef != null ) {
                json.writeValue("spriteDef", "S:" + spriteDef.region.name);
            }
            json.writeValue("x", x);
            json.writeValue("y", y);
            json.writeValue("scaleX", scaleX);
            json.writeValue("scaleY", scaleY);
            json.writeValue("rotation", rotation);
            json.writeValue("blendMode", blendMode);
        }

        @Override
        public void read(Json json, JsonValue jsonData) {
            String spriteDefStr = json.readValue("spriteDef", String.class, jsonData);
            if( spriteDefStr != null ) {
                spriteDef = AnimationPack.currentSpritePack.getSpriteDef(spriteDefStr.substring(2));
            }
            x = json.readValue("x", Integer.class, jsonData);
            y = json.readValue("y", Integer.class, jsonData);
            scaleX = json.readValue("scaleX", Float.class, jsonData);
            scaleY = json.readValue("scaleY", Float.class, jsonData);
            rotation = json.readValue("rotation", Float.class, jsonData);
            blendMode = json.readValue("blendMode", BlendMode.class, jsonData);
        }

        // Gets bounding polygon
        public void getBounds(int x, int y, float scale, boolean flip, Polygon out) {
            int w = spriteDef.region.originalWidth;
            int h = spriteDef.region.originalHeight;
            int ox = flip ? w - spriteDef.originX : spriteDef.originX;
            int oy = spriteDef.originY;
            out.setVertices(new float[]{-ox,-oy, -ox+w,-oy, -ox+w,-oy+h, -ox,-oy+h});
            out.setPosition(x + scale*this.x*(flip?-1:1) , y + scale*this.y);
            out.setScale(scaleX, scaleY);
            out.setRotation(rotation);
        }
    }

    // The components
    public Array<Component> components = new Array<>();

    // The length in ticks
    public int lengthTicks;

    // Collisions
    public Array<Rectangle> damageCollisions = new Array<>();
    public Array<Rectangle> hitCollisions = new Array<>();

    // For JSON
    public AnimationFrame(){}

    /**
     * Create an animation spriteDef with a single component
     * @param spriteDef The spriteDef
     * @param lengthTicks The length in ticks
     */
    public AnimationFrame(SpriteDef spriteDef, int lengthTicks){
        this.components.add(new Component(spriteDef));
        this.lengthTicks = lengthTicks;
    }

    /**
     * Draw Wrapper
     */
    public void draw(SpriteBatch batch, float x, float y, float scale, boolean flip) {
        // Draw all components
        for(Component r : components) {
            r.draw(batch, x, y, scale, flip);
        }
    }
}
