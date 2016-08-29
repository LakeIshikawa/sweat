package com.lksoft.sweat.stateful;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

/**
 * An fsm with only one state.
 * So it's a non-fsm.
 */
public class NonFsm extends Fsm<NonFsm, State<NonFsm>, Object> {
    @Override
    protected State<NonFsm> getInitialState() {
        return new State<NonFsm>() {
            @Override
            public void enter(NonFsm entity) {

            }

            @Override
            public void update(NonFsm entity) {

            }

            @Override
            public void exit(NonFsm entity) {

            }

            @Override
            public boolean onMessage(NonFsm entity, Telegram telegram) {
                return false;
            }
        };
    }

    @Override
    protected void statelessUpdate() {}
}
