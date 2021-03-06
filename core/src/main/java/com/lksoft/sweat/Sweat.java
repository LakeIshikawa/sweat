package com.lksoft.sweat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.lksoft.sweat.stateful.Fsm;
import com.lksoft.sweat.stateful.NonFsm;
import com.lksoft.sweat.stateless.SceneDef;
import com.lksoft.sweat.stateless.Settings;

import java.io.IOException;

import static com.lksoft.sweat.Resources.loadFSMClass;

/**
 * Created by Lake on 15/08/2016.
 *
 * Sweat engine
 */
public class Sweat {
    public final static String SETTINGS_FILE = "settings.json";
    public static Sweat i;

    // Settings
    private Settings settings;

    // Fsms (map and layers)
    private ObjectMap<String, Fsm> fsms = new ObjectMap<>();
    private Array<Fsm>[] layers = new Array[10];

    // Viewport
    private SweatCamera camera = new SweatCamera();

    // Debug
    private boolean debug = true;
    private boolean drawRects = false;

    // Collisions
    private Rectangle f1Rect = new Rectangle();
    private Rectangle f2Rect = new Rectangle();

    /**
     * Create yugen engine
     * File "settings.def" must exist
     */
    public Sweat(boolean debug) throws IOException {
        this();
        this.debug = debug;

        // Parse settings
        settings = Settings.read(Gdx.files.internal(Resources.BIN_FOLDER + SETTINGS_FILE));

        // Load main fsm
        loadFSM(Gdx.files.internal(settings.getMainFsm()), "main");
    }

    /**
     * Create Sweat engine without loading any script
     */
    public Sweat(){
        i = this;

        // Create layers
        for( int i=0; i<layers.length; i++ ){
            layers[i] = new Array<>();
        }
    }

    /**
     * Frame update
     */
    public void update() {
        // Update fsms
        for( Fsm fsm : fsms.values() ){
            fsm.update();
        }

        // Manage collisions
        checkCollisions();

        // Debug keys
        if( debug ){
            if( Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) ){
                drawRects = !drawRects;
            }
        }
    }

    /**
     * Frame render
     */
    public void render(SpriteBatch batch) {
        for( int i=0; i<layers.length; i++ ) {
            // Render bg sprites
            for (Fsm s : layers[i]) {
                if( s.getActive() ) {
                    s.render(batch, camera);
                }
            }
        }
    }

    /**
     * Render debug
     */
    public void renderDebug(ShapeRenderer shapeRenderer) {
        if( drawRects ) {
            for (int i = 0; i < layers.length; i++) {
                // Render bg sprites
                for (Fsm fsm : layers[i]) {
                    fsm.renderCollision(shapeRenderer);
                }
            }
        }
    }

    /**
     * Load an FSM from file and register it with specified name
     * @param fsmFile File handle for .fsm file
     * @param name Registration name (use this with getFSM() to get the FSM back)
     * @throws IOException
     */
    public Fsm loadFSM(FileHandle fsmFile, String name) throws IOException {
        Class fsmClass = loadFSMClass(fsmFile);

        try {
            Fsm fsm = (Fsm) fsmClass.newInstance();
            fsm.setName(uniquefy(name));
            fsms.put(fsm.getName(), fsm);
            layers[fsm.getLayer()].add(fsm);
            return fsm;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create an empty fsm
     * @param name
     * @return
     */
    public Fsm createFSM(String name) {
        Fsm fsm = new NonFsm();
        fsm.setName(uniquefy(name));
        fsms.put(name, fsm);
        layers[fsm.getLayer()].add(fsm);
        return fsm;
    }

    /**
     * Destroy an fsm by name
     * @param name
     */
    public void destroyFSM(String name) {
        Fsm fsm = fsms.remove(name);
        layers[fsm.getLayer()].removeValue(fsm, true);
        fsm.dispose();
    }

    /**
     * Remove all fsms
     */
    public void clear() {
        for( Fsm fsm : fsms.values() ){
            destroyFSM(fsm.getName());
        }
    }

    /**
     * Gets an fsm
     * @param name
     * @return
     */
    public Fsm getFSM(String name) {
        return fsms.get(name);
    }

    /**
     * Load a scene
     * @param scnDef
     * @return
     */
    public SceneDef loadScene(FileHandle scnDef) throws IOException {
        SceneDef scene = SceneDef.read(scnDef);

        // Load all scene fsms
        for(SceneDef.SceneFsmDef def : scene.layout){
            Fsm fsm = loadFSM(Gdx.files.internal(def.scriptPath), scnDef.pathWithoutExtension() + def.animation);
            fsm.setAnimation(def.animation);
            fsm.pos.set(def.x, def.y);
            fsm.scrollFactor.set(def.scrollFactorX, def.scrollFactorY);
            fsm.setLayer(def.layer);
        }

        return scene;
    }

    /**
     * Change fsm layer
     */
    public void layerChanged(Fsm fsm, int oldLayer){
        if( layers[oldLayer].removeValue(fsm, true) ) {
            layers[fsm.getLayer()].add(fsm);
        }
    }

    /**
     * Check for fsm collisions
     */
    private void checkCollisions() {
        for( Fsm f1 : fsms.values() ){
            if( f1.animation == null ) continue;
            for( Object o : f1.getCollisionTargets() ){
                Fsm f2 = (Fsm) o;
                if( f2.animation == null ) continue;
                for(Rectangle r1 : f1.animation.getCurrentFrame().hitCollisions ){
                    f1Rect.set(r1);
                    f1.getRectWorld(f1Rect);
                    for(Rectangle r2 : f2.animation.getCurrentFrame().damageCollisions ){
                        f2Rect.set(r2);
                        f2.getRectWorld(f2Rect);
                        if (f1Rect.overlaps(f2Rect)){
                            f2.setHit(f1.getAttackHit());
                        }
                    }
                }
            }
        }
    }

    /**
     * Uniquefy a name
     * @param name
     * @return Uniquefied name
     */
    public String uniquefy(String name) {
        String res = name;
        int i=1;
        while(fsms.containsKey(name)){
            res = name + "_" + i;
            i++;
        }
        return res;
    }

    // Accessors
    public SweatCamera getCamera() {
        return camera;
    }
    public Settings getSettings() {
        return settings;
    }
    public boolean isDebug() {
        return debug;
    }
}
