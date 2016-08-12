package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.SimpleFloatSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.lksoft.yugen.stateless.AnimationFrame;
import com.lksoft.yugen.stateless.BlendMode;

/**
 * Created by Lake on 05/08/2016.
 */
public class InspectorWindow extends VisWindow {
    // Editor screen
    private AnimationEditorScreen editorScreen;

    // Current spriteDef
    private AnimationFrame frame;
    // Current component
    private AnimationFrame.Component component;

    // GUI components
    private VisLabel nameLabel;
    private Spinner ticksSpinner;
    private Spinner x, y, sx, sy, rot;
    private VisSelectBox<BlendMode> blendModeSelect;

    /**
     * Create playback window
     * @param animationEditorScreen
     */
    public InspectorWindow(AnimationEditorScreen animationEditorScreen) {
        super("Inspector");
        this.editorScreen = animationEditorScreen;
        TableUtils.setSpacingDefaults(this);
        top();

        // Frame
        add(nameLabel = new VisLabel()).colspan(2).growX().row();
        add(ticksSpinner = new Spinner("Ticks:", new IntSpinnerModel(3, 1, 100, 1))).colspan(2).growX().row();

        ticksSpinner.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( frame == null ) return;
                frame.lengthTicks = ((IntSpinnerModel)ticksSpinner.getModel()).getValue();
            }
        });

        // Component
        VisTable row = new VisTable();
        row.add(x = new Spinner("x", new IntSpinnerModel(0, -1000, 1000, 1))).growX();
        row.add(y = new Spinner("y", new IntSpinnerModel(0, -1000, 1000, 1))).growX();
        add(row).colspan(2).growX().row();

        row = new VisTable();
        row.add(sx = new Spinner("sx", new SimpleFloatSpinnerModel(1, 0, 10, 0.1f))).growX();
        row.add(sy = new Spinner("sy", new SimpleFloatSpinnerModel(1, 0, 10, 0.1f))).growX();
        add(row).colspan(2).growX().row();

        row = new VisTable();
        row.add(rot = new Spinner("rot", new SimpleFloatSpinnerModel(0, 0, 360, 1))).growX();
        add(row).colspan(2).growX().row();

        blendModeSelect = new VisSelectBox<>();
        blendModeSelect.setItems(BlendMode.values());
        add(blendModeSelect).colspan(2).growX().row();

        x.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( component == null ) return;
                component.x = ((IntSpinnerModel)x.getModel()).getValue();
            }
        });
        y.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( component == null ) return;
                component.y = ((IntSpinnerModel)y.getModel()).getValue();
            }
        });
        sx.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( component == null ) return;
                component.scaleX = ((SimpleFloatSpinnerModel)sx.getModel()).getValue();
            }
        });
        sy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( component == null ) return;
                component.scaleY = ((SimpleFloatSpinnerModel)sy.getModel()).getValue();
            }
        });
        rot.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( component == null ) return;
                component.rotation = ((SimpleFloatSpinnerModel)rot.getModel()).getValue();
            }
        });
        blendModeSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( component == null ) return;
                component.blendMode = blendModeSelect.getSelected();
            }
        });
    }

    /**
     * Set spriteDef
     * @param frame
     */
    public void setFrame(AnimationFrame frame){
        this.frame = frame;

        if( frame == null ){
            nameLabel.setText("");
            ((IntSpinnerModel) ticksSpinner.getModel()).setValue(3);
        } else {
            nameLabel.setText(frame.components.first().spriteDef.region.name);
            ((IntSpinnerModel) ticksSpinner.getModel()).setValue(frame.lengthTicks);
        }
    }

    /**
     * Set component
     * @param component
     */
    public void setComponent(AnimationFrame.Component component){
        this.component = component;

        if( component == null ){
            ((IntSpinnerModel)x.getModel()).setValue(0);
            ((IntSpinnerModel)y.getModel()).setValue(0);
            ((SimpleFloatSpinnerModel)sx.getModel()).setValue(0);
            ((SimpleFloatSpinnerModel)sy.getModel()).setValue(0);
            ((SimpleFloatSpinnerModel)rot.getModel()).setValue(0);
            blendModeSelect.setSelected(BlendMode.NORMAL);
        } else {
            ((IntSpinnerModel)x.getModel()).setValue(component.x);
            ((IntSpinnerModel)y.getModel()).setValue(component.y);
            ((SimpleFloatSpinnerModel)sx.getModel()).setValue(component.scaleX);
            ((SimpleFloatSpinnerModel)sy.getModel()).setValue(component.scaleY);
            ((SimpleFloatSpinnerModel)rot.getModel()).setValue(component.rotation);
            blendModeSelect.setSelected(component.blendMode);
        }
    }
}
