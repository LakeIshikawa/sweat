package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.stateless.AnimationFrame;

/**
 * Created by Stallman on 12/08/2016.
 */
public class ComponentControls implements Controls {
    // Editor screen
    private AnimationEditorScreen editorScreen;

    // Shape
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    // Reverse iterator
    private Array<AnimationFrame.Component> componentsReverse = new Array<>();

    // Hovering component
    AnimationFrame.Component hoveringComponent;
    // Dragging
    private Vector2 touchOffset;

    // Bounds buffer
    private Polygon bounds = new Polygon();

    // Create component controls
    public ComponentControls(AnimationEditorScreen editorScreen) {
        this.editorScreen = editorScreen;
    }

    @Override
    public void render() {
        // Render selected component frame
        shapeRenderer.setProjectionMatrix(editorScreen.getAnimationFrameRenderer().getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Hovering component
        if( hoveringComponent != null ){
            shapeRenderer.setColor( Color.GREEN);
            hoveringComponent.getBounds(0, 0, 1.0f, false, bounds);
            shapeRenderer.polygon(bounds.getTransformedVertices());
        }

        // Selected component
        AnimationFrame.Component selected = editorScreen.getAnimationFrameComponentWindow().getSelectedComponent();
        if( selected != null ) {
            shapeRenderer.setColor( Color.ORANGE);
            selected.getBounds(0, 0, 1.0f, false, bounds);
            shapeRenderer.polygon(bounds.getTransformedVertices());
        }

        shapeRenderer.end();
    }

    @Override
    public void copy() {

    }

    @Override
    public void paste() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
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

        // Select component
        componentsReverse.clear();
        AnimationFrame frame = editorScreen.getAnimationFrameComponentWindow().getAnimationFrame();
        if( frame != null ) {
            componentsReverse.addAll(frame.components);
            componentsReverse.reverse();
        }

        Vector2 touchPoint = editorScreen.getAnimationFrameRenderer().getTouch(screenX, screenY);
        for(AnimationFrame.Component c : componentsReverse){
            c.getBounds(0, 0, 1, false, bounds);
            if( bounds.contains(touchPoint.x, touchPoint.y) ) {
                editorScreen.selectComponent(c);
                touchOffset = new Vector2(touchPoint).sub(c.x, c.y);
                return true;
            }
        }

        // Click on nothing
        editorScreen.unfocusStage();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchOffset = null;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if( editorScreen.getAnimationFrameRenderer() == null ) return false;
        if( touchOffset == null ) return false;

        Vector2 touch = editorScreen.getAnimationFrameRenderer().getTouch(screenX, screenY);
        AnimationFrame.Component selected = editorScreen.getAnimationFrameComponentWindow().getSelectedComponent();
        if( selected != null ) {
            selected.x = (int) (touch.x - touchOffset.x);
            selected.y = (int) (touch.y - touchOffset.y);
            editorScreen.selectComponent(selected);
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        hoveringComponent = null;
        if( editorScreen.getAnimationFrameRenderer() == null ) return false;

        Vector2 point = editorScreen.getAnimationFrameRenderer().getTouch(screenX, screenY);
        for(AnimationFrame.Component c : componentsReverse){
            c.getBounds(0, 0, 1, false, bounds);
            if( bounds.contains(point.x, point.y) ) {
                hoveringComponent = c;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
