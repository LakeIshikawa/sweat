package com.lksoft.yugen.stateless;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Lake on 12/06/2016.
 */
public class Settings {
    // Virtual keys
    public enum Key {
        A(Input.Keys.A),
        B(Input.Keys.B),
        C(Input.Keys.C),
        D(Input.Keys.D),
        E(Input.Keys.E),
        F(Input.Keys.F),
        G(Input.Keys.G),
        H(Input.Keys.H),
        I(Input.Keys.I),
        J(Input.Keys.J),
        K(Input.Keys.K),
        L(Input.Keys.L),
        M(Input.Keys.M),
        N(Input.Keys.N),
        O(Input.Keys.O),
        P(Input.Keys.P),
        Q(Input.Keys.Q),
        R(Input.Keys.R),
        S(Input.Keys.S),
        T(Input.Keys.T),
        U(Input.Keys.U),
        V(Input.Keys.V),
        W(Input.Keys.W),
        X(Input.Keys.X),
        Y(Input.Keys.Y),
        Z(Input.Keys.Z),

        NUM_1(Input.Keys.NUM_1),
        NUM_2(Input.Keys.NUM_2),
        NUM_3(Input.Keys.NUM_3),
        NUM_4(Input.Keys.NUM_4),
        NUM_5(Input.Keys.NUM_5),
        NUM_6(Input.Keys.NUM_6),
        NUM_7(Input.Keys.NUM_7),
        NUM_8(Input.Keys.NUM_8),
        NUM_9(Input.Keys.NUM_9),
        NUM_0(Input.Keys.NUM_0),

        MINUS(Input.Keys.MINUS),
        EQUALS(Input.Keys.EQUALS),
        LBRACKET(Input.Keys.LEFT_BRACKET),
        RBRACKET(Input.Keys.RIGHT_BRACKET),
        SEMICOLON(Input.Keys.SEMICOLON),
        APOSTROPHE(Input.Keys.APOSTROPHE),
        SLASH(Input.Keys.SLASH),
        COMMA(Input.Keys.COMMA),
        DOT(Input.Keys.PERIOD),
        BACKSLASH(Input.Keys.BACKSLASH),

        TAB(Input.Keys.TAB),
        LSHIFT(Input.Keys.SHIFT_LEFT),
        LCTRL(Input.Keys.CONTROL_LEFT),
        LALT(Input.Keys.ALT_LEFT),
        SPACE(Input.Keys.SPACE),
        RALT(Input.Keys.ALT_RIGHT),
        RCTRL(Input.Keys.CONTROL_RIGHT),
        RSHIFT(Input.Keys.SHIFT_RIGHT),
        ENTER(Input.Keys.ENTER),
        BACKSPACE(Input.Keys.BACKSPACE),

        LEFT(Input.Keys.LEFT),
        RIGHT(Input.Keys.RIGHT),
        UP(Input.Keys.UP),
        DOWN(Input.Keys.DOWN),

        INSERT(Input.Keys.INSERT),
        HOME(Input.Keys.HOME),
        PGUP(Input.Keys.PAGE_UP),
        DELETE(Input.Keys.FORWARD_DEL),
        END(Input.Keys.END),
        PGDOWN(Input.Keys.PAGE_DOWN),

        NUMPAD_0(Input.Keys.NUMPAD_0),
        NUMPAD_1(Input.Keys.NUMPAD_1),
        NUMPAD_2(Input.Keys.NUMPAD_2),
        NUMPAD_3(Input.Keys.NUMPAD_3),
        NUMPAD_4(Input.Keys.NUMPAD_4),
        NUMPAD_5(Input.Keys.NUMPAD_5),
        NUMPAD_6(Input.Keys.NUMPAD_6),
        NUMPAD_7(Input.Keys.NUMPAD_7),
        NUMPAD_8(Input.Keys.NUMPAD_8),
        NUMPAD_9(Input.Keys.NUMPAD_9)
        ;

        public int key;
        Key(int key){
            this.key = key;
        }
    }

    // Input Keys
    public static class KeySettings{
        public Key up;
        public Key down;
        public Key left;
        public Key right;
        public Key b1;
        public Key b2;
        public Key b3;
        public Key b4;
        public Key b5;
        public Key b6;
    }

    // Startup fsm script
    private String mainFsm;
    // Players keys
    private KeySettings p1Keys = new KeySettings();
    private KeySettings p2Keys = new KeySettings();

    public String getMainFsm() {
        return mainFsm;
    }
    public KeySettings getP1Keys() {
        return p1Keys;
    }
    public KeySettings getP2Keys() {
        return p2Keys;
    }

    /**
     * Parse settings from file
     * @param setFile Settings file
     */
    public static Settings read(FileHandle setFile){
        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        return json.fromJson(Settings.class, setFile);
    }

    /**
     * Writes settings to file
     * @param setFile
     */
    public void write(FileHandle setFile){
        Json json = new Json();
        json.setIgnoreUnknownFields(true);

        String jstring = json.prettyPrint(this);
        setFile.writeString(jstring, false);
    }
}
