package com.lksoft.yugen.stateful;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lksoft.yugen.stateless.SpriteDef;
import com.lksoft.yugen.stateless.StageDef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lake on 08/06/2016.
 */
public class Stage {

    // Sprites
    private List<Sprite> layers[] = new ArrayList[2];

    // Stage layout
    private StageDef layout;

    // Camera
    private StageCamera camera;

    // The 2 players
    private Fighter p1;
    private Fighter p2;

    // Batch
    private SpriteBatch batch = new SpriteBatch();

    /**
     * Create a stage from a layout
     * @param layout The stage layout
     */
    public Stage(StageDef layout, Fighter p1, Fighter p2, StageCamera camera){
        this.setLayout(layout);
        this.setCamera(camera);
        camera.setStage(this);
        this.p1 = p1;
        this.p2 = p2;

        // Position the players
        if( p1 != null ) p1.pos.set(layout.getP1StartX(), 0);
        if( p2 != null ) p2.pos.set(layout.getP2StartX(), 0);

        // Create the sprites
        for( int i=0; i<layout.getLayers().length; i++ ) {
            layers[i] = new ArrayList<>();
            for (SpriteDef def : layout.getLayers()[i]) {
                layers[i].add(new StageSprite(def, this));
            }
        }
    }

    /**
     * Frame update
     */
    public void update(){
        if( getP1() != null ) getP1().update();
        if( getP2() != null ) getP2().update();

        // Update camera
        getCamera().update(this);

        // Update sprites
        for( int i=0; i<layers.length; i++ ) {
            for (Sprite s : layers[i]) {
                s.update();
            }
        }
    }

    /**
     * Render the stage
     */
    public void render(){
        batch.setProjectionMatrix(getCamera().getCombinedMatrix());
        batch.begin();
        for( int i=0; i<layers.length; i++ ) {
            // Render bg sprites
            for (Sprite s : layers[i]) {
                s.render(batch);
            }

            // Render chars
            if( p1 != null && p1.getLayer() == i ) p1.render(batch);
            if( p2 != null && p2.getLayer() == i ) p2.render(batch);
        }
        batch.end();
    }

    /**
     * Screen resize
     * @param width
     * @param height
     */
    public void resize(int width, int height) {
        getCamera().resize(width, height);
    }

    public StageDef getLayout() {
        return layout;
    }

    public void setLayout(StageDef layout) {
        this.layout = layout;
    }

    public StageCamera getCamera() {
        return camera;
    }

    public void setCamera(StageCamera camera) {
        this.camera = camera;
    }

    public Fighter getP1() {
        return p1;
    }

    public Fighter getP2() {
        return p2;
    }
}
