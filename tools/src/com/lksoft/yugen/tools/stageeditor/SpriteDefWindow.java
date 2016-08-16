package com.lksoft.yugen.tools.stageeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.SimpleFloatSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.lksoft.yugen.stateless.AnimationDef;

/**
 * Created by Lake on 10/06/2016.
 */
public class SpriteDefWindow extends VisWindow {

    // Editor
    private StageEditorScreen stageEditorScreen;

    // Widgets
    private VisTextField name;
    private VisTextField resource;
    private Spinner startPosX, startPosY;
    private Spinner scrollFactorX, scrollFactorY;
    private Spinner layer;

    // State
    //private StageSpriteDef currentDef;

    /**
     * Create sprite def window
     * @param stageEditorScreen
     */
    public SpriteDefWindow(final StageEditorScreen stageEditorScreen) {
        super("Sprite definition");
        this.stageEditorScreen = stageEditorScreen;
        TableUtils.setSpacingDefaults(this);

        VisTable table = new VisTable();
        table.add(new VisLabel("Name:"));
        table.add(name = new VisTextField());
        table.row();
        table.add(new VisLabel("Resource:"));
        table.add(resource = new VisTextField());
        table.row();
        table.add(new VisLabel("Start: "));
        table.add(startPosX = new Spinner("x", new IntSpinnerModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1)));
        table.add(startPosY = new Spinner("y", new IntSpinnerModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1)));
        table.row();
        table.add(new VisLabel("Scroll factor: "));
        table.add(scrollFactorX = new Spinner("x", new SimpleFloatSpinnerModel(0, -5, 5, 0.1f, 4)));
        table.add(scrollFactorY = new Spinner("y", new SimpleFloatSpinnerModel(0, -5, 5, 0.1f, 4)));
        table.row();
        table.add(new VisLabel("Z Layer: "));
        table.add(layer = new Spinner("", new IntSpinnerModel(0, 0, 1, 1)));
        table.row();

        add(table);


//        // Listeners
//        name.setTextFieldListener(new VisTextField.TextFieldListener() {
//            @Override
//            public void keyTyped(VisTextField textField, char c) {
//                if( currentDef != null ) currentDef.setName(name.getText());
//            }
//        });
//        resource.setTextFieldListener(new VisTextField.TextFieldListener() {
//            @Override
//            public void keyTyped(VisTextField textField, char c) {
//                if( currentDef != null ){
//                    AnimationDef sequence = stageEditorScreen.getCurrentStageDef().getAnimationPack().getAnimationDef(resource.getText());
//                    if( sequence != null ){
//                        currentDef.setResource(sequence);
//                    }
//                }
//            }
//        });
//        startPosX.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                if( currentDef != null ) currentDef.setStartX(((IntSpinnerModel)startPosX.getModel()).getValue());
//            }
//        });
//        startPosY.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                if( currentDef != null ) currentDef.setStartY(((IntSpinnerModel)startPosY.getModel()).getValue());
//            }
//        });
//        scrollFactorX.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                if( currentDef != null ) currentDef.setScrollFactorX(((SimpleFloatSpinnerModel)scrollFactorX.getModel()).getValue());
//            }
//        });
//        scrollFactorY.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                if( currentDef != null ) currentDef.setScrollFactorY(((SimpleFloatSpinnerModel)scrollFactorY.getModel()).getValue());
//            }
//        });
//        layer.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                if( currentDef != null ) {
//                    stageEditorScreen.getCurrentStageDef().changeLayer(currentDef,
//                            ((IntSpinnerModel)layer.getModel()).getValue());
//                }
//            }
//        });
    }

//    /**
//     * Set view to sprite def
//     * @param stageSpriteDef
//     */
//    public void setSpriteDef(StageSpriteDef stageSpriteDef){
//        currentDef = stageSpriteDef;
//
//        if( stageSpriteDef == null ){
//            name.setText("");
//            resource.setText("");
//            ((IntSpinnerModel) startPosX.getModel()).setValue(0);
//            ((IntSpinnerModel) startPosY.getModel()).setValue(0);
//            ((SimpleFloatSpinnerModel) scrollFactorX.getModel()).setValue(0);
//            ((SimpleFloatSpinnerModel) scrollFactorY.getModel()).setValue(0);
//            ((IntSpinnerModel) layer.getModel()).setValue(0);
//        } else {
//            name.setText(stageSpriteDef.getName());
//            resource.setText(stageSpriteDef.getResource().getName());
//            ((IntSpinnerModel) startPosX.getModel()).setValue(stageSpriteDef.getStartX());
//            ((IntSpinnerModel) startPosY.getModel()).setValue(stageSpriteDef.getStartY());
//            ((SimpleFloatSpinnerModel) scrollFactorX.getModel()).setValue(stageSpriteDef.getScrollFactorX());
//            ((SimpleFloatSpinnerModel) scrollFactorY.getModel()).setValue(stageSpriteDef.getScrollFactorY());
//            ((IntSpinnerModel) layer.getModel()).setValue(stageSpriteDef.getLayer());
//        }
//    }
}
