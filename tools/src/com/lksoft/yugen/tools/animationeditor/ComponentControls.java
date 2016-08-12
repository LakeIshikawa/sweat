package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
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

    // Dragging
    private Vector2 touchPoint;
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

        AnimationFrame.Component selected = editorScreen.getAnimationFrameComponentWindow().getSelectedComponent();
        if( selected != null ) {
            shapeRenderer.setColor(Color.ORANGE);
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

        touchPoint = new Vector2(editorScreen.getAnimationFrameRenderer().getTouch(screenX, screenY));

        // Select component
        componentsReverse.clear();
        componentsReverse.addAll(editorScreen.getAnimationFrameComponentWindow().getAnimationFrame().components);
        componentsReverse.reverse();
        for(AnimationFrame.Component c : componentsReverse){
            c.getBounds(0, 0, 1, false, bounds);
            if( bounds.contains(touchPoint.x, touchPoint.y) ) {
                editorScreen.selectComponent(c);
                break;
            }
        }

        AnimationFrame.Component selected = editorScreen.getAnimationFrameComponentWindow().getSelectedComponent();
        if( selected != null ) {
            touchOffset = new Vector2(touchPoint).sub(selected.x, selected.y);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
