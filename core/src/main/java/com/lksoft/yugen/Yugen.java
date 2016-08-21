package com.lksoft.yugen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.lksoft.yugen.stateful.Fsm;
import com.lksoft.yugen.stateful.NonFsm;
import com.lksoft.yugen.stateless.SceneDef;
import com.lksoft.yugen.stateless.Settings;

import java.io.IOException;

import static com.lksoft.yugen.Resources.loadFSMClass;

/**
 * Created by Lake on 15/08/2016.
 *
 * Yugen engine
 */
public class Yugen {
    public static Yugen i;

    // Settings
    private Settings settings;

    // Fsms (map and layers)
    private ObjectMap<String, Fsm> fsms = new ObjectMap<>();
    private Array<Fsm>[] layers = new Array[10];

    // Viewport
    private YugenCamera camera = new YugenCamera();

    // Debug
    private boolean debug = false;
    private boolean drawRects = false;

    // Collisions
    private Rectangle f1Rect = new Rectangle();
    private Rectangle f2Rect = new Rectangle();

    /**
     * Create yugen engine
     * File "settings.def" must exist
     */
    public Yugen(FileHandle mainFsm, boolean debug, boolean compileScripts) throws IOException {
        i = this;
        this.debug = debug;

        if( compileScripts ){
            ScriptCompiler.compileScripts();
        }

        // Create layers
        for( int i=0; i<layers.length; i++ ){
            layers[i] = new Array<>();
        }

        // Parse settings
        settings = new Settings(Gdx.files.internal("settings.def"));

        // Load main fsm
        loadFSM(mainFsm, "main");
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
    public void render(SpriteBatch batch, YugenCamera camera) {
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
            fsm.setName(name);
            fsms.put(name, fsm);
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
        fsm.setName(name);
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
     * @param scnFile
     * @return
     */
    public SceneDef loadScene(FileHandle scnFile) throws IOException {
        SceneDef scene = SceneDef.read(scnFile);

        // Load all scene fsms
        for(SceneDef.SceneFsmDef def : scene.layout){
            Fsm fsm = loadFSM(Gdx.files.internal(def.scriptPath), scnFile.pathWithoutExtension() + def.animation); // TODO Only one fsm per animation is ungood.
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

    // Accessors
    public YugenCamera getCamera() {
        return camera;
    }
    public Settings getSettings() {
        return settings;
    }
    public boolean isDebug() {
        return debug;
    }
}
