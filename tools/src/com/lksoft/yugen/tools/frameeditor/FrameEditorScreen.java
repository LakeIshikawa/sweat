package com.lksoft.yugen.tools.frameeditor;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserListener;
import com.kotcrab.vis.ui.widget.file.FileTypeFilter;
import com.lksoft.yugen.stateless.Frame;
import com.lksoft.yugen.stateless.FramePack;
import com.lksoft.yugen.stateless.FramePackReader;

import java.io.File;
import java.io.IOException;

/**
 * Created by Lake on 07/06/2016.
 */
public class FrameEditorScreen implements Screen, InputProcessor {

    // Chooser Path
    private File path;

    // UI
    private Stage stage;

    // Shape
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    // GUI components
    private FrameEditorMenuBar menuBar;
    private FramePackWindow framePackWindow;

    // Current animations
    private FrameRenderer frameRenderer;
    private FileHandle frmFile;

    // Screen size info
    private int lastW, lastH;

    // Dragging
    private Vector2 touchPoint;
    private Rectangle draggingRect;
    private Rectangle hoveringRect;
    private Rectangle hoveringCornerRect = new Rectangle();
    private boolean hoveringCorner = false;
    private Vector2 touchOffset;

    /**
     * Create frame editor
     * @param path
     */
    public FrameEditorScreen(File path){
        this.path = path;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, this));

        // -- Create GUI components
        float w = stage.getViewport().getWorldWidth();
        float h = stage.getViewport().getWorldHeight();

        // Menus!
        menuBar = new FrameEditorMenuBar(this);

        // Animation pack window
        framePackWindow = new FramePackWindow(this);
        framePackWindow.setSize(300, h - 30);
        framePackWindow.setPosition((w-5)- framePackWindow.getWidth(), 5);

        // Root with menu bar
        VisTable root = new VisTable(true);
        root.add(menuBar.getTable()).fillX().expandX().row();
        root.add().expand().fill();
        root.setFillParent(true);

        stage.addActor(root);
        stage.addActor(framePackWindow);

        // Autoshape
        shapeRenderer.setAutoShapeType(true);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        if( frameRenderer != null ) frameRenderer.resize(width, height);
        lastW = width;
        lastH = height;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render stage layout
        if( frameRenderer != null ) {
            frameRenderer.render();

            // Current rectangle
            shapeRenderer.setProjectionMatrix(frameRenderer.getViewport().getCamera().combined);
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

        // Render ui
        stage.act();
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.FORWARD_DEL:
                if( hoveringRect != null ){
                    removeCollision(hoveringRect);
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
        if( frameRenderer == null ) return false;

        touchPoint = new Vector2(frameRenderer.getTouch(screenX, screenY));

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
        if( frameRenderer == null ) return false;

        // Add rectangle
        if( touchPoint != null && hoveringRect == null ){
            Vector2 touch = frameRenderer.getTouch(screenX, screenY);

            float x = Math.min(touchPoint.x, touch.x);
            float y = Math.min(touchPoint.y, touch.y);
            float w = Math.abs(touchPoint.x - touch.x);
            float h = Math.abs(touchPoint.y - touch.y);
            addCollision(new Rectangle(x, y, w, h), button == Input.Buttons.LEFT);
        }

        touchPoint = null;
        draggingRect = null;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if( frameRenderer == null ) return false;

        if( touchPoint != null ) {
            Vector2 touch = frameRenderer.getTouch(screenX, screenY);

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
        if( frameRenderer == null ) return false;

        // Check hovering
        hoveringRect = null;
        Vector2 touch = frameRenderer.getTouch(screenX, screenY);
        Frame frame = framePackWindow.getSelected();
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

    // Set the current frame pack
    private void setFramePack(FramePack pack, FileHandle frmFile) {
        this.frmFile = frmFile;

        // Create renderer
        frameRenderer = new FrameRenderer();
        frameRenderer.resize(lastW, lastH);

        // Update GUI
        framePackWindow.setFramePack(pack);

        // Select first animation def
        selectFrame(pack.getFrames().first());
    }

    // Selects the specified frame
    void selectFrame(Frame frame) {
        // Set window selection
        framePackWindow.setSelected(frame);
        // Renderer
        frameRenderer.setFrame(frame == null ? null : frame);
    }

    // Remove frame
    void removeFrame(Frame frame) {
        framePackWindow.removeFrame(frame);
        selectFrame(null);
    }

    // Choose region and add new frame
    public void addFrame() {
        if( framePackWindow.getFramePack() == null ) return;

        RegionPicker picker = new RegionPicker(framePackWindow.getFramePack().getAtlas(),
                new RegionPicker.PickListener() {
                    @Override
                    public void onRegionPicked(TextureAtlas.AtlasRegion region) {
                        Frame newFrame = new Frame(region, region.originalWidth/2, region.originalHeight);
                        framePackWindow.addFrame(newFrame);
                        selectFrame(newFrame);
                    }

                    @Override
                    public void onCancel() {}
                });
        picker.show(stage);
    }

    // Add collision rectangle to current frame
    private void addCollision(Rectangle rectangle, boolean damage) {
        if( damage ) framePackWindow.getSelected().damageCollisions.add(rectangle);
        else framePackWindow.getSelected().hitCollisions.add(rectangle);
    }

    // Remove collision rectangle from current frame
    private void removeCollision(Rectangle rectangle) {
        framePackWindow.getSelected().damageCollisions.removeValue(rectangle, true);
        framePackWindow.getSelected().hitCollisions.removeValue(rectangle, true);
    }

    // Create new pack
    void newFramePack() {
        // Show file chooser
        final FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
        chooser.setSelectionMode(FileChooser.SelectionMode.FILES);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDirectory(path);
        FileTypeFilter filter = new FileTypeFilter(false);
        filter.addRule("TextureAtlas file", "atlas");
        chooser.setFileTypeFilter(filter);
        chooser.setListener(new FileChooserListener() {
            @Override
            public void selected(Array<FileHandle> files) {
                // Find the atlas
                FileHandle atlas = files.first();
                FileHandle frm = new FileHandle(atlas.pathWithoutExtension()+".frm");

                // Load stuff
                TextureAtlas tAtlas = new TextureAtlas(atlas);

                // Create new frame pack
                setFramePack(new FramePack(tAtlas), frm);
            }

            @Override
            public void canceled() {
            }
        });

        stage.addActor(chooser);
    }

    // Open stage
    void open() {
        final FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
        chooser.setSelectionMode(FileChooser.SelectionMode.FILES);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDirectory(path);
        FileTypeFilter filter = new FileTypeFilter(false);
        filter.addRule("Frame pack files", "frm");
        chooser.setFileTypeFilter(filter);
        chooser.setListener(new FileChooserListener() {

            @Override
            public void selected(Array<FileHandle> files) {
                // Read frm and atlas
                FileHandle atlasHandle = new FileHandle(files.first().pathWithoutExtension() + ".atlas");
                TextureAtlas atlas = new TextureAtlas(atlasHandle);

                FramePack framePack = new FramePackReader(files.first()).read(atlas);
                setFramePack(framePack, files.first());
            }

            @Override
            public void canceled() {
            }
        });

        stage.addActor(chooser);
    }

    // Save pack
    void save() {
        if( framePackWindow.getFramePack() == null ) return;

        FramePackWriter writer = new FramePackWriter(frmFile.file());
        try {
            writer.write(framePackWindow.getFramePack());
            Dialogs.showOKDialog(stage, "Success", "Frame Pack saved.");
        } catch (IOException e) {
            e.printStackTrace();
            Dialogs.showErrorDialog(stage, "Could not write file.  Check permissions?");
        }
    }
}
