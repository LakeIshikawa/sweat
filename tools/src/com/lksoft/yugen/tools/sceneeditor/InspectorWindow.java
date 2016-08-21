package com.lksoft.yugen.tools.sceneeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.SimpleFloatSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.lksoft.yugen.stateless.SceneDef;

/**
 * Created by Lake on 10/06/2016.
 */
public class InspectorWindow extends VisWindow {

    // Editor
    private SceneEditorScreen sceneEditorScreen;

    // Widgets
    private VisTextButton fsmBrowseButton;
    private VisTextButton animationBrowseButton;
    private VisTextField fsmScript;
    private VisTextField animation;
    private Spinner startPosX, startPosY;
    private Spinner scrollFactorX, scrollFactorY;
    private Spinner layer;

    // State
    private SceneDef.SceneFsmDef currentDef;

    /**
     * Create sprite def window
     * @param sceneEditorScreen
     */
    public InspectorWindow(final SceneEditorScreen sceneEditorScreen) {
        super("Fsm details");
        this.sceneEditorScreen = sceneEditorScreen;
        TableUtils.setSpacingDefaults(this);

        add(new VisLabel("FSM:")).left();
        VisTable hor = new VisTable();
        hor.add(fsmScript = new VisTextField()).growX();
        hor.add(fsmBrowseButton = new VisTextButton("..."));
        add(hor).colspan(2).growX();
        row();
        add(new VisLabel("Animation:")).left();
        hor = new VisTable();
        hor.add(animation = new VisTextField()).growX();
        hor.add(animationBrowseButton = new VisTextButton("..."));
        add(hor).colspan(2).growX();
        row();
        add(new VisLabel("Start:")).left();
        add(startPosX = new Spinner("x", new IntSpinnerModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1))).growX();
        add(startPosY = new Spinner("y", new IntSpinnerModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1))).growX();
        row();
        add(new VisLabel("Scroll factor: ")).left();
        add(scrollFactorX = new Spinner("x", new SimpleFloatSpinnerModel(0, -5, 5, 0.1f, 4))).growX();
        add(scrollFactorY = new Spinner("y", new SimpleFloatSpinnerModel(0, -5, 5, 0.1f, 4))).growX();
        row();
        add(new VisLabel("Z Layer: ")).left();;
        add(layer = new Spinner("", new IntSpinnerModel(0, 0, 10, 1))).colspan(2).growX();
        row();

        fsmScript.setDisabled(true);
        animation.setDisabled(true);

        // Listeners
        fsmBrowseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sceneEditorScreen.changeFsm(currentDef);
            }
        });
        animationBrowseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sceneEditorScreen.changeAnimationDef(currentDef);
            }
        });
        startPosX.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) currentDef.x = ((IntSpinnerModel)startPosX.getModel()).getValue();
            }
        });
        startPosY.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) currentDef.y = ((IntSpinnerModel)startPosY.getModel()).getValue();
            }
        });
        scrollFactorX.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) currentDef.scrollFactorX = ((SimpleFloatSpinnerModel)scrollFactorX.getModel()).getValue();
            }
        });
        scrollFactorY.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) currentDef.scrollFactorY = ((SimpleFloatSpinnerModel)scrollFactorY.getModel()).getValue();
            }
        });
        layer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( currentDef != null ) {
                    currentDef.layer = ((IntSpinnerModel)layer.getModel()).getValue();
                }
            }
        });
    }

    /**
     * Set view to sprite def
     * @param fsmDef
     */
    public void setFsmDef(SceneDef.SceneFsmDef fsmDef){
        currentDef = fsmDef;

        if( fsmDef == null ){
            fsmScript.setText("");
            animation.setText("");
            ((IntSpinnerModel) startPosX.getModel()).setValue(0);
            ((IntSpinnerModel) startPosY.getModel()).setValue(0);
            ((SimpleFloatSpinnerModel) scrollFactorX.getModel()).setValue(0);
            ((SimpleFloatSpinnerModel) scrollFactorY.getModel()).setValue(0);
            ((IntSpinnerModel) layer.getModel()).setValue(0);
        } else {
            fsmScript.setText(fsmDef.scriptPath);
            animation.setText(fsmDef.animation);
            ((IntSpinnerModel) startPosX.getModel()).setValue(fsmDef.x);
            ((IntSpinnerModel) startPosY.getModel()).setValue(fsmDef.y);
            ((SimpleFloatSpinnerModel) scrollFactorX.getModel()).setValue(fsmDef.scrollFactorX);
            ((SimpleFloatSpinnerModel) scrollFactorY.getModel()).setValue(fsmDef.scrollFactorY);
            ((IntSpinnerModel) layer.getModel()).setValue(fsmDef.layer);
        }
    }
}
