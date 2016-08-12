package com.lksoft.yugen.stateless;

import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Stallman on 11/08/2016.
 */
public enum BlendMode {
    NORMAL(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA),
    ADDITIVE(GL20.GL_SRC_ALPHA, GL20.GL_ONE);


    public int src, dst;
    BlendMode(int src, int dst) {
        this.src = src;
        this.dst = dst;
    }
}
