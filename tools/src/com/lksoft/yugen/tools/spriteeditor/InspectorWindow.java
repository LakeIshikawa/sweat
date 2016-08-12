package com.lksoft.yugen.tools.spriteeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.lksoft.yugen.stateless.SpriteDef;

/**
 * Created by Lake on 05/08/2016.
 */
public class InspectorWindow extends VisWindow {
    // Editor screen
    private SpriteEditorScreen editorScreen;

    // Current spriteDef
    private SpriteDef spriteDef;

    // GUI components
    private Spinner xSpinner;
    private Spinner ySpinner;

    /**
     * Create playback window
     * @param spriteEditorScreen
     */
    public InspectorWindow(SpriteEditorScreen spriteEditorScreen) {
        super("Inspector");
        this.editorScreen = spriteEditorScreen;
        TableUtils.setSpacingDefaults(this);

        add(xSpinner = new Spinner("x", new IntSpinnerModel(0, 0, 0, 1))).colspan(2).growX();
        add(ySpinner = new Spinner("y", new IntSpinnerModel(0, 0, 0, 1))).colspan(2).growX();

        xSpinner.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( spriteDef == null ) return;
                spriteDef.originX = ((IntSpinnerModel)xSpinner.getModel()).getValue();
            }
        });
        ySpinner.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( spriteDef == null ) return;
                spriteDef.originY = ((IntSpinnerModel)ySpinner.getModel()).getValue();
            }
        });
    }

    /**
     * Set spriteDef
     * @param spriteDef
     */
    public void setSpriteDef(SpriteDef spriteDef){
        this.spriteDef = spriteDef;

        if( spriteDef == null ){
            ((IntSpinnerModel) xSpinner.getModel()).setValue(0);
            ((IntSpinnerModel) ySpinner.getModel()).setValue(0);
        } else {
            EventListener l = xSpinner.getListeners().first();
            xSpinner.removeListener(l);
            xSpinner.setModel(new IntSpinnerModel(0, -100, spriteDef.region.originalWidth+100, 5));
            xSpinner.addListener(l);

            l = ySpinner.getListeners().first();
            ySpinner.removeListener(l);
            ySpinner.setModel(new IntSpinnerModel(0, -100, spriteDef.region.originalHeight+100, 5));
            ySpinner.addListener(l);

            ((IntSpinnerModel) xSpinner.getModel()).setValue(spriteDef.originX);
            ((IntSpinnerModel) ySpinner.getModel()).setValue(spriteDef.originY);
        }
    }
}
