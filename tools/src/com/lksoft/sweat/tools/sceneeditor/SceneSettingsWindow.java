package com.lksoft.sweat.tools.sceneeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.lksoft.sweat.stateless.SceneDef;

/**
 * Created by Lake on 10/06/2016.
 */
public class SceneSettingsWindow extends VisWindow {

    // Editor
    private SceneEditorScreen sceneEditorScreen;

    // Widgets
    private Spinner cameraX, cameraY, cameraW, cameraH;
    private Spinner areaL, areaR, areaT, areaB;

    // Current state
    private SceneDef currentDef;

    /**
     * Create scene settings window
     * @param sceneEditorScreen
     */
    public SceneSettingsWindow(final SceneEditorScreen sceneEditorScreen) {
        super("Scene");
        this.sceneEditorScreen = sceneEditorScreen;
        TableUtils.setSpacingDefaults(this);

        add(new VisLabel("Camera:")).colspan(2).growX().left();
        row();
        add(cameraX = new Spinner("x", new IntSpinnerModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1))).growX();
        add(cameraY = new Spinner("y", new IntSpinnerModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1))).growX();
        row();
        add(cameraW = new Spinner("W", new IntSpinnerModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1))).growX();
        add(cameraH = new Spinner("H", new IntSpinnerModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1))).growX();
        row();
        add(new VisLabel("Area:")).colspan(2).growX().left();
        row();
        add(areaL = new Spinner("L", new IntSpinnerModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1))).growX();
        add(areaR = new Spinner("R", new IntSpinnerModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1))).growX();
        row();
        add(areaT = new Spinner("T", new IntSpinnerModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1))).growX();
        add(areaB = new Spinner("B", new IntSpinnerModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1))).growX();

        // Listeners
        cameraX.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) currentDef.camera_x = ((IntSpinnerModel)cameraX.getModel()).getValue();
            }
        });
        cameraY.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) currentDef.camera_y = ((IntSpinnerModel)cameraY.getModel()).getValue();
            }
        });
        cameraW.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) currentDef.camera_width = ((IntSpinnerModel)cameraW.getModel()).getValue();
            }
        });
        cameraH.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) currentDef.camera_height = ((IntSpinnerModel)cameraH.getModel()).getValue();
            }
        });
        areaL.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) currentDef.area_l = ((IntSpinnerModel)areaL.getModel()).getValue();
            }
        });
        areaR.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) currentDef.area_r = ((IntSpinnerModel)areaR.getModel()).getValue();
            }
        });
        areaT.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) currentDef.area_t = ((IntSpinnerModel)areaT.getModel()).getValue();
            }
        });
        areaB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) currentDef.area_b = ((IntSpinnerModel)areaB.getModel()).getValue();
            }
        });
    }

    /**
     * Set view to current scene
     * @param scene
     */
    public void setSceneDef(SceneDef scene){
        currentDef = scene;

        if( scene == null ){
            ((IntSpinnerModel) cameraX.getModel()).setValue(0);
            ((IntSpinnerModel) cameraY.getModel()).setValue(0);
            ((IntSpinnerModel) cameraW.getModel()).setValue(0);
            ((IntSpinnerModel) cameraH.getModel()).setValue(0);
            ((IntSpinnerModel) areaL.getModel()).setValue(0);
            ((IntSpinnerModel) areaR.getModel()).setValue(0);
            ((IntSpinnerModel) areaT.getModel()).setValue(0);
            ((IntSpinnerModel) areaB.getModel()).setValue(0);
        } else {
            ((IntSpinnerModel) cameraX.getModel()).setValue(scene.camera_x);
            ((IntSpinnerModel) cameraY.getModel()).setValue(scene.camera_y);
            ((IntSpinnerModel) cameraW.getModel()).setValue(scene.camera_width);
            ((IntSpinnerModel) cameraH.getModel()).setValue(scene.camera_height);
            ((IntSpinnerModel) areaL.getModel()).setValue(scene.area_l);
            ((IntSpinnerModel) areaR.getModel()).setValue(scene.area_r);
            ((IntSpinnerModel) areaT.getModel()).setValue(scene.area_t);
            ((IntSpinnerModel) areaB.getModel()).setValue(scene.area_b);
        }
    }
}
