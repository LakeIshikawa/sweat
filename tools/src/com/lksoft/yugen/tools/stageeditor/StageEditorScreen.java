package com.lksoft.yugen.tools.stageeditor;

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
import com.lksoft.yugen.stateless.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by Lake on 07/06/2016.
 */
public class StageEditorScreen implements Screen, InputProcessor {

    // Stages path
    private File stagesPath;

    // UI
    private Stage stage;

    // Sprite def window
    private SpriteDefWindow spriteDefWindow;

    // Current stage
    private StageDef currentStageDef;
    private StageDefRenderer stageRenderer;
    private String stageBaseName;

    // Screen size info
    private int lastW, lastH;

    // Selection
    private StageSpriteDef selection;
    // Dragging
    private Vector2 clickOffset;

    /**
     * Create b1 stage editor
     * @param stagesPath
     */
    public StageEditorScreen(File stagesPath){
        this.stagesPath = stagesPath;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, this));

        // Create menu bar
        StageEditorMenuBar menuBar = new StageEditorMenuBar(this);

        // Create sprite def window
        spriteDefWindow = new SpriteDefWindow(this);
        spriteDefWindow.pack();
        spriteDefWindow.setPosition(stage.getViewport().getWorldWidth()-spriteDefWindow.getWidth(), 0);

        // Root with menu bar
        VisTable root = new VisTable(true);
        root.add(menuBar.getTable()).fillX().expandX().row();
        root.add().expand().fill();
        root.setFillParent(true);

        stage.addActor(root);
        stage.addActor(spriteDefWindow);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        if( stageRenderer != null ) stageRenderer.resize(width, height);
        lastW = width;
        lastH = height;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Unfocus
        if( Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) ){
            stage.unfocusAll();
        }

        // Render stage layout
        if( stageRenderer != null ) {
            stageRenderer.render(selection);
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

    // Set the current stage layout
    private void setLayout(StageDef stageDef) {
        currentStageDef = stageDef;
        stageRenderer = new StageDefRenderer(getCurrentStageDef());
        stageRenderer.resize(lastW, lastH);
        stageBaseName = stageDef.getAnmFile().nameWithoutExtension();
    }

    public StageDef getCurrentStageDef() {
        return currentStageDef;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.N: newStage(); break;
            case Input.Keys.O: open(); break;
            case Input.Keys.S: save(); break;
            case Input.Keys.A: addSpriteDef(); break;

            case Input.Keys.PLUS:
            case Input.Keys.MINUS:
                if( selection == null ) break;

                Array<StageSpriteDef> defs = getCurrentStageDef().getLayers()[selection.getLayer()];
                int pos = defs.indexOf(selection, true);
                int newpos = Math.max(0, pos + (keycode==Input.Keys.PLUS ? 1 : -1));
                if( newpos<0 || newpos > defs.size-1 ) break;

                defs.removeIndex(pos);
                defs.insert(newpos, selection);
                break;

            case Input.Keys.ESCAPE:
                selection = null;
                spriteDefWindow.setSpriteDef(null);
                break;

            case Input.Keys.SPACE:
                StageEditor.instance.setScreen(
                        new TestScreen(
                                this,
                                new com.lksoft.yugen.stateful.Stage(getCurrentStageDef(), null, null, new ManualCamera(getCurrentStageDef()))));
                break;

            case Input.Keys.RIGHT: stageRenderer.moveCamera(10, 0); break;
            case Input.Keys.LEFT: stageRenderer.moveCamera(-10, 0); break;
            case Input.Keys.UP: stageRenderer.moveCamera(0, 10); break;
            case Input.Keys.DOWN: stageRenderer.moveCamera(0,-10); break;
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
        // Selection
        if( getCurrentStageDef() != null ){
            Vector2 touch = stageRenderer.getTouch(screenX, screenY);

            // Get first intersecting sprite
            Array<StageSpriteDef>[] layers = getCurrentStageDef().getLayers();
            boolean found = false;
            for( int l=layers.length-1; l>=0; l-- ) {
                Array<StageSpriteDef> dfs = layers[l];
                for (int i = dfs.size - 1; i >= 0; i--) {
                    if (dfs.get(i).getBounds().contains(touch)) {
                        selection = dfs.get(i);
                        spriteDefWindow.setSpriteDef(selection);
                        found = true;
                        clickOffset = new Vector2(touch);
                        clickOffset.sub(dfs.get(i).getStartX(), dfs.get(i).getStartY());
                        break;
                    }
                }
            }

            // Clear selection if click on nothing
            if(!found) {
                selection = null;
                spriteDefWindow.setSpriteDef(null);
                stage.unfocusAll();
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Dragging
        if( clickOffset != null ){
            Vector2 place = stageRenderer.getTouch(screenX, screenY);
            place.sub(clickOffset);

            if( selection != null ) {
                selection.setStartX((int) place.x);
                selection.setStartY((int) place.y);
                spriteDefWindow.setSpriteDef(selection);
            }
        }
        else{
            clickOffset = null;
        }
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

    // Create new stage
    void newStage() {
        // Show file chooser
        final FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
        chooser.setSelectionMode(FileChooser.SelectionMode.FILES);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDirectory(stagesPath);
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
                SpritePack spritePack = new SpritePackReader(frm).read(atlas);

                // Create new stage
                setLayout(new StageDef(AnimationPack.read(files.first(), spritePack), files.first(), frm, atlasHandle));
            }

            @Override
            public void canceled() {
            }
        });

        stage.addActor(chooser);
    }

    // Add sprite def
    void addSpriteDef() {
        if( currentStageDef == null ) return;

        AnimationDefPicker picker = new AnimationDefPicker(getCurrentStageDef().getAnimationPack(), new AnimationDefPicker.PickListener() {
            @Override
            public void onFramePicked(AnimationDef frame) {
                getCurrentStageDef().addSpriteDef(new StageSpriteDef(frame));
            }

            @Override
            public void onCancel() {}
        });
        picker.show(stage);
    }

    // Open stage
    void open() {
        final FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
        chooser.setSelectionMode(FileChooser.SelectionMode.FILES);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDirectory(stagesPath);
        FileTypeFilter filter = new FileTypeFilter(false);
        filter.addRule("Stage files", "stg");
        chooser.setFileTypeFilter(filter);
        chooser.setListener(new FileChooserListener() {

            @Override
            public void selected(Array<FileHandle> files) {
                StageDefReader reader = new StageDefReader(files.first());
                setLayout(reader.read());
            }

            @Override
            public void canceled() {
            }
        });

        stage.addActor(chooser);
    }

    // Save stage
    void save() {
        if( getCurrentStageDef() == null ) return;
        StageDefWriter writer = new StageDefWriter(new File(getCurrentStageDef().getAnmFile().parent().path(), stageBaseName+".stg"));
        try {
            writer.write(getCurrentStageDef());
            Dialogs.showOKDialog(stage, "Success", "Stage saved.");
        } catch (IOException e) {
            e.printStackTrace();
            Dialogs.showErrorDialog(stage, "Could not write file.  Check permissions?");
        }
    }
}
