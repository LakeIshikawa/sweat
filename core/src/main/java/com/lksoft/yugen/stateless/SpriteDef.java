package com.lksoft.yugen.stateless;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Lake on 08/06/2016.
 */
public class SpriteDef {
    public TextureAtlas.AtlasRegion region;
    public int originX;
    public int originY;

    /**
     * Create a spriteDef from a region with an origin
     * @param region Region
     * @param originX Origin
     * @param originY Origin
     */
    public SpriteDef(TextureAtlas.AtlasRegion region, int originX, int originY){
        this.region = region;
        this.originX = originX;
        this.originY = region.originalHeight - originY;
    }

    /**
     * Draw this spriteDef on a sprite batch
     * @param batch The ready sprite batch
     * @param flip
     */
    public void draw(SpriteBatch batch, float x, float y, float scaleX, float scaleY, boolean flip, float rotation){
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
                scaleX,
                scaleY,
                rotation);
    }
}
