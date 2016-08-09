package com.lksoft.yugen.tools.animationeditor;

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
import com.lksoft.yugen.stateless.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by Lake on 07/06/2016.
 */
public class AnimationEditorScreen implements Screen, InputProcessor {

    // Chooser Path
    private File path;

    // UI
    private Stage stage;

    // Shape
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    // GUI components
    private AnimationEditorMenuBar menuBar;
    private AnimationPackWindow animationPackWindow;
    private AnimationFrameWindow animationFrameWindow;
    private PlaybackWindow playbackWindow;
    private InspectorWindow inspectorWindow;

    // Current animations
    private AnimationFrameRenderer animationFrameRenderer;
    private FileHandle anmFile;

    // Screen size info
    private int lastW, lastH;

    // Playback
    boolean playing = false;
    int ticks = 0;

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

    /**
     * Create b1 stage editor
     * @param path
     */
    public AnimationEditorScreen(File path){
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
        menuBar = new AnimationEditorMenuBar(this);

        // Animation pack window
        animationPackWindow = new AnimationPackWindow(this);
        animationPackWindow.setSize(300, h/2.15f);
        animationPackWindow.setPosition((w-5)-animationPackWindow.getWidth(), (h-30)-animationPackWindow.getHeight());

        // Animation Frame window
        animationFrameWindow = new AnimationFrameWindow(this);
        animationFrameWindow.setSize(300, h/2.15f);
        animationFrameWindow.setPosition((w-5)-animationFrameWindow.getWidth(), 5);

        // Playback window
        playbackWindow = new PlaybackWindow(this);
        playbackWindow.setSize(200, 60);
        playbackWindow.setPosition(w/2-playbackWindow.getWidth(), 5);

        // Inspector window
        inspectorWindow = new InspectorWindow(this);
        inspectorWindow.setSize(200, 100);
        inspectorWindow.setPosition(5, 5);

        // Tools window
        ToolsWindow toolsWindow = new ToolsWindow(this);
        toolsWindow.setSize(150, 60);
        toolsWindow.setPosition(5, (h-30) - toolsWindow.getHeight());

        // Root with menu bar
        VisTable root = new VisTable(true);
        root.add(menuBar.getTable()).fillX().expandX().row();
        root.add().expand().fill();
        root.setFillParent(true);

        stage.addActor(root);
        stage.addActor(animationPackWindow);
        stage.addActor(animationFrameWindow);
        stage.addActor(playbackWindow);
        stage.addActor(inspectorWindow);
        stage.addActor(toolsWindow);

        // Autoshape
        shapeRenderer.setAutoShapeType(true);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        if( animationFrameRenderer != null ) animationFrameRenderer.resize(width, height);
        lastW = width;
        lastH = height;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render stage layout
        if( animationFrameRenderer != null ) {
            animationFrameRenderer.render();

            // Current rectangle
            shapeRenderer.setProjectionMatrix(animationFrameRenderer.getViewport().getCamera().combined);
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

        // Playback
        if( playing ) {
            AnimationFrame frame = animationFrameWindow.getSelectedFrame();
            if (frame != null && ticks >= frame.lengthTicks-1) {
                // Go to next
                selectFrame(animationFrameWindow.getNextFrame());
                ticks = 0;
            }
            else {
                ticks++;
            }
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
        if( animationFrameRenderer == null ) return false;

        touchPoint = new Vector2(animationFrameRenderer.getTouch(screenX, screenY));

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
        if( animationFrameRenderer == null ) return false;

        // Add rectangle
        if( touchPoint != null && hoveringRect == null ){
            Vector2 touch = animationFrameRenderer.getTouch(screenX, screenY);

            float x = Math.min(touchPoint.x, touch.x);
            float y = Math.min(touchPoint.y, touch.y);
            float w = Math.abs(touchPoint.x - touch.x);
            float h = Math.abs(touchPoint.y - touch.y);
            if( w<=1 || h<=1 ) return false;
            addCollision(new Rectangle(x, y, w, h), button == Input.Buttons.LEFT);
        }

        touchPoint = null;
        draggingRect = null;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if( animationFrameRenderer == null ) return false;

        if( touchPoint != null ) {
            Vector2 touch = animationFrameRenderer.getTouch(screenX, screenY);

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
        if( animationFrameRenderer == null ) return false;

        // Check hovering
        hoveringRect = null;
        Vector2 touch = animationFrameRenderer.getTouch(screenX, screenY);
        AnimationFrame frame = animationFrameWindow.getSelectedFrame();
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


    // Add collision rectangle to current frame
    private void addCollision(Rectangle rectangle, boolean damage) {
        if( damage ) animationFrameWindow.getSelectedFrame().damageCollisions.add(rectangle);
        else animationFrameWindow.getSelectedFrame().hitCollisions.add(rectangle);
    }

    // Remove collision rectangle from current frame
    private void removeCollision(Rectangle rectangle) {
        animationFrameWindow.getSelectedFrame().damageCollisions.removeValue(rectangle, true);
        animationFrameWindow.getSelectedFrame().hitCollisions.removeValue(rectangle, true);
    }

    // Copy rectangles
    public void copy() {
        paletteDamage = new Array<>();
        paletteHit = new Array<>();

        for( Rectangle r : animationFrameWindow.getSelectedFrame().damageCollisions ){
            paletteDamage.add(new Rectangle(r));
        }
        for( Rectangle r : animationFrameWindow.getSelectedFrame().hitCollisions ){
            paletteHit.add(new Rectangle(r));
        }
    }

    // PAste rectangles
    public void paste() {
        for( Rectangle r : paletteDamage ){
            animationFrameWindow.getSelectedFrame().damageCollisions.add(new Rectangle(r));
        }
        for( Rectangle r : paletteHit ){
            animationFrameWindow.getSelectedFrame().hitCollisions.add(new Rectangle(r));
        }
    }

    // Set the current animdef
    private void setAnimationPack(AnimationPack animationPack, FileHandle anmFile) {
        this.anmFile = anmFile;

        // Create renderer
        animationFrameRenderer = new AnimationFrameRenderer();
        animationFrameRenderer.resize(lastW, lastH);

        // Update GUI
        animationPackWindow.setAnimationPack(animationPack);

        // Select first animation def
        selectAnimationDef(animationPack.getAnimations().first());
    }

    // Selects the specified animation def
    void selectAnimationDef(AnimationDef def) {
        // Set window selection
        animationPackWindow.setSelected(def);

        // Populate frames window
        animationFrameWindow.setAnimationDef(def);

        // Select first frame
        if( def == null || def.getFrames().size == 0 ) selectFrame(null);
        else selectFrame(def.getFrames().first());
    }

    // Selects the specified frame
    void selectFrame(AnimationFrame frame){
        animationFrameWindow.setSelected(frame);
        animationFrameRenderer.setAnimationFrame(frame == null ? null : frame);
        inspectorWindow.setFrame(frame);
    }

    // Play the animation
    public void play() {
        playing = true;
        ticks = 0;
    }
    // Stop the animation
    public void stop() {
        playing = false;
    }

    // Add animation
    void addAnimationDef(String name) {
        if( animationPackWindow.getAnimationPack() == null ) return;

        AnimationDef newDef = new AnimationDef(name);
        animationPackWindow.addAnimationDef(newDef);
        selectAnimationDef(newDef);
    }

    // Remove animation
    void removeAnimationDef(AnimationDef def) {
        animationPackWindow.removeAnimationDef(def);
        selectAnimationDef(null);
    }

    // Remove frame
    public void removeFrame(AnimationFrame frame) {
        animationFrameWindow.removeFrame(frame);
        selectFrame(null);
    }

    // Choose and add new frame
    public void addFrame() {
        if( animationPackWindow.getAnimationPack() == null ) return;

        FramePicker picker = new FramePicker(animationPackWindow.getAnimationPack().getFramePack(),
                new FramePicker.PickListener() {
                    @Override
                    public void onFramePicked(Frame frame) {
                        AnimationFrame newFrame = new AnimationFrame(frame, 3);
                        animationFrameWindow.addFrame(newFrame);
                        selectFrame(newFrame);
                    }

                    @Override
                    public void onCancel() {}
                });
        picker.show(stage);
    }

    // Create new pack
    void newAnimationPack() {
        // Show file chooser
        final FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
        chooser.setSelectionMode(FileChooser.SelectionMode.FILES);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDirectory(path);
        FileTypeFilter filter = new FileTypeFilter(false);
        filter.addRule("FramePack file", "frm");
        chooser.setFileTypeFilter(filter);
        chooser.setListener(new FileChooserListener() {
            @Override
            public void selected(Array<FileHandle> files) {
                // Find the frm files
                FileHandle frm = files.first();
                FileHandle atlas = new FileHandle(frm.pathWithoutExtension()+".atlas");
                FileHandle anm = new FileHandle(frm.pathWithoutExtension()+".anm");

                // Load stuff
                TextureAtlas tAtlas = new TextureAtlas(atlas);
                FramePack framePack = new FramePackReader(frm).read(tAtlas);

                // Create new stage
                setAnimationPack(new AnimationPack(framePack), anm);
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
        filter.addRule("Animation pack files", "anm");
        chooser.setFileTypeFilter(filter);
        chooser.setListener(new FileChooserListener() {

            @Override
            public void selected(Array<FileHandle> files) {
                // Read frm and atlas
                FileHandle frm = new FileHandle(files.first().pathWithoutExtension() + ".frm");
                FileHandle atlasHandle = new FileHandle(files.first().pathWithoutExtension() + ".atlas");
                TextureAtlas atlas = new TextureAtlas(atlasHandle);
                FramePack framePack = new FramePackReader(frm).read(atlas);

                AnimationPackReader reader = new AnimationPackReader(files.first());
                setAnimationPack(reader.read(framePack), files.first());
            }

            @Override
            public void canceled() {
            }
        });

        stage.addActor(chooser);
    }

    // Save pack
    void save() {
        if( animationPackWindow.getAnimationPack() == null ) return;

        AnimationPackWriter writer = new AnimationPackWriter(anmFile.file());
        try {
            writer.write(animationPackWindow.getAnimationPack());
            Dialogs.showOKDialog(stage, "Success", "Animation Pack saved.");
        } catch (IOException e) {
            e.printStackTrace();
            Dialogs.showErrorDialog(stage, "Could not write file.  Check permissions?");
        }
    }
}
