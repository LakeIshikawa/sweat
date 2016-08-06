package com.lksoft.yugen.tools.frameeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.util.adapter.AbstractListAdapter;
import com.kotcrab.vis.ui.widget.*;
import com.lksoft.yugen.stateless.Frame;
import com.lksoft.yugen.stateless.FramePack;

import java.util.Comparator;

/**
 * Created by Lake on 04/08/2016.
 */
public class FramePackWindow extends VisWindow {

    // Editor screen
    private FrameEditorScreen editorScreen;

    // Frame list
    private ListView<Frame> frameList;

    /**
     * Create a new animations window
     * @param frameEditorScreen
     */
    public FramePackWindow(FrameEditorScreen frameEditorScreen) {
        super("FramePack");
        this.editorScreen = frameEditorScreen;
        TableUtils.setSpacingDefaults(this);

        VisTextButton addButton = new VisTextButton("+");
        addButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.addFrame();
            }
        });
        add(addButton).growX();

        VisTextButton removeButton = new VisTextButton("-");
        removeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array selection = getAdapter().getSelectionManager().getSelection();
                if( selection.size > 0 ) {
                    editorScreen.removeFrame((Frame) selection.first());
                }
            }
        });
        add(removeButton).growX();
        row();

        frameList = new ListView<>(new FramePackAdapter(null));
        frameList.setItemClickListener(new ListView.ItemClickListener<Frame>() {
            @Override
            public void clicked(Frame item) {
                editorScreen.selectFrame(item);
            }
        });
        add(frameList.getMainTable()).colspan(3).grow();
    }

    /**
     * Sets the frame pack
     * @param pack
     */
    public void setFramePack(FramePack pack){
        getAdapter().setPack(pack);
    }

    /**
     * Set selected frame
     * @param selected
     */
    public void setSelected(Frame selected) {
        getAdapter().getSelectionManager().deselectAll();
        if( selected != null ){
            getAdapter().getSelectionManager().select(selected);
        }
    }

    /**
     * Adds a frame to the pack
     * @param frame
     */
    public void addFrame(Frame frame) {
        getAdapter().add(frame);
    }

    /**
     * Removes a frame from the pack
     * @param frame
     */
    public void removeFrame(Frame frame) {
        getAdapter().remove(frame);
    }

    /**
     * @return Current frame  pack
     */
    public FramePack getFramePack() {
        return getAdapter().pack;
    }

    /**
     * @return Current frame
     */
    public Frame getSelected() {
        return getAdapter().getSelection().first();
    }


    // Gets list adapter
    private FramePackAdapter getAdapter(){
        return (FramePackAdapter) frameList.getAdapter();
    }

    /**
     * ListView adapter for frame pack
     */
    private class FramePackAdapter extends AbstractListAdapter<Frame, VisTable> {
        private final Drawable bg = VisUI.getSkin().getDrawable("window-bg");
        private final Drawable selection = VisUI.getSkin().getDrawable("list-selection");

        // Backing pack
        private FramePack pack;
        // Sorted array
        private Array<Frame> sorted = new Array<>();

        public FramePackAdapter(FramePack pack) {
            this.pack = pack;
            if( pack != null ) sorted = pack.getFrames();
            setSelectionMode(SelectionMode.SINGLE);

            setItemsSorter(new Comparator<Frame>() {
                @Override
                public int compare (Frame o1, Frame o2) {
                    return o1.region.name.toLowerCase().compareTo(o2.region.name.toLowerCase());
                }
            });
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

        @Override
        protected void sort(Comparator<Frame> comparator) {
            if( pack == null ) return;
            sorted.sort(comparator);
        }

        @Override
        public Iterable<Frame> iterable() {
            return sorted;
        }

        @Override
        public int size() {
            return pack == null ? 0 : pack.getFrames().size;
        }

        @Override
        public int indexOf(Frame item) {
            return pack.getFrames().indexOf(item, true);
        }

        @Override
        public void add(Frame item) {
            // No overwriting
            if( pack == null || pack.getFrame(item.region.name) != null ) {
                setSelected(pack.getFrame(item.region.name));
                return;
            }

            pack.addFrame(item);
            sorted.add(item);
            itemsChanged();
        }

        @Override
        public Frame get(int index) {
            return pack.getFrames().get(index);
        }

        /**
         * Remove frame
         * @param item
         */
        public void remove(Frame item){
            if( pack == null ) return;
            pack.removeFrame(item.region.name);
            sorted.removeValue(item, true);
            itemsChanged();
        }

        /**
         * Sets a frame pack
         */
        public void setPack(FramePack pack) {
            this.pack = pack;
            if( pack != null ) sorted = pack.getFrames();
            itemsChanged();
        }
    }
}
