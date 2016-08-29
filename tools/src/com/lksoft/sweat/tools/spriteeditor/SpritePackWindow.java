package com.lksoft.sweat.tools.spriteeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.util.adapter.AbstractListAdapter;
import com.kotcrab.vis.ui.widget.*;
import com.lksoft.sweat.stateless.SpriteDef;
import com.lksoft.sweat.stateless.SpritePack;

import java.util.Comparator;

/**
 * Created by Lake on 04/08/2016.
 */
public class SpritePackWindow extends VisWindow {

    // Editor screen
    private SpriteEditorScreen editorScreen;

    // SpriteDef list
    private ListView<SpriteDef> frameList;

    /**
     * Create a new animations window
     * @param spriteEditorScreen
     */
    public SpritePackWindow(SpriteEditorScreen spriteEditorScreen) {
        super("Sprites");
        this.editorScreen = spriteEditorScreen;
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
                    editorScreen.removeFrame((SpriteDef) selection.first());
                }
            }
        });
        add(removeButton).growX();
        row();

        frameList = new ListView<>(new FramePackAdapter(null));
        frameList.setItemClickListener(new ListView.ItemClickListener<SpriteDef>() {
            @Override
            public void clicked(SpriteDef item) {
                editorScreen.selectFrame(item);
            }
        });
        add(frameList.getMainTable()).colspan(3).grow();
    }

    /**
     * Sets the spriteDef pack
     * @param pack
     */
    public void setFramePack(SpritePack pack){
        getAdapter().setPack(pack);
    }

    /**
     * Set selected spriteDef
     * @param selected
     */
    public void setSelected(SpriteDef selected) {
        getAdapter().getSelectionManager().deselectAll();
        if( selected != null ){
            getAdapter().getSelectionManager().select(selected);
        }
    }

    /**
     * Adds a spriteDef to the pack
     * @param spriteDef
     */
    public void addFrame(SpriteDef spriteDef) {
        getAdapter().add(spriteDef);
    }

    /**
     * Removes a spriteDef from the pack
     * @param spriteDef
     */
    public void removeFrame(SpriteDef spriteDef) {
        getAdapter().remove(spriteDef);
    }

    /**
     * @return Current spriteDef  pack
     */
    public SpritePack getFramePack() {
        return getAdapter().pack;
    }

    /**
     * @return Current spriteDef
     */
    public SpriteDef getSelected() {
        return getAdapter().getSelection().first();
    }


    // Gets list adapter
    private FramePackAdapter getAdapter(){
        return (FramePackAdapter) frameList.getAdapter();
    }

    /**
     * ListView adapter for spriteDef pack
     */
    private class FramePackAdapter extends AbstractListAdapter<SpriteDef, VisTable> {
        private final Drawable bg = VisUI.getSkin().getDrawable("window-bg");
        private final Drawable selection = VisUI.getSkin().getDrawable("list-selection");

        // Backing pack
        private SpritePack pack;
        // Sorted array
        private Array<SpriteDef> sorted = new Array<>();

        public FramePackAdapter(SpritePack pack) {
            this.pack = pack;
            if( pack != null ) sorted = pack.getSpriteDefs();
            setSelectionMode(SelectionMode.SINGLE);

            setItemsSorter(new Comparator<SpriteDef>() {
                @Override
                public int compare (SpriteDef o1, SpriteDef o2) {
                    return o1.region.name.toLowerCase().compareTo(o2.region.name.toLowerCase());
                }
            });
        }

        @Override
        protected VisTable createView(SpriteDef item) {
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
        protected void sort(Comparator<SpriteDef> comparator) {
            if( pack == null ) return;
            sorted.sort(comparator);
        }

        @Override
        public Iterable<SpriteDef> iterable() {
            return sorted;
        }

        @Override
        public int size() {
            return pack == null ? 0 : pack.getSpriteDefs().size;
        }

        @Override
        public int indexOf(SpriteDef item) {
            return pack.getSpriteDefs().indexOf(item, true);
        }

        @Override
        public void add(SpriteDef item) {
            // No overwriting
            if( pack == null || pack.getSpriteDef(item.region.name) != null ) {
                setSelected(pack.getSpriteDef(item.region.name));
                return;
            }

            pack.addSpriteDef(item);
            sorted.add(item);
            itemsChanged();
        }

        @Override
        public SpriteDef get(int index) {
            return pack.getSpriteDefs().get(index);
        }

        /**
         * Remove spriteDef
         * @param item
         */
        public void remove(SpriteDef item){
            if( pack == null ) return;
            pack.removeSpriteDef(item.region.name);
            sorted.removeValue(item, true);
            itemsChanged();
        }

        /**
         * Sets a spriteDef pack
         */
        public void setPack(SpritePack pack) {
            this.pack = pack;
            if( pack != null ) sorted = pack.getSpriteDefs();
            itemsChanged();
        }
    }
}
