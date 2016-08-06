package com.lksoft.yugen.stateless;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Lake on 08/06/2016.
 */
public class Frame {
    public TextureAtlas.AtlasRegion region;
    public int originX;
    public int originY;
    public Array<Rectangle> damageCollisions = new Array<>();
    public Array<Rectangle> hitCollisions = new Array<>();

    /**
     * Create a frame from a region with an origin
     * @param region Region
     * @param originX Origin
     * @param originY Origin
     */
    public Frame(TextureAtlas.AtlasRegion region, int originX, int originY){
        this.region = region;
        this.originX = originX;
        this.originY = region.originalHeight - originY;
    }

    /**
     * Draw this frame on a sprite batch
     * @param batch The ready sprite batch
     * @param flip
     */
    public void draw(SpriteBatch batch, float x, float y, float scale, boolean flip){
        if( flip != region.isFlipX() ){
            region.flip(true, false);
            originX = region.originalWidth - originX;
        }

        batch.draw(
                region,
                (x-originX) + region.offsetX,
                (y-originY) + region.offsetY,
                originX-region.offsetX,
                originY-region.offsetY,
                region.packedWidth,
                region.packedHeight,
                scale,
                scale,
                0);
    }
}
