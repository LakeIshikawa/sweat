package com.lksoft.yugen.tools.frameeditor;

import com.lksoft.yugen.stateless.Frame;
import com.lksoft.yugen.stateless.FramePack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Lake on 08/06/2016.
 */
public class FramePackWriter {

    // Output file
    private File file;

    /**
     * Creates pack writer
     * @param file Output file
     */
    public FramePackWriter(File file) {
        this.file = file;
    }

    /**
     * Serialize the specified frame pack
     * @param pack Animation pack to serialize
     */
    public void write(FramePack pack) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        // Animations
        for(Frame def : pack.getFrames()) {
            bw.write(def.region.name);
            bw.write(" ");
            bw.write("" + def.originX + " " + (def.region.originalHeight - def.originY));
            bw.write("\n");
        }

        bw.close();
    }
}
