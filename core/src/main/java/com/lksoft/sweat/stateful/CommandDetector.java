package com.lksoft.sweat.stateful;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.lksoft.sweat.stateless.CommandDef;

import java.util.LinkedList;

/**
 * Created by Lake on 15/06/2016.
 */
public class CommandDetector {

    // Input state for control buttons
    private static class InputState {
        boolean F, B, U, D, B1, B2, B3, B4, B5, B6;

        // Determine down state of key
        public boolean get(CommandDef.CommandKey key) {
            switch (key){
                case F: return F;
                case B: return B;
                case U: return U;
                case D: return D;
                case B1:return B1;
                case B2:return B2;
                case B3:return B3;
                case B4:return B4;
                case B5:return B5;
                case B6:return B6;
                default: return false;
            }
        }

        // Determine AND of multiple keystates
        public boolean get(Array<CommandDef.CommandKey> keys) {
            for(CommandDef.CommandKey k : keys ) {
                if( !get(k) ) return false;
            }
            return true;
        }

        public void clear() {
            F = B = U = D = B1 = B2 = B3 = B4 = B5 = B6 = false;
        }
    }

    // Fsm
    private Fsm fsm;

    // Last 60 frames of input
    private LinkedList<InputState> history = new LinkedList<>();

    // Key to be held down (temp)
    private Array<CommandDef.CommandKey> holdKeys = new Array<>(10);


    /**
     * Create detector for specified key settings
     * @param fsm
     */
    public CommandDetector(Fsm fsm){
        this.fsm = fsm;

        // Pre-allocate all input frames
        for( int i=0; i<60; i++ ){
            history.add(i, new InputState());
        }
    }

    /**
     * SpriteDef update
     */
    public void update(){
        if( fsm.getKeySettings() == null ) return;
        InputState last = history.removeLast();
        last.U = fsm.getKeySettings().up != null && Gdx.input.isKeyPressed(fsm.getKeySettings().up.key);
        last.D = fsm.getKeySettings().down != null && Gdx.input.isKeyPressed(fsm.getKeySettings().down.key);
        last.B1 = fsm.getKeySettings().b1 != null && Gdx.input.isKeyPressed(fsm.getKeySettings().b1.key);
        last.B2 = fsm.getKeySettings().b2 != null && Gdx.input.isKeyPressed(fsm.getKeySettings().b2.key);
        last.B3 = fsm.getKeySettings().b3 != null && Gdx.input.isKeyPressed(fsm.getKeySettings().b3.key);
        last.B4 = fsm.getKeySettings().b4 != null && Gdx.input.isKeyPressed(fsm.getKeySettings().b4.key);
        last.B5 = fsm.getKeySettings().b5 != null && Gdx.input.isKeyPressed(fsm.getKeySettings().b5.key);
        last.B6 = fsm.getKeySettings().b6 != null && Gdx.input.isKeyPressed(fsm.getKeySettings().b6.key);

        boolean l = fsm.getKeySettings().left != null && Gdx.input.isKeyPressed(fsm.getKeySettings().left.key);
        boolean r = fsm.getKeySettings().right != null && Gdx.input.isKeyPressed(fsm.getKeySettings().right.key);
        boolean facingRight = !fsm.flip;
        last.F = (facingRight && r) || (!facingRight && l);
        last.B = (facingRight && l) || (!facingRight && r);

        history.addFirst(last);
    }

    /**
     * Matches a command def, and clears the history if a match is found
     *
     * @param command
     * @return true if matched, false otherwise
     */
    public boolean matchCommand(CommandDef command){
        int startPos = command.time-1;

        // Match command
        int p = startPos;
        holdKeys.clear();

        for(CommandDef.CommandToken t : command.sequence ) {
            // if key hold, remember it and forget matching
            if( t.hold ){
                holdKeys.addAll(t.keys);
                continue;
            }

            // if timed match, the last n frames must be held
            if( t.time != -1 ){
                if( !matchTimedHold(t, p)) continue;
            }

            // Match token
            p = matchToken(t, p);
            if( p<0 ) break;
        }

        // If last position is positive, all tokens have been matched
        return p>=0;
    }

    // Match a token with history starting back at p
    private int matchToken(CommandDef.CommandToken t, int p) {
        for( ; p>=0; p-- ){
            // Exclusive check
            if (t.exclusive && otherKey(t, p)) return -1;
            // Key hold check
            if( !history.get(p).get(holdKeys) ) return -1;

            // Release keypress
            if( t.release ) {
                if (matchKeyRelease(t, p)) return p;
            }
            // Normal keypress
            else {
                if (matchKeyDown(t, p)) return p;
            }
        }

        return p;
    }

    // Match a timed hold
    private boolean matchTimedHold(CommandDef.CommandToken token, int p) {
        // Not enough frames
        if( p+token.time >= history.size() ) return false;

        for( int i=p+token.time; i>=p; i-- ){
            if( !history.get(i).get(token.keys) ) return false;
        }
        return true;
    }

    // Determine key down
    private boolean matchKeyDown(CommandDef.CommandToken t, int p) {
        return !matchKeyHold(t, p+1) && matchKeyHold(t, p);
    }

    // Determine key release
    private boolean matchKeyRelease(CommandDef.CommandToken t, int p) {
        return matchKeyHold(t, p+1) && !matchKeyHold(t, p);
    }

    // Determine key hold
    private boolean matchKeyHold(CommandDef.CommandToken t, int p) {
        return history.get(p).get(t.keys);
    }

    // Determine if any other key is hold
    private boolean otherKey(CommandDef.CommandToken t, int p) {
        for(CommandDef.CommandKey k : CommandDef.CommandKey.values() ) {
            if( !t.keys.contains(k, true) && history.get(p).get(k) ) return true;
        }

        return false;
    }

    // Clear the history
    public void clearHistory() {
        for( InputState s: history ){
            s.clear();
        }
    }
}
