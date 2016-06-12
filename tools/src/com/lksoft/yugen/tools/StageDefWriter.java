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
public class StageDefWriter {

    // Output file
    private File file;

    /**
     * Creates b1 writer
     * @param file Output file
     */
    public StageDefWriter(File file) {
        this.file = file;
    }

    /**
     * Serialize the specified stage def
     * @param def Stage def to serialize
     */
    public void write(StageDef def) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(file));

        // Params
        br.write("[Params]\n");
        br.write("name = " + def.getName() + "\n");
        br.write("author = " + def.getAuthor() + "\n");
        br.write("\n");

        br.write("atlas = " + def.getAtlasFile().name() + "\n");
        br.write("frm = " + def.getFrmFile().name() + "\n");
        br.write("anm = " + def.getAnmFile().name() + "\n");
        br.write("\n");

        br.write("camera.width = " + def.getCameraW() + "\n");
        br.write("camera.height = " + def.getCameraH() + "\n");
        br.write("camera.offsetY = " + def.getCameraOffsetY() + "\n");
        br.write("fighters.height = " + def.getFightersHeight() + "\n");
        br.write("\n");

        br.write("p1.start.x = " + def.getP1StartX() + "\n");
        br.write("p2.start.x = " + def.getP2StartX() + "\n");
        br.write("\n");

        br.write("area.right = " + def.getAreaR() + "\n");
        br.write("area.left = " + def.getAreaL() + "\n");
        br.write("area.top = " + def.getAreaT() + "\n");
        br.write("area.bottom = " + def.getAreaB() + "\n");
        br.write("\n\n");

        // Sprite defs
        for( int l=0; l<def.getLayers().length; l++ ) {
            for (SpriteDef s : def.getLayers()[l]) {
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
