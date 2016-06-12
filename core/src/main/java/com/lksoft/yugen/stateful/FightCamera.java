package com.lksoft.yugen.stateful;

import com.lksoft.yugen.stateless.StageDef;

/**
 * Created by Lake on 11/06/2016.
 */
public class FightCamera extends StageCamera {

    // The fighting stage
    private Stage stage;

    /**
     * Create b1 fight camera
     *
     * @param layout The stage layout
     */
    public FightCamera(StageDef layout) {
        super(layout);
    }

    @Override
    public void update(Stage stage) {
        // Set camera position to center point
        float x = (stage.getP1().pos.x + stage.getP2().pos.x) / 2;
        float y = stage.getStageDef().getCameraOffsetY();
        viewport.getCamera().position.set(x, y, 0);
        viewport.getCamera().update();
    }
}
