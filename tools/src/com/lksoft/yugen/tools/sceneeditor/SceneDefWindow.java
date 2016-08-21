package com.lksoft.yugen.tools.sceneeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.util.adapter.AbstractListAdapter;
import com.kotcrab.vis.ui.widget.*;
import com.lksoft.yugen.stateless.SceneDef;

import java.util.Comparator;

/**
 * Created by Lake on 05/08/2016.
 */
public class SceneDefWindow extends VisWindow {

    // Editor screen
    private SceneEditorScreen editorScreen;

    // Components list
    private ListView<SceneDef.SceneFsmDef> componentListView;

    /**
     * Create a new fsm def list window
     * @param screen
     */
    public SceneDefWindow(SceneEditorScreen screen) {
        super("Fsm");
        this.editorScreen = screen;
        TableUtils.setSpacingDefaults(this);

        VisTextButton addButton = new VisTextButton("+");
        addButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editorScreen.addFsm();
            }
        });
        VisTextButton removeButton = new VisTextButton("-");
        removeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<SceneDef.SceneFsmDef> selection = getAdapter().getSelectionManager().getSelection();
                if( selection.size == 0 ) return;

                editorScreen.removeFsm(selection.first());
            }
        });

        VisTextButton mvUp = new VisTextButton("Mv up");
        mvUp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<SceneDef.SceneFsmDef> selection = getAdapter().getSelectionManager().getSelection();
                if( selection.size == 0 ) return;

                getAdapter().moveUp(selection.first());
            }
        });

        VisTextButton mvDown = new VisTextButton("Mv down");
        mvDown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<SceneDef.SceneFsmDef> selection = getAdapter().getSelectionManager().getSelection();
                if( selection.size == 0 ) return;

                getAdapter().moveDown(selection.first());
            }
        });

        add(addButton).growX();
        add(removeButton).growX();
        add(mvUp).growX();
        add(mvDown).growX();
        row();

        componentListView = new ListView<>(new SceneAdapter(null));
        componentListView.setItemClickListener(new ListView.ItemClickListener<SceneDef.SceneFsmDef>() {
            @Override
            public void clicked(SceneDef.SceneFsmDef item) {
                editorScreen.select(item);
            }
        });
        add(componentListView.getMainTable()).colspan(3).grow();
    }

    /**
     * Sets the animation frame
     * @param sceneDef
     */
    public void setSceneDef(SceneDef sceneDef){
        getAdapter().setFrame(sceneDef);
    }

    /**
     * Set selected component
     * @param selected
     */
    public void setSelected(SceneDef.SceneFsmDef selected) {
        getAdapter().getSelectionManager().deselectAll();

        if( selected != null ) {
            getAdapter().getSelectionManager().select(selected);
        }
    }

    /**
     * @return Currently selected fsm
     */
    public SceneDef.SceneFsmDef getSelectedComponent() {
        if( getAdapter().getSelection().size == 0 ) return null;
        return getAdapter().getSelection().first();
    }

    /**
     * Adds a new fsm to current position
     * @param component
     */
    public void addFsm(SceneDef.SceneFsmDef component) {
        getAdapter().add(component);
    }

    /**
     * Remove the specified fsm
     */
    public void removeFsm(SceneDef.SceneFsmDef component) {
        getAdapter().remove(component);
    }

    /**
     * @return Current scene def
     */
    public SceneDef getSceneDef(){
        return getAdapter().scene;
    }

    // Gets the list adapter
    private SceneAdapter getAdapter(){
        return (SceneAdapter) componentListView.getAdapter();
    }

    // Updates view
    public void update() {
        setSceneDef(getSceneDef());
    }

    /**
     * ListView adapter for animation frame
     */
    private class SceneAdapter extends AbstractListAdapter<SceneDef.SceneFsmDef, VisTable> {
        private final Drawable bg = VisUI.getSkin().getDrawable("window-bg");
        private final Drawable selection = VisUI.getSkin().getDrawable("list-selection");

        // Backing sceneDef
        private SceneDef scene;

        public SceneAdapter(SceneDef scene) {
            this.scene = scene;
            setSelectionMode(SelectionMode.SINGLE);
        }

        @Override
        protected VisTable createView(SceneDef.SceneFsmDef item) {
            VisLabel label = new VisLabel(item.getScriptFileName() + "-" + item.animation);
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
        protected void sort(Comparator<SceneDef.SceneFsmDef> comparator) {}

        @Override
        public Iterable<SceneDef.SceneFsmDef> iterable() {
            if( scene == null ) return new Array<>();
            return scene.layout;
        }

        @Override
        public int size() {
            return scene == null ? 0 : scene.layout.size;
        }

        @Override
        public int indexOf(SceneDef.SceneFsmDef item) {
            return scene.layout.indexOf(item, true);
        }

        @Override
        public void add(SceneDef.SceneFsmDef item) {
            if( scene == null ) return;
            scene.layout.add(item);
            itemsChanged();
        }

        @Override
        public SceneDef.SceneFsmDef get(int index) {
            return scene.layout.get(index);
        }

        /**
         * Remove fsm
         * @param item
         */
        public void remove(SceneDef.SceneFsmDef item){
            if( scene == null ) return;
            scene.layout.removeValue(item, true);
            itemsChanged();
        }

        /**
         * Sets the scene
         */
        public void setFrame(SceneDef scene) {
            this.scene = scene;
            itemsChanged();
        }

        /**
         * Moves specified component up in the order
         * @param fsm
         */
        public void moveUp(SceneDef.SceneFsmDef fsm) {
            int index = scene.layout.indexOf(fsm, true);
            if( index == 0 ) return;

            scene.layout.swap(index, index-1);
            itemsChanged();
            setSelected(fsm);
        }

        /**
         * Moves specified fsm down in the order
         * @param fsm
         */
        public void moveDown(SceneDef.SceneFsmDef fsm) {
            int index = scene.layout.indexOf(fsm, true);
            if( index == scene.layout.size -1 ) return;

            scene.layout.swap(index, index+1);
            itemsChanged();
            setSelected(fsm);
        }
    }
}
