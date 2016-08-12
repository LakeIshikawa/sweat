package com.lksoft.yugen.stateful;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lksoft.yugen.stateless.StageSpriteDef;

/**
 * Created by Lake on 10/06/2016.
 */
public class StageSprite extends Sprite {

    // Stage
    private Stage stage;
    // Sprite definition
    private StageSpriteDef stageSpriteDef;

    /**
     * Create b1 sprite from b1 sprite def
     *
     * @param def The sprite definition
     */
    public StageSprite(StageSpriteDef def, Stage stage) {
        super(new Animation(def.getResource()));
        this.stage = stage;
        this.stageSpriteDef = def;

        // Set start position
        pos.set(def.getStartX(), def.getStartY());
    }

    @Override
    public void render(SpriteBatch batch){
        float x = pos.x + (stage.getCamera().getPosition().x - stage.getCamera().getCameraStart().x) * stageSpriteDef.getScrollFactorX();
        float y = pos.y + (stage.getCamera().getPosition().y - stage.getCamera().getCameraStart().y) * stageSpriteDef.getScrollFactorY();
        animation.draw(batch, x, y, scale, flip);
    }
}
