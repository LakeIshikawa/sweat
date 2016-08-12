package com.lksoft.yugen.tools.spriteeditor;

import com.lksoft.yugen.stateless.SpriteDef;
import com.lksoft.yugen.stateless.SpritePack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Lake on 08/06/2016.
 */
public class SpritePackWriter {

    // Output file
    private File file;

    /**
     * Creates pack writer
     * @param file Output file
     */
    public SpritePackWriter(File file) {
        this.file = file;
    }

    /**
     * Serialize the specified spriteDef pack
     * @param pack Animation pack to serialize
     */
    public void write(SpritePack pack) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        // Animations
        for(SpriteDef def : pack.getSpriteDefs()) {
            bw.write(def.region.name);
            bw.write(" ");
            bw.write("" + def.originX + " " + (def.region.originalHeight - def.originY));
            bw.write("\n");
        }

        bw.close();
    }
}
