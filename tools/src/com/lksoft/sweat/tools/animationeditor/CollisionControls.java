package com.lksoft.sweat.tools.animationeditor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.lksoft.sweat.stateless.AnimationFrame;

/**
 * Created by Stallman on 12/08/2016.
 */
public class CollisionControls implements Controls {
    // Editor screen
    private AnimationEditorScreen editorScreen;

    // Shape
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    // Dragging
    private Vector2 touchPoint;
    private Rectangle draggingRect;
    private Rectangle hoveringRect;
    private Rectangle hoveringCornerRect = new Rectangle();
    private boolean hoveringCorner = false;
    private Vector2 touchOffset;

    // Palette
    private Array<Rectangle> paletteDamage;
    private Array<Rectangle> paletteHit;

    // Create collisions input processor
    public CollisionControls(AnimationEditorScreen editorScreen){
        this.editorScreen = editorScreen;

        // Autoshape
        shapeRenderer.setAutoShapeType(true);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.FORWARD_DEL:
                if( hoveringRect != null ){
                    editorScreen.removeCollision(hoveringRect);
                    hoveringRect = null;
                }
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if( editorScreen.getAnimationFrameRenderer() == null ) return false;

        touchPoint = new Vector2(editorScreen.getAnimationFrameRenderer().getTouch(screenX, screenY));

        // Start new rectangle
        if( hoveringRect == null ) {
            draggingRect = new Rectangle();
        }
        // Move or resize selected!
        else {
            touchOffset = new Vector2(touchPoint).sub(hoveringRect.x, hoveringRect.y);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if( editorScreen.getAnimationFrameRenderer() == null ) return false;

        // Add rectangle
        if( touchPoint != null && hoveringRect == null ){
            Vector2 touch = editorScreen.getAnimationFrameRenderer().getTouch(screenX, screenY);

            float x = Math.min(touchPoint.x, touch.x);
            float y = Math.min(touchPoint.y, touch.y);
            float w = Math.abs(touchPoint.x - touch.x);
            float h = Math.abs(touchPoint.y - touch.y);
            if( w<=1 || h<=1 ) return false;
            editorScreen.addCollision(new Rectangle(x, y, w, h), button == Input.Buttons.LEFT);
        }

        touchPoint = null;
        draggingRect = null;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if( editorScreen.getAnimationFrameRenderer() == null ) return false;

        if( touchPoint != null ) {
            Vector2 touch = editorScreen.getAnimationFrameRenderer().getTouch(screenX, screenY);

            // New rectangle
            if( draggingRect != null ) {
                float x = Math.min(touchPoint.x, touch.x);
                float y = Math.min(touchPoint.y, touch.y);
                float w = Math.abs(touchPoint.x - touch.x);
                float h = Math.abs(touchPoint.y - touch.y);
                draggingRect.set(x, y, w, h);
            }

            // Dragging selection
            else if( hoveringRect != null ){
                // Resize
                if( hoveringCorner ){
                    hoveringRect.setSize(touch.x - hoveringRect.x, touch.y - hoveringRect.y);
                }
                // Move
                else {
                    hoveringRect.setPosition(touch.x - touchOffset.x, touch.y - touchOffset.y);
                }

                // Update corner
                hoveringCornerRect.set(
                        hoveringRect.x + hoveringRect.width - 20,
                        hoveringRect.y + hoveringRect.height - 20,
                        20, 20);
            }
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if( editorScreen.getAnimationFrameRenderer() == null ) return false;

        // Check hovering
        hoveringRect = null;
        Vector2 touch = editorScreen.getAnimationFrameRenderer().getTouch(screenX, screenY);
        AnimationFrame frame = editorScreen.getAnimationFrameWindow().getSelectedFrame();
        if( frame == null ) return false;

        for( Rectangle r : frame.damageCollisions ){
            if( r.contains(touch) ){
                hoveringRect = r;
            }
        }
        for( Rectangle r : frame.hitCollisions ){
            if( r.contains(touch) ){
                hoveringRect = r;
            }
        }

        if( hoveringRect != null ){
            hoveringCornerRect.set(
                    hoveringRect.x + hoveringRect.width - 20,
                    hoveringRect.y + hoveringRect.height - 20,
                    20, 20);
        }

        hoveringCorner = hoveringCornerRect.contains(touch);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    @Override
    public void render() {
        // Current rectangle
        shapeRenderer.setProjectionMatrix(editorScreen.getAnimationFrameRenderer().getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        if( draggingRect != null ) {
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(draggingRect.x, draggingRect.y, draggingRect.width, draggingRect.height);
        }

        // Hovering
        if( hoveringRect != null ){
            shapeRenderer.setColor(hoveringCorner ? Color.PURPLE : Color.CYAN);
            shapeRenderer.rect(hoveringRect.x, hoveringRect.y, hoveringRect.width, hoveringRect.height);

            // Resize corner
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.PURPLE);
            shapeRenderer.rect(hoveringCornerRect.x, hoveringCornerRect.y, hoveringCornerRect.width, hoveringCornerRect.height);

        }
        shapeRenderer.end();
    }

    @Override
    public void copy() {
        paletteDamage = new Array<>();
        paletteHit = new Array<>();

        for( Rectangle r : editorScreen.getAnimationFrameWindow().getSelectedFrame().damageCollisions ){
            paletteDamage.add(new Rectangle(r));
        }
        for( Rectangle r : editorScreen.getAnimationFrameWindow().getSelectedFrame().hitCollisions ){
            paletteHit.add(new Rectangle(r));
        }
    }

    @Override
    public void paste() {
        for( Rectangle r : paletteDamage ){
            editorScreen.getAnimationFrameWindow().getSelectedFrame().damageCollisions.add(new Rectangle(r));
        }
        for( Rectangle r : paletteHit ){
            editorScreen.getAnimationFrameWindow().getSelectedFrame().hitCollisions.add(new Rectangle(r));
        }
    }
}
