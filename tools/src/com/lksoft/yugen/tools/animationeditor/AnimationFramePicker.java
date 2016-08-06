package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Selection;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.adapter.ArrayAdapter;
import com.kotcrab.vis.ui.widget.*;
import com.lksoft.yugen.stateless.Frame;
import com.lksoft.yugen.stateless.Frames;

/**
 * Created by Lake on 06/08/2016.
 */
public class AnimationFramePicker extends VisWindow {

    // GUI components
    private VisImage icon;

    /**
     * Creates an animation picker
     */
    public AnimationFramePicker(Frames frames, final PickListener listener) {
        super("Pick frame");
        setModal(true);

        icon = new VisImage();

        final ListView<Frame> frameList = new ListView<>(new FramesAdapter(frames.getFrames()));
        frameList.setItemClickListener(new ListView.ItemClickListener<Frame>() {
            @Override
            public void clicked(Frame item) {
                icon.setDrawable(new TextureRegionDrawable(item.region));
            }
        });
        add(frameList.getMainTable()).grow();
        add(icon);
        row();

        center();

        VisTable buttonTable = new VisTable(true);
        VisTextButton choose = new VisTextButton("Choose");
        choose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<Frame> selection = ((ArrayAdapter)frameList.getAdapter()).getSelection();
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
        add(buttonTable).colspan(2).grow();

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

    // Adapter for Frame array
    private class FramesAdapter extends ArrayAdapter<Frame, VisTable> {
        private final Drawable bg = VisUI.getSkin().getDrawable("window-bg");
        private final Drawable selection = VisUI.getSkin().getDrawable("list-selection");

        // Create adapter
        public FramesAdapter(Array<Frame> frames) {
            super(frames);
            setSelectionMode(SelectionMode.SINGLE);
        }

        @Override
        protected VisTable createView(Frame item) {
            VisLabel label = new VisLabel(item.region.name);

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

        void onFramePicked(Frame frame);
        void onCancel();
    }
}
