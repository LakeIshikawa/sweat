package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.util.adapter.AbstractListAdapter;
import com.kotcrab.vis.ui.widget.*;
import com.lksoft.yugen.stateless.AnimationDef;
import com.lksoft.yugen.stateless.AnimationPack;

import java.util.Comparator;

/**
 * Created by Lake on 04/08/2016.
 */
public class AnimationPackWindow extends VisWindow {

    // Editor screen
    private AnimationEditorScreen editorScreen;

    // Animdef list
    private ListView<AnimationDef> animationDefList;

    /**
     * Create a new animations window
     * @param animationEditorScreen
     */
    public AnimationPackWindow(AnimationEditorScreen animationEditorScreen) {
        super("Animations");
        this.editorScreen = animationEditorScreen;
        TableUtils.setSpacingDefaults(this);

        final VisTextField nameField = new VisTextField();
        add(nameField).growX();

        VisTextButton addButton = new VisTextButton("+");
        addButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.addAnimationDef(nameField.getText());
                nameField.setText("");
            }
        });
        add(addButton);

        VisTextButton removeButton = new VisTextButton("-");
        removeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array selection = getAdapter().getSelectionManager().getSelection();
                if( selection.size > 0 ) {
                    editorScreen.removeAnimationDef((AnimationDef) selection.first());
                }
            }
        });
        add(removeButton);
        row();

        animationDefList = new ListView<>(new AnimationPackAdapter(null));
        animationDefList.setItemClickListener(new ListView.ItemClickListener<AnimationDef>() {
            @Override
            public void clicked(AnimationDef item) {
                editorScreen.selectAnimationDef(item);
            }
        });
        add(animationDefList.getMainTable()).colspan(3).grow();
    }

    /**
     * Sets the animation pack
     * @param pack
     */
    public void setAnimationPack(AnimationPack pack){
        getAdapter().setPack(pack);
    }

    /**
     * Set selected animation def
     * @param selected
     */
    public void setSelected(AnimationDef selected) {
        getAdapter().getSelectionManager().deselectAll();
        if( selected != null ){
            getAdapter().getSelectionManager().select(selected);
        }
    }

    /**
     * Adds an animation def to the pack
     * @param newDef
     */
    public void addAnimationDef(AnimationDef newDef) {
        getAdapter().add(newDef);
    }

    /**
     * Removes an animation def from the pack
     * @param newDef
     */
    public void removeAnimationDef(AnimationDef newDef) {
        getAdapter().remove(newDef);
    }

    /**
     * @return Current animation pack
     */
    public AnimationPack getAnimationPack() {
        return getAdapter().pack;
    }

    // Gets list adapter
    private AnimationPackAdapter getAdapter(){
        return (AnimationPackAdapter) animationDefList.getAdapter();
    }

    /**
     * ListView adapter for animationpack
     */
    private class AnimationPackAdapter extends AbstractListAdapter<AnimationDef, VisTable> {
        private final Drawable bg = VisUI.getSkin().getDrawable("window-bg");
        private final Drawable selection = VisUI.getSkin().getDrawable("list-selection");

        // Backing pack
        private AnimationPack pack;
        // Sorted array
        private Array<AnimationDef> sorted = new Array<>();

        public AnimationPackAdapter(AnimationPack pack) {
            this.pack = pack;
            if( pack != null ) sorted = pack.getAnimations();
            setSelectionMode(SelectionMode.SINGLE);

            setItemsSorter(new Comparator<AnimationDef>() {
                @Override
                public int compare (AnimationDef o1, AnimationDef o2) {
                    return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                }
            });
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

        @Override
        protected void sort(Comparator<AnimationDef> comparator) {
            if( pack == null ) return;
            sorted.sort(comparator);
        }

        @Override
        public Iterable<AnimationDef> iterable() {
            return sorted;
        }

        @Override
        public int size() {
            return pack == null ? 0 : pack.getAnimations().size;
        }

        @Override
        public int indexOf(AnimationDef item) {
            return pack.getAnimations().indexOf(item, true);
        }

        @Override
        public void add(AnimationDef item) {
            // No overwriting
            if( pack == null || pack.getAnimationDef(item.getName()) != null ) {
                setSelected(pack.getAnimationDef(item.getName()));
                return;
            }
            pack.addAnimationDef(item);
            sorted.add(item);
            itemsChanged();
        }

        @Override
        public AnimationDef get(int index) {
            return sorted.get(index);
        }

        /**
         * Remove animation
         * @param item
         */
        public void remove(AnimationDef item){
            if( pack == null ) return;
            pack.removeAnimationDef(item.getName());
            sorted.removeValue(item, true);
            itemsChanged();
        }

        /**
         * Sets an animation pack
         */
        public void setPack(AnimationPack pack) {
            this.pack = pack;
            if( pack != null ) sorted = pack.getAnimations();
            itemsChanged();
        }
    }
}
