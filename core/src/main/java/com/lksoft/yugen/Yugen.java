package com.lksoft.yugen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.lksoft.yugen.stateful.Fsm;
import com.lksoft.yugen.stateful.Sprite;
import com.lksoft.yugen.stateless.FsmDef;
import com.lksoft.yugen.stateless.FsmDefReader;
import com.lksoft.yugen.stateless.Settings;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;

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


    // Accessors
    public Settings getSettings() {
        return settings;
    }

    /**
     * Create yugen engine
     * File "settings.def" must exist
     */
    public Yugen(FileHandle mainFsm) throws IOException {
        i = this;

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
    }

    /**
     * Frame render
     */
    public void render(SpriteBatch batch, YugenCamera camera) {
        for( int i=0; i<layers.length; i++ ) {
            // Render bg sprites
            for (Sprite s : layers[i]) {
                s.render(batch, camera);
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
        FsmDef fsmDef = new FsmDefReader(fsmFile).read();
        Fsm fsm = new Fsm(fsmDef);
        fsms.put(name, fsm);
        return fsm;
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
     * Check for fsm collisions
     */
    private void checkCollisions() {
//        // P1 to P2
//        for(Rectangle r1 : getP1().animation.getCurrentFrame().hitCollisions ){
//            p1Rect.set(r1);
//            getP1().getRectWorld(p1Rect);
//            for(Rectangle r2 : getP2().animation.getCurrentFrame().damageCollisions ){
//                p2Rect.set(r2);
//                getP2().getRectWorld(p2Rect);
//                if (p1Rect.overlaps(p2Rect)){
//                    getP2().setCurrentHit(getP1().getAttackHit());
//                }
//            }
//        }
//
//        // P2 to P1
//        for(Rectangle r2 : getP2().animation.getCurrentFrame().hitCollisions ){
//            p2Rect.set(r2);
//            getP2().getRectWorld(p2Rect);
//            for(Rectangle r1 : getP1().animation.getCurrentFrame().damageCollisions ){
//                p1Rect.set(r1);
//                getP1().getRectWorld(p1Rect);
//                if (p1Rect.overlaps(p2Rect)){
//                    getP1().setCurrentHit(getP2().getAttackHit());
//                }
//            }
//        }
    }
}
