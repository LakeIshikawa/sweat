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

    // GUI components
    private FrameEditorMenuBar menuBar;
    private FramePackWindow framePackWindow;
    private InspectorWindow inspectorWindow;

    // Current animations
    private FrameRenderer frameRenderer;
    private FileHandle frmFile;

    // Screen size info
    private int lastW, lastH;

    // Palette
    private Vector2 palette;

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

        // Inspector window
        inspectorWindow = new InspectorWindow(this);
        inspectorWindow.setSize(150, 60);
        inspectorWindow.setPosition(5, 5);

        // Tool window
        ToolsWindow toolsWindow = new ToolsWindow(this);
        toolsWindow.pack();
        toolsWindow.setPosition( w/2 - toolsWindow.getWidth()/2, 5);

        // Root with menu bar
        VisTable root = new VisTable(true);
        root.add(menuBar.getTable()).fillX().expandX().row();
        root.add().expand().fill();
        root.setFillParent(true);

        stage.addActor(root);
        stage.addActor(framePackWindow);
        stage.addActor(inspectorWindow);
        stage.addActor(toolsWindow);
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
        frameRenderer.setFrame(frame);
        // Inspector
        inspectorWindow.setFrame(frame);
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

    // Set origin
    private void setOrigin(int x, int y) {
        framePackWindow.getSelected().originX = x;
        framePackWindow.getSelected().originY = y;
        inspectorWindow.setFrame(framePackWindow.getSelected());
    }

    // Copy
    public void copy() {
        if( framePackWindow.getFramePack() == null ) return;
        palette= new Vector2(framePackWindow.getSelected().originX, framePackWindow.getSelected().originY);
    }

    // Paste to cur frame
    public void paste() {
        if( framePackWindow.getFramePack() == null || palette == null ) return;
        framePackWindow.getSelected().originX = (int) palette.x;
        framePackWindow.getSelected().originY = (int) palette.y;
        inspectorWindow.setFrame(framePackWindow.getSelected());
    }

    // Paste to all frames
    public void pasteToAll() {
        if( framePackWindow.getFramePack() == null || palette == null ) return;
        for( Frame f : framePackWindow.getFramePack().getFrames() ){
            f.originX = (int) palette.x;
            f.originY = (int) palette.y;
        }
        inspectorWindow.setFrame(framePackWindow.getSelected());
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
        Vector2 touch = frameRenderer.getTouch(screenX, screenY);
        setOrigin((int)-touch.x, (int)-touch.y);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 touch = frameRenderer.getTouch(screenX, screenY);
        setOrigin((int)-touch.x, (int)-touch.y);
        return true;
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
