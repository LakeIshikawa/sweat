package com.lksoft.yugen.stateless;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Lake on 08/06/2016.
 */
public class SpriteDef {
    private String name;
    private int startX = 0;
    private int startY = 0;
    private float scrollFactorX = 0;
    private float scrollFactorY = 0;
    private int layer = 0;
    private AnimationDef resource;

    // Bounds cache
    private Rectangle bounds = new Rectangle();

    /**
     * Create b1 sprite def
     * @param resource The sprite def resource
     */
    public SpriteDef(AnimationDef resource){
        this.setResource(resource);
        this.name = resource.getName();
    }

    // For deserialization
    SpriteDef(String name){
        this.name = name;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public float getScrollFactorX() {
        return scrollFactorX;
    }

    public void setScrollFactorX(float scrollFactorX) {
        this.scrollFactorX = scrollFactorX;
    }

    public float getScrollFactorY() {
        return scrollFactorY;
    }

    public void setScrollFactorY(float scrollFactorY) {
        this.scrollFactorY = scrollFactorY;
    }

    public AnimationDef getResource() {
        return resource;
    }

    public void setResource(AnimationDef resource) {
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    /**
     * @return The bounds for the first frame of this sprite
     */
    public Rectangle getBounds() {
        Frame frame = resource.getFrameAt(0).frame;
        bounds.setX((getStartX()-frame.originX) + frame.region.offsetX);
        bounds.setY((getStartY()-frame.originY) + frame.region.offsetY);
        bounds.setWidth(frame.region.packedWidth);
        bounds.setHeight(frame.region.packedHeight);
        return bounds;
    }
}
