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

    // Stage stageDef
    private StageDef stageDef;

    // Camera
    private StageCamera camera;

    // The 2 players
    private Fighter p1;
    private Fighter p2;

    // Batch
    private SpriteBatch batch = new SpriteBatch();

    /**
     * Create b1 stage from b1 stageDef
     * @param stageDef The stage stageDef
     */
    public Stage(StageDef stageDef, Fighter p1, Fighter p2, StageCamera camera){
        this.setStageDef(stageDef);
        this.setCamera(camera);
        camera.setStage(this);
        this.p1 = p1;
        this.p2 = p2;


        // Position the players
        if( p1 != null ) {
            p1.setStage(this);
            p1.setPosX(stageDef.getP1StartX());
            p1.setPosY(0);
            p1.setFacing(false);

            // Calculate scale factor
            int oh = p1.getFighterDef().getAnimationPack().getAnimationDef("idle").getFrameAt(0).frame.region.originalHeight;
            float scale =  p1.getFighterDef().getScale() * (stageDef.getFightersHeight() / oh);
            p1.scale = scale;

        }
        if( p2 != null ) {
            p2.setStage(this);
            p2.setPosX(stageDef.getP2StartX());
            p2.setPosY(0);
            p2.setFacing(true);

            // Calculate scale factor
            int oh = p2.getFighterDef().getAnimationPack().getAnimationDef("idle").getFrameAt(0).frame.region.originalHeight;
            float scale =  p2.getFighterDef().getScale() * (stageDef.getFightersHeight() / oh);
            p2.scale = scale;
        }

        // Create the sprites
        for(int i = 0; i< stageDef.getLayers().length; i++ ) {
            layers[i] = new ArrayList<>();
            for (SpriteDef def : stageDef.getLayers()[i]) {
                layers[i].add(new StageSprite(def, this));
            }
        }
    }

    /**
     * Frame update
     */
    public void update(){
        // Update fighters
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
    public StageDef getStageDef() {
        return stageDef;
    }
    public void setStageDef(StageDef stageDef) {
        this.stageDef = stageDef;
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
