package com.lksoft.yugen.stateless;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Lake on 12/06/2016.
 */
public class Settings {
    // Input Keys
    public class KeySettings{
        public int up;
        public int down;
        public int left;
        public int right;
        public int b1;
        public int b2;
        public int b3;
        public int b4;
        public int b5;
        public int b6;
    }

    // Players keys
    private KeySettings p1Keys = new KeySettings();
    private KeySettings p2Keys = new KeySettings();

    public KeySettings getP1Keys() {
        return p1Keys;
    }
    public KeySettings getP2Keys() {
        return p2Keys;
    }

    /**
     * Parse settings from fil
     * @param defFile Settings file
     */
    public Settings(FileHandle defFile){
        String[] lines = defFile.readString().split("\\n");
        for( String line : lines ) {
            line = line.trim();
            if (line.startsWith("p1.keys.U")) {
                getP1Keys().up = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            else if (line.startsWith("p2.keys.U")) {
                getP2Keys().up = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            if (line.startsWith("p1.keys.D")) {
                getP1Keys().down = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            else if (line.startsWith("p2.keys.D")) {
                getP2Keys().down = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            if (line.startsWith("p1.keys.L")) {
                getP1Keys().left = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            else if (line.startsWith("p2.keys.L")) {
                getP2Keys().left = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            if (line.startsWith("p1.keys.R")) {
                getP1Keys().right = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            else if (line.startsWith("p2.keys.R")) {
                getP2Keys().right = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            if (line.startsWith("p1.keys.B1")) {
                getP1Keys().b1 = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            else if (line.startsWith("p2.keys.B1")) {
                getP2Keys().b1 = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            if (line.startsWith("p1.keys.B2")) {
                getP1Keys().b2 = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            else if (line.startsWith("p2.keys.B2")) {
                getP2Keys().b2 = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            if (line.startsWith("p1.keys.B3")) {
                getP1Keys().b3 = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            else if (line.startsWith("p2.keys.B3")) {
                getP2Keys().b3 = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            if (line.startsWith("p1.keys.B4")) {
                getP1Keys().b4 = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            else if (line.startsWith("p2.keys.B4")) {
                getP2Keys().b4 = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            if (line.startsWith("p1.keys.B5")) {
                getP1Keys().b5 = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            else if (line.startsWith("p2.keys.B5")) {
                getP2Keys().b5 = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            if (line.startsWith("p1.keys.B6")) {
                getP1Keys().b6 = Input.Keys.valueOf(line.split("=")[1].trim());
            }
            else if (line.startsWith("p2.keys.B6")) {
                getP2Keys().b6 = Input.Keys.valueOf(line.split("=")[1].trim());
            }
        }
    }
}
