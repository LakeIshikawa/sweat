package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.util.adapter.AbstractListAdapter;
import com.kotcrab.vis.ui.widget.*;
import com.lksoft.yugen.stateless.AnimationFrame;

import java.util.Comparator;

/**
 * Created by Lake on 05/08/2016.
 */
public class AnimationFrameComponentWindow extends VisWindow {

    // Editor screen
    private AnimationEditorScreen editorScreen;

    // Components list
    private ListView<AnimationFrame.Component> componentListView;

    /**
     * Create a new region window
     * @param animationEditorScreen
     */
    public AnimationFrameComponentWindow(AnimationEditorScreen animationEditorScreen) {
        super("Components");
        this.editorScreen = animationEditorScreen;
        TableUtils.setSpacingDefaults(this);

        VisTextButton addButton = new VisTextButton("+");
        addButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.addComponent();
            }
        });
        VisTextButton removeButton = new VisTextButton("-");
        removeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<AnimationFrame.Component> selection = getAdapter().getSelectionManager().getSelection();
                if( componentListView.getAdapter().size() <= 1 ) return;
                editorScreen.removeComponent(selection.first());
            }
        });

        VisTextButton mvUp = new VisTextButton("Mv up");
        mvUp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<AnimationFrame.Component> selection = getAdapter().getSelectionManager().getSelection();
                if( selection.size == 0 ) return;

                getAdapter().moveUp(selection.first());
            }
        });

        VisTextButton mvDown = new VisTextButton("Mv down");
        mvDown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<AnimationFrame.Component> selection = getAdapter().getSelectionManager().getSelection();
                if( selection.size == 0 ) return;

                getAdapter().moveDown(selection.first());
            }
        });

        add(addButton).growX();
        add(removeButton).growX();
        add(mvUp).growX();
        add(mvDown).growX();
        row();

        componentListView = new ListView<>(new AnimationFrameAdapter(null));
        componentListView.setItemClickListener(new ListView.ItemClickListener<AnimationFrame.Component>() {
            @Override
            public void clicked(AnimationFrame.Component item) {
                editorScreen.selectComponent(item);
            }
        });
        add(componentListView.getMainTable()).colspan(3).grow();
    }

    /**
     * Sets the animation frame
     * @param animationFrame
     */
    public void setAnimationFrame(AnimationFrame animationFrame){
        getAdapter().setFrame(animationFrame);
    }

    /**
     * Set selected component
     * @param selected
     */
    public void setSelected(AnimationFrame.Component selected) {
        getAdapter().getSelectionManager().deselectAll();

        if( selected != null ) {
            getAdapter().getSelectionManager().select(selected);
        }
    }

    /**
     * @return Currently selected component
     */
    public AnimationFrame.Component getSelectedComponent() {
        if( getAdapter().getSelection().size == 0 ) return null;
        return getAdapter().getSelection().first();
    }

    /**
     * Adds a new component to current position
     * @param component
     */
    public void addComponent(AnimationFrame.Component component) {
        getAdapter().add(component);
    }

    /**
     * Remove the specified component
     */
    public void removeComponent(AnimationFrame.Component component) {
        getAdapter().remove(component);
    }

    /**
     * @return Current animation frame
     */
    public AnimationFrame getAnimationFrame(){
        return getAdapter().frame;
    }

    // Gets the list adapter
    private AnimationFrameAdapter getAdapter(){
        return (AnimationFrameAdapter) componentListView.getAdapter();
    }

    /**
     * ListView adapter for animation frame
     */
    private class AnimationFrameAdapter extends AbstractListAdapter<AnimationFrame.Component, VisTable> {
        private final Drawable bg = VisUI.getSkin().getDrawable("window-bg");
        private final Drawable selection = VisUI.getSkin().getDrawable("list-selection");

        // Backing spriteDef
        private AnimationFrame frame;

        public AnimationFrameAdapter(AnimationFrame frame) {
            this.frame = frame;
            setSelectionMode(SelectionMode.SINGLE);
        }

        @Override
        protected VisTable createView(AnimationFrame.Component item) {
            VisLabel label = new VisLabel(item.spriteDef.region.name);
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
        protected void sort(Comparator<AnimationFrame.Component> comparator) {

        }

        @Override
        public Iterable<AnimationFrame.Component> iterable() {
            if( frame == null ) return new Array<>();
            return frame.components;
        }

        @Override
        public int size() {
            return frame == null ? 0 : frame.components.size;
        }

        @Override
        public int indexOf(AnimationFrame.Component item) {
            return frame.components.indexOf(item, true);
        }

        @Override
        public void add(AnimationFrame.Component item) {
            if( frame == null ) return;
            frame.components.add(item);
            itemsChanged();
        }

        @Override
        public AnimationFrame.Component get(int index) {
            return frame.components.get(index);
        }

        /**
         * Remove animation
         * @param item
         */
        public void remove(AnimationFrame.Component item){
            if( frame == null ) return;
            frame.components.removeValue(item, true);
            itemsChanged();
        }

        /**
         * Sets the frame
         */
        public void setFrame(AnimationFrame frame) {
            this.frame = frame;
            itemsChanged();
        }

        /**
         * Moves specified component up in the order
         * @param component
         */
        public void moveUp(AnimationFrame.Component component) {
            int index = frame.components.indexOf(component, true);
            if( index == 0 ) return;

            frame.components.swap(index, index-1);
            itemsChanged();
            setSelected(component);
        }

        /**
         * Moves specified frame down in the order
         * @param region
         */
        public void moveDown(AnimationFrame.Component region) {
            int index = frame.components.indexOf(region, true);
            if( index == frame.components.size -1 ) return;

            frame.components.swap(index, index+1);
            itemsChanged();
            setSelected(region);
        }
    }
}
