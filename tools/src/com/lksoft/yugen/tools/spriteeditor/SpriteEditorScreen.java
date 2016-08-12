package com.lksoft.yugen.tools.spriteeditor;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserListener;
import com.kotcrab.vis.ui.widget.file.FileTypeFilter;
import com.lksoft.yugen.stateless.SpriteDef;
import com.lksoft.yugen.stateless.SpritePack;
import com.lksoft.yugen.stateless.SpritePackReader;

import java.io.File;
import java.io.IOException;

/**
 * Created by Lake on 07/06/2016.
 */
public class SpriteEditorScreen implements Screen, InputProcessor {

    // Chooser Path
    private File path;

    // UI
    private Stage stage;

    // GUI components
    private SpriteEditorMenuBar menuBar;
    private SpritePackWindow spritePackWindow;
    private InspectorWindow inspectorWindow;

    // Current animations
    private SpriteDefRenderer spriteDefRenderer;
    private FileHandle frmFile;

    // Screen size info
    private int lastW, lastH;

    // Palette
    private Vector2 palette;

    /**
     * Create spriteDef editor
     * @param path
     */
    public SpriteEditorScreen(File path){
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
        menuBar = new SpriteEditorMenuBar(this);

        // Animation pack window
        spritePackWindow = new SpritePackWindow(this);
        spritePackWindow.setSize(300, h - 30);
        spritePackWindow.setPosition((w-5)- spritePackWindow.getWidth(), 5);

        // Inspector window
        inspectorWindow = new InspectorWindow(this);
        inspectorWindow.setSize(150, 60);
        inspectorWindow.setPosition(5, 5);

        // Tool window
        com.lksoft.yugen.tools.spriteeditor.ToolsWindow toolsWindow = new com.lksoft.yugen.tools.spriteeditor.ToolsWindow(this);
        toolsWindow.pack();
        toolsWindow.setPosition( w/2 - toolsWindow.getWidth()/2, 5);

        // Root with menu bar
        VisTable root = new VisTable(true);
        root.add(menuBar.getTable()).fillX().expandX().row();
        root.add().expand().fill();
        root.setFillParent(true);

        stage.addActor(root);
        stage.addActor(spritePackWindow);
        stage.addActor(inspectorWindow);
        stage.addActor(toolsWindow);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        if( spriteDefRenderer != null ) spriteDefRenderer.resize(width, height);
        lastW = width;
        lastH = height;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render stage layout
        if( spriteDefRenderer != null ) {
            spriteDefRenderer.render();
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

    // Set the current spriteDef pack
    private void setFramePack(SpritePack pack, FileHandle frmFile) {
        this.frmFile = frmFile;

        // Create renderer
        spriteDefRenderer = new SpriteDefRenderer();
        spriteDefRenderer.resize(lastW, lastH);

        // Update GUI
        spritePackWindow.setFramePack(pack);

        // Select first animation def
        selectFrame(pack.getSpriteDefs().first());
    }

    // Selects the specified spriteDef
    void selectFrame(SpriteDef spriteDef) {
        // Set window selection
        spritePackWindow.setSelected(spriteDef);
        // Renderer
        spriteDefRenderer.setSpriteDef(spriteDef);
        // Inspector
        inspectorWindow.setSpriteDef(spriteDef);
    }

    // Remove spriteDef
    void removeFrame(SpriteDef spriteDef) {
        spritePackWindow.removeFrame(spriteDef);
        selectFrame(null);
    }

    // Choose region and add new spriteDef
    public void addFrame() {
        if( spritePackWindow.getFramePack() == null ) return;

        com.lksoft.yugen.tools.spriteeditor.RegionPicker picker = new com.lksoft.yugen.tools.spriteeditor.RegionPicker(spritePackWindow.getFramePack().getAtlas(),
                new com.lksoft.yugen.tools.spriteeditor.RegionPicker.PickListener() {
                    @Override
                    public void onRegionPicked(TextureAtlas.AtlasRegion region) {
                        SpriteDef newSpriteDef = new SpriteDef(region, region.originalWidth/2, region.originalHeight);
                        spritePackWindow.addFrame(newSpriteDef);
                        selectFrame(newSpriteDef);
                    }

                    @Override
                    public void onCancel() {}
                });
        picker.show(stage);
    }

    // Set origin
    private void setOrigin(int x, int y) {
        spritePackWindow.getSelected().originX = x;
        spritePackWindow.getSelected().originY = y;
        inspectorWindow.setSpriteDef(spritePackWindow.getSelected());
    }

    // Copy
    public void copy() {
        if( spritePackWindow.getFramePack() == null ) return;
        palette= new Vector2(spritePackWindow.getSelected().originX, spritePackWindow.getSelected().originY);
    }

    // Paste to cur spriteDef
    public void paste() {
        if( spritePackWindow.getFramePack() == null || palette == null ) return;
        spritePackWindow.getSelected().originX = (int) palette.x;
        spritePackWindow.getSelected().originY = (int) palette.y;
        inspectorWindow.setSpriteDef(spritePackWindow.getSelected());
    }

    // Paste to all frames
    public void pasteToAll() {
        if( spritePackWindow.getFramePack() == null || palette == null ) return;
        for( SpriteDef f : spritePackWindow.getFramePack().getSpriteDefs() ){
            f.originX = (int) palette.x;
            f.originY = (int) palette.y;
        }
        inspectorWindow.setSpriteDef(spritePackWindow.getSelected());
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

                // Create new spriteDef pack
                setFramePack(new SpritePack(tAtlas), frm);
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
        filter.addRule("SpriteDef pack files", "frm");
        chooser.setFileTypeFilter(filter);
        chooser.setListener(new FileChooserListener() {

            @Override
            public void selected(Array<FileHandle> files) {
                // Read frm and atlas
                FileHandle atlasHandle = new FileHandle(files.first().pathWithoutExtension() + ".atlas");
                TextureAtlas atlas = new TextureAtlas(atlasHandle);

                SpritePack spritePack = new SpritePackReader(files.first()).read(atlas);
                setFramePack(spritePack, files.first());
            }

            @Override
            public void canceled() {
            }
        });

        stage.addActor(chooser);
    }

    // Save pack
    void save() {
        if( spritePackWindow.getFramePack() == null ) return;

        SpritePackWriter writer = new SpritePackWriter(frmFile.file());
        try {
            writer.write(spritePackWindow.getFramePack());
            Dialogs.showOKDialog(stage, "Success", "SpriteDef Pack saved.");
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
        Vector2 touch = spriteDefRenderer.getTouch(screenX, screenY);
        setOrigin((int)-touch.x, (int)-touch.y);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 touch = spriteDefRenderer.getTouch(screenX, screenY);
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