package com.lksoft.sweat.tools.spriteeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.lksoft.sweat.stateless.SpriteDef;

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
    private VisTextButton c, t, l, r, b, tl, tr, br, bl;

    /**
     * Create playback window
     * @param spriteEditorScreen
     */
    public InspectorWindow(final SpriteEditorScreen spriteEditorScreen) {
        super("Inspector");
        this.editorScreen = spriteEditorScreen;
        TableUtils.setSpacingDefaults(this);

        add(xSpinner = new Spinner("x", new IntSpinnerModel(0, 0, 0, 1))).growX();
        add(ySpinner = new Spinner("y", new IntSpinnerModel(0, 0, 0, 1))).growX();
        row();
        VisTable row = new VisTable();
        row.add(tl = new VisTextButton("TL")).growX();
        row.add(t = new VisTextButton("TC")).growX();
        row.add(tr = new VisTextButton("TR")).growX();
        add(row).colspan(2).growX();
        row();
        row = new VisTable();
        row.add(l = new VisTextButton("CL")).growX();
        row.add(c = new VisTextButton("CC")).growX();
        row.add(r = new VisTextButton("CR")).growX();
        add(row).colspan(2).growX();
        row();
        row = new VisTable();
        row.add(bl = new VisTextButton("BL")).growX();
        row.add(b = new VisTextButton("BC")).growX();
        row.add(br = new VisTextButton("BR")).growX();
        add(row).colspan(2).growX();

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

        tl.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( spriteDef == null ) return;
                spriteDef.originX = 0;
                spriteDef.originY = spriteDef.region.originalHeight;
            }
        });
        t.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( spriteDef == null ) return;
                spriteDef.originX = spriteDef.region.originalWidth/2;
                spriteDef.originY = spriteDef.region.originalHeight;
            }
        });
        tr.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( spriteDef == null ) return;
                spriteDef.originX = spriteDef.region.originalWidth;
                spriteDef.originY = spriteDef.region.originalHeight;
            }
        });
        l.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( spriteDef == null ) return;
                spriteDef.originX = 0;
                spriteDef.originY = spriteDef.region.originalHeight/2;
            }
        });
        c.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( spriteDef == null ) return;
                spriteDef.originX = spriteDef.region.originalWidth/2;
                spriteDef.originY = spriteDef.region.originalHeight/2;
            }
        });
        r.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( spriteDef == null ) return;
                spriteDef.originX = spriteDef.region.originalWidth;
                spriteDef.originY = spriteDef.region.originalHeight/2;
            }
        });
        bl.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( spriteDef == null ) return;
                spriteDef.originX = 0;
                spriteDef.originY = 0;
            }
        });
        b.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( spriteDef == null ) return;
                spriteDef.originX = spriteDef.region.originalWidth/2;
                spriteDef.originY = 0;
            }
        });
        br.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( spriteDef == null ) return;
                spriteDef.originX = spriteDef.region.originalWidth;
                spriteDef.originY = 0;
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
            xSpinner.setModel(new IntSpinnerModel(0, -5000, 5000, 5));
            xSpinner.addListener(l);

            l = ySpinner.getListeners().first();
            ySpinner.removeListener(l);
            ySpinner.setModel(new IntSpinnerModel(0, -5000, 5000, 5));
            ySpinner.addListener(l);

            ((IntSpinnerModel) xSpinner.getModel()).setValue(spriteDef.originX);
            ((IntSpinnerModel) ySpinner.getModel()).setValue(spriteDef.originY);
        }
    }
}
