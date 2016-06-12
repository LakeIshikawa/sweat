package com.lksoft.yugen.tools;

import com.lksoft.yugen.stateless.SpriteDef;
import com.lksoft.yugen.stateless.StageDef;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Lake on 08/06/2016.
 */
public class StageLayoutWriter {

    // Output file
    private File file;

    /**
     * Creates a writer
     * @param file Output file
     */
    public StageLayoutWriter(File file) {
        this.file = file;
    }

    /**
     * Serialize the specified stage layout
     * @param layout Stage layout to serialize
     */
    public void write(StageDef layout) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(file));

        // Params
        br.write("[Params]\n");
        br.write("name = " + layout.getName() + "\n");
        br.write("author = " + layout.getAuthor() + "\n");
        br.write("\n");

        br.write("atlas = " + layout.getAtlasFile().name() + "\n");
        br.write("frm = " + layout.getFrmFile().name() + "\n");
        br.write("anm = " + layout.getAnmFile().name() + "\n");
        br.write("\n");

        br.write("camera.width = " + layout.getCameraW() + "\n");
        br.write("camera.height = " + layout.getCameraH() + "\n");
        br.write("camera.offsetY = " + layout.getCameraOffsetY() + "\n");
        br.write("\n");

        br.write("p1.start.x = " + layout.getP1StartX() + "\n");
        br.write("p2.start.x = " + layout.getP2StartX() + "\n");
        br.write("\n");

        br.write("area.right = " + layout.getAreaR() + "\n");
        br.write("area.left = " + layout.getAreaL() + "\n");
        br.write("area.top = " + layout.getAreaT() + "\n");
        br.write("area.bottom = " + layout.getAreaB() + "\n");
        br.write("\n\n");

        // Sprite defs
        for( int l=0; l<layout.getLayers().length; l++ ) {
            for (SpriteDef s : layout.getLayers()[l]) {
                br.write("[Sprite " + s.getName() + "]\n");
                br.write("start = " + s.getStartX() + "," + s.getStartY() + "\n");
                br.write("scrollFactor = " + s.getScrollFactorX() + "," + s.getScrollFactorY() + "\n");
                br.write("layer = " + s.getLayer() + "\n");
                br.write("resource = " + s.getResource().getName() + "\n\n");
            }
        }

        br.close();
    }
}
