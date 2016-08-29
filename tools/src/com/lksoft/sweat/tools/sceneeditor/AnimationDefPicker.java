package com.lksoft.sweat.tools.sceneeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.adapter.ArrayAdapter;
import com.kotcrab.vis.ui.widget.*;
import com.lksoft.sweat.stateless.AnimationDef;
import com.lksoft.sweat.stateless.AnimationPack;

/**
 * Created by Lake on 06/08/2016.
 */
public class AnimationDefPicker extends VisWindow {

    // GUI components
    private VisImage icon;

    /**
     * Creates an animation picker
     */
    public AnimationDefPicker(final AnimationPack pack, final PickListener listener) {
        super("Pick animation");
        setModal(true);

        icon = new VisImage();

        final ListView<AnimationDef> animList = new ListView<>(new AnimationsDefAdapter(pack.getAnimations()));
        animList.setItemClickListener(new ListView.ItemClickListener<AnimationDef>() {
            @Override
            public void clicked(AnimationDef item) {
                icon.setDrawable(new TextureRegionDrawable(item.getFrameAt(0).components.get(0).spriteDef.region));
            }
        });

        // Content table
        VisTable table = new VisTable(true);
        table.left();
        table.add(animList.getMainTable()).width(200).growY();
        table.add(icon).expand();

        add(table).grow();
        row();

        VisTable buttonTable = new VisTable(true);
        VisTextButton choose = new VisTextButton("Choose");
        choose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<AnimationDef> selection = ((ArrayAdapter)animList.getAdapter()).getSelection();
                if(selection.size > 0) {
                    listener.onFramePicked(selection.first());
                }
                close();
            }
        });
        VisTextButton cancel = new VisTextButton("Cancel");
        cancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                close();
                listener.onCancel();
            }
        });

        buttonTable.add(choose);
        buttonTable.add(cancel);
        add(buttonTable).growX();

        setSize(500, 400);
    }

    /**
     * Show as a dialog
     * @param stage
     */
    public void show(Stage stage) {
        setPosition(stage.getViewport().getWorldWidth()/2 - getWidth()/2, stage.getViewport().getWorldHeight()/2 - getHeight()/2);
        stage.addActor(this);
    }

    // Adapter for SpriteDef array
    private class AnimationsDefAdapter extends ArrayAdapter<AnimationDef, VisTable> {
        private final Drawable bg = VisUI.getSkin().getDrawable("window-bg");
        private final Drawable selection = VisUI.getSkin().getDrawable("list-selection");

        // Create adapter
        public AnimationsDefAdapter(Array<AnimationDef> frames) {
            super(frames);
            setSelectionMode(SelectionMode.SINGLE);
        }

        @Override
        protected VisTable createView(AnimationDef item) {
            VisLabel label = new VisLabel(item.getName());

            VisTable table = new VisTable();
            table.left();
            table.add(label);
            return table;
        }

        @Override
        protected void selectView (VisTable view) {
            if( view != null ) {
                view.setBackground(selection);
            }
        }

        @Override
        protected void deselectView (VisTable view) {
            if( view != null ) {
                view.setBackground(bg);
            }
        }
    }

    // Pick listener interface
    public interface PickListener {
        void onFramePicked(AnimationDef frame);
        void onCancel();
    }
}
