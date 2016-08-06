package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.util.adapter.AbstractListAdapter;
import com.kotcrab.vis.ui.widget.*;
import com.lksoft.yugen.stateless.AnimationDef;
import com.lksoft.yugen.stateless.AnimationFrame;

import java.util.Comparator;

/**
 * Created by Lake on 05/08/2016.
 */
public class AnimationFrameWindow extends VisWindow {

    // Editor screen
    private AnimationEditorScreen editorScreen;

    // Animdef list
    private ListView<AnimationFrame> animationFrameList;

    /**
     * Create a new animations window
     * @param animationEditorScreen
     */
    public AnimationFrameWindow(AnimationEditorScreen animationEditorScreen) {
        super("FramePack");
        this.editorScreen = animationEditorScreen;
        TableUtils.setSpacingDefaults(this);

        VisTextButton addButton = new VisTextButton("+");
        addButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.addFrame();
            }
        });
        VisTextButton removeButton = new VisTextButton("-");
        removeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<AnimationFrame> selection = getAdapter().getSelectionManager().getSelection();
                if( selection.size == 0 ) return;
                editorScreen.removeFrame(selection.first());
            }
        });

        VisTextButton mvUp = new VisTextButton("Mv up");
        mvUp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<AnimationFrame> selection = getAdapter().getSelectionManager().getSelection();
                if( selection.size == 0 ) return;

                getAdapter().moveUp(selection.first());
            }
        });

        VisTextButton mvDown = new VisTextButton("Mv down");
        mvDown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<AnimationFrame> selection = getAdapter().getSelectionManager().getSelection();
                if( selection.size == 0 ) return;

                getAdapter().moveDown(selection.first());
            }
        });

        VisTextButton loopStart = new VisTextButton("Loop Start");
        loopStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<AnimationFrame> selection = getAdapter().getSelectionManager().getSelection();
                if( selection.size == 0 ) return;

                getAdapter().setLoopStart(selection.first());
            }
        });

        add(addButton).growX();
        add(removeButton).growX();
        add(mvUp).growX();
        add(mvDown).growX();
        add(loopStart).growX();
        row();

        animationFrameList = new ListView<>(new AnimationDefAdapter(null));
        animationFrameList.setItemClickListener(new ListView.ItemClickListener<AnimationFrame>() {
            @Override
            public void clicked(AnimationFrame item) {
                editorScreen.selectFrame(item);
            }
        });
        add(animationFrameList.getMainTable()).colspan(3).grow();
    }

    /**
     * Sets the animation def
     * @param animationDef
     */
    public void setAnimationDef(AnimationDef animationDef){
        getAdapter().setAnimationDef(animationDef);
    }

    /**
     * Set selected animation def
     * @param selected
     */
    public void setSelected(AnimationFrame selected) {
        getAdapter().getSelectionManager().deselectAll();

        if( selected != null ) {
            getAdapter().getSelectionManager().select(selected);
        }
    }

    /**
     * @return Currently selected frame
     */
    public AnimationFrame getSelectedFrame() {
        if( getAdapter().size() == 0 ) return null;
        return getAdapter().getSelection().first();
    }

    /**
     * @return Frame next to currently selected
     */
    public AnimationFrame getNextFrame() {
        AnimationFrame current = getSelectedFrame();
        int index = getAnimationDef().getFrames().indexOf(current, true);
        index++;
        if( index >= getAnimationDef().getFrames().size ){
            index = getAnimationDef().getLoopStartPos();
        }

        return getAnimationDef().getFrames().get(index);
    }

    /**
     * Adds a new frame to current position
     * @param newFrame
     */
    public void addFrame(AnimationFrame newFrame) {
        getAdapter().add(newFrame);
    }

    /**
     * Remove the specified frame
     */
    public void removeFrame(AnimationFrame frame) {
        getAdapter().remove(frame);
    }

    /**
     * @return Current animation def
     */
    public AnimationDef getAnimationDef(){
        return getAdapter().animationDef;
    }

    // Gets the list adapter
    private AnimationDefAdapter getAdapter(){
        return (AnimationDefAdapter) animationFrameList.getAdapter();
    }

    /**
     * ListView adapter for animationpack
     */
    private class AnimationDefAdapter extends AbstractListAdapter<AnimationFrame, VisTable> {
        private final Drawable bg = VisUI.getSkin().getDrawable("window-bg");
        private final Drawable selection = VisUI.getSkin().getDrawable("list-selection");

        // Backing frame
        private AnimationDef animationDef;

        public AnimationDefAdapter(AnimationDef def) {
            this.animationDef = def;
            setSelectionMode(SelectionMode.SINGLE);
        }

        @Override
        protected VisTable createView(AnimationFrame item) {
            VisLabel label = new VisLabel(item.frame.region.name);
            // Loop start
            if( animationDef.getFrames().indexOf(item, true) == animationDef.getLoopStartPos()) {
                label.setColor(Color.GREEN);
            } else {
                label.setColor(Color.WHITE);
            }

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
        protected void sort(Comparator<AnimationFrame> comparator) {

        }

        @Override
        public Iterable<AnimationFrame> iterable() {
            if( animationDef == null ) return new Array<>();
            return animationDef.getFrames();
        }

        @Override
        public int size() {
            return animationDef == null ? 0 : animationDef.getFrames().size;
        }

        @Override
        public int indexOf(AnimationFrame item) {
            return animationDef.getFrames().indexOf(item, true);
        }

        @Override
        public void add(AnimationFrame item) {
            if( animationDef == null ) return;
            animationDef.addFrame(item);
            itemsChanged();
        }

        @Override
        public AnimationFrame get(int index) {
            return animationDef.getFrames().get(index);
        }

        /**
         * Remove animation
         * @param item
         */
        public void remove(AnimationFrame item){
            if( animationDef == null ) return;
            int index = animationDef.getFrames().indexOf(item, true);
            animationDef.getFrames().removeValue(item, true);

            if( animationDef.getLoopStartPos() == index ){
                animationDef.setLoopStartPos(0);
            } else if( animationDef.getLoopStartPos() > index ){
                animationDef.setLoopStartPos(animationDef.getLoopStartPos()-1);
            }
            itemsChanged();
        }

        /**
         * Sets the animation def
         */
        public void setAnimationDef(AnimationDef def) {
            this.animationDef = def;
            itemsChanged();
        }

        /**
         * Moves specified frame up in the order
         * @param frame
         */
        public void moveUp(AnimationFrame frame) {
            int index = animationDef.getFrames().indexOf(frame, true);
            if( index == 0 ) return;

            animationDef.getFrames().swap(index, index-1);
            itemsChanged();
            setSelected(frame);
        }

        /**
         * Moves specified frame down in the order
         * @param frame
         */
        public void moveDown(AnimationFrame frame) {
            int index = animationDef.getFrames().indexOf(frame, true);
            if( index == animationDef.getFrames().size -1 ) return;

            animationDef.getFrames().swap(index, index+1);
            itemsChanged();
            setSelected(frame);
        }

        /**
         * Sets the loop start to specified frame
         * @param frame
         */
        public void setLoopStart(AnimationFrame frame) {
            if( animationDef == null ) return;
            animationDef.setLoopStartPos(animationDef.getFrames().indexOf(frame, true));
            itemsChanged();
            setSelected(frame);
        }
    }
}
