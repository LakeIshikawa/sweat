package com.lksoft.sweat.tools.sceneeditor;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserListener;
import com.kotcrab.vis.ui.widget.file.FileTypeFilter;
import com.lksoft.sweat.stateless.AnimationDef;
import com.lksoft.sweat.stateless.SceneDef;
import com.lksoft.sweat.tools.CameraControls;

import java.io.File;

/**
 * Created by Lake on 07/06/2016.
 */
public class SceneEditorScreen implements Screen, InputProcessor {

    // UI
    private Stage stage;


    // Windows
    private InspectorWindow inspectorWindow;
    private SceneDefWindow sceneDefWindow;
    private SceneSettingsWindow sceneSettingsWindow;

    // Current stage
    private SceneFsmDefRenderer stageRenderer;
    private FileHandle currentSceneFile;

    // Screen size info
    private int lastW, lastH;

    // Selection
    private SceneDef.SceneFsmDef selection;
    // Dragging
    private Vector2 clickOffset;
    private Rectangle bounds = new Rectangle();

     // Accessors
    public SceneDef getCurrentSceneDef(){
        return sceneDefWindow.getSceneDef();
    }

    @Override
    public void show() {
        // Don't reinitialize if coming back from test screen
        if( sceneDefWindow != null ) return;

        stage = new Stage();
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, this));

        // Create menu bar
        SceneEditorMenuBar menuBar = new SceneEditorMenuBar(this);

        // Create inspector
        inspectorWindow = new InspectorWindow(this);
        inspectorWindow.setSize(300, 200);
        inspectorWindow.setPosition(stage.getViewport().getWorldWidth()- inspectorWindow.getWidth(), 0);

        // Create scene def window
        sceneDefWindow = new SceneDefWindow(this);
        sceneDefWindow.setSize(300, 400);
        sceneDefWindow.setPosition(0, 0);

        // Create scene settings window
        sceneSettingsWindow = new SceneSettingsWindow(this);
        sceneSettingsWindow.setSize(300, 250);
        sceneSettingsWindow.setPosition(stage.getViewport().getWorldWidth()- sceneSettingsWindow.getWidth(),
                stage.getViewport().getWorldHeight() - (30 + sceneSettingsWindow.getHeight()));

        // Root with menu bar
        VisTable root = new VisTable(true);
        root.add(menuBar.getTable()).fillX().expandX().row();
        root.add().expand().fill();
        root.setFillParent(true);

        stage.addActor(root);
        stage.addActor(inspectorWindow);
        stage.addActor(sceneDefWindow);
        stage.addActor(sceneSettingsWindow);

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
    private void setSceneDef(SceneDef sceneDef) {
        sceneDefWindow.setSceneDef(sceneDef);
        sceneSettingsWindow.setSceneDef(sceneDef);
        stageRenderer = new SceneFsmDefRenderer(sceneDef);
        stageRenderer.resize(lastW, lastH);

        Gdx.input.setInputProcessor(
                new InputMultiplexer(stage, new CameraControls(stageRenderer.getCamera()), this));
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.N: newStage(); break;
            case Input.Keys.O: open(); break;
            case Input.Keys.S: save(); break;

            case Input.Keys.ESCAPE:
                selection = null;
                inspectorWindow.setFsmDef(null);
                break;

            case Input.Keys.SPACE:
                // Save
                if( getCurrentSceneDef() == null ) break;
                getCurrentSceneDef().write(currentSceneFile);

                SceneEditor.instance.setScreen(new SceneTestScreen(this, currentSceneFile));
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
        if( getCurrentSceneDef() == null ) return false;

        // Selection
        Vector2 touch = stageRenderer.getTouch(screenX, screenY);

        // Get first intersecting sprite
        boolean found = false;
        for( int l=getCurrentSceneDef().layout.size-1; l>=0; l-- ) {
            SceneDef.SceneFsmDef fsmDef = getCurrentSceneDef().layout.get(l);

            fsmDef.getBounds(bounds);

            if (bounds.contains(touch)) {
                select(fsmDef);
                found = true;
                clickOffset = new Vector2(touch);
                clickOffset.sub(fsmDef.x, fsmDef.y);
                break;
            }
        }

        // Clear selection if click on nothing
        if(!found) {
            selection = null;
            inspectorWindow.setFsmDef(null);
            stage.unfocusAll();
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
                selection.x = ((int) place.x);
                selection.y = ((int) place.y);
                inspectorWindow.setFsmDef(selection);
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

    // Select fsm def
    void select(SceneDef.SceneFsmDef fsmDef){
        selection = fsmDef;
        inspectorWindow.setFsmDef(selection);
        sceneDefWindow.setSelected(selection);
    }

    // Add fsm
    void addFsm() {
        if( sceneDefWindow.getSceneDef() == null ) return;

        final FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
        chooser.setSelectionMode(FileChooser.SelectionMode.FILES);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDirectory(new File("."));
        FileTypeFilter filter = new FileTypeFilter(false);
        filter.addRule("Fsm script file", "java");
        chooser.setFileTypeFilter(filter);
        chooser.setListener(new FileChooserListener() {

            @Override
            public void selected(Array<FileHandle> files) {
                SceneDef.SceneFsmDef def = new SceneDef.SceneFsmDef();
                String absPath = files.first().file().getAbsolutePath();
                String rootPath = new File(".").getAbsolutePath();
                def.scriptPath = absPath.replace(rootPath+File.separator, "");
                def.animation = def.loadAnimationPack().getAnimations().first().getName();
                def.layer = 0;

                sceneDefWindow.addFsm(def);
                select(def);
            }

            @Override
            public void canceled() {
            }
        });

        stage.addActor(chooser);
    }

    // Remove fsm
    public void removeFsm(SceneDef.SceneFsmDef fsm) {
        sceneDefWindow.removeFsm(fsm);
        select(null);
    }

    // Change fsm of specified def
    public void changeFsm(final SceneDef.SceneFsmDef currentDef) {
        if( sceneDefWindow.getSceneDef() == null ) return;

        final FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
        chooser.setSelectionMode(FileChooser.SelectionMode.FILES);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDirectory(new File("."));
        FileTypeFilter filter = new FileTypeFilter(false);
        filter.addRule("Fsm script file", "java");
        chooser.setFileTypeFilter(filter);
        chooser.setListener(new FileChooserListener() {

            @Override
            public void selected(Array<FileHandle> files) {
                String absPath = files.first().file().getAbsolutePath();
                String rootPath = new File(".").getAbsolutePath();
                currentDef.scriptPath = absPath.replace(rootPath+File.separator, "");
                currentDef.animation = currentDef.loadAnimationPack().getAnimations().first().getName();
                inspectorWindow.setFsmDef(currentDef);
                sceneDefWindow.update();
            }

            @Override
            public void canceled() {
            }
        });

        stage.addActor(chooser);
    }

    // Choose animation def for specified def
    public void changeAnimationDef(final SceneDef.SceneFsmDef currentDef) {
        if( sceneDefWindow.getSceneDef() == null ) return;

        AnimationDefPicker picker = new AnimationDefPicker(currentDef.loadAnimationPack(), new AnimationDefPicker.PickListener() {
            @Override
            public void onFramePicked(AnimationDef frame) {
                currentDef.animation = frame.getName();
                inspectorWindow.setFsmDef(currentDef);
                sceneDefWindow.update();
            }

            @Override
            public void onCancel() {}
        });
        picker.show(stage);
    }

    // Create new stage
    void newStage() {
        // Show file chooser
        final FileChooser chooser = new FileChooser(FileChooser.Mode.SAVE);
        chooser.setSelectionMode(FileChooser.SelectionMode.FILES);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDirectory(new File("."));
        FileTypeFilter filter = new FileTypeFilter(false);
        filter.addRule("Scene files", "scn");
        chooser.setFileTypeFilter(filter);
        chooser.setListener(new FileChooserListener() {
            @Override
            public void selected(Array<FileHandle> files) {
                // Create new stage
                currentSceneFile = files.first();
                setSceneDef(new SceneDef());
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
        chooser.setDirectory(new File("."));
        FileTypeFilter filter = new FileTypeFilter(false);
        filter.addRule("Scene files", "scn");
        chooser.setFileTypeFilter(filter);
        chooser.setListener(new FileChooserListener() {

            @Override
            public void selected(Array<FileHandle> files) {
                currentSceneFile = files.first();
                SceneDef sceneDef = SceneDef.read(files.first());
                setSceneDef(sceneDef);
            }

            @Override
            public void canceled() {
            }
        });

        stage.addActor(chooser);
    }

    // Save stage
    void save() {
        if( getCurrentSceneDef() == null ) return;
        getCurrentSceneDef().write(currentSceneFile);
        Dialogs.showOKDialog(stage, "Success", "Scene saved.");
    }
}
