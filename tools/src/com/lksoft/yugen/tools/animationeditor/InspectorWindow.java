package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.lksoft.yugen.stateless.AnimationFrame;

/**
 * Created by Lake on 05/08/2016.
 */
public class InspectorWindow extends VisWindow {
    // Editor screen
    private AnimationEditorScreen editorScreen;

    // Current frame
    private AnimationFrame frame;

    // GUI components
    private VisLabel nameLabel;
    private Spinner ticksSpinner;

    /**
     * Create playback window
     * @param animationEditorScreen
     */
    public InspectorWindow(AnimationEditorScreen animationEditorScreen) {
        super("Inspector");
        this.editorScreen = animationEditorScreen;
        TableUtils.setSpacingDefaults(this);

        add(nameLabel = new VisLabel()).colspan(2).growX().row();
        add(ticksSpinner = new Spinner("Ticks:", new IntSpinnerModel(3, 1, 100, 1))).colspan(2).growX().row();

        ticksSpinner.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( frame == null ) return;
                frame.lengthTicks = ((IntSpinnerModel)ticksSpinner.getModel()).getValue();
            }
        });
    }

    /**
     * Set frame
     * @param frame
     */
    public void setFrame(AnimationFrame frame){
        this.frame = frame;

        if( frame == null ){
            nameLabel.setText("");
            ((IntSpinnerModel) ticksSpinner.getModel()).setValue(3);
        } else {
            nameLabel.setText(frame.frame.region.name);
            ((IntSpinnerModel) ticksSpinner.getModel()).setValue(frame.lengthTicks);
        }
    }
}
