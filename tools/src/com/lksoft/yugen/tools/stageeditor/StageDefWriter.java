package com.lksoft.yugen.tools.stageeditor;

import com.lksoft.yugen.stateless.StageSpriteDef;
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
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        // Params
        bw.write("[Params]\n");
        bw.write("name = " + def.getName() + "\n");
        bw.write("author = " + def.getAuthor() + "\n");
        bw.write("\n");

        bw.write("camera.width = " + def.getCameraW() + "\n");
        bw.write("camera.height = " + def.getCameraH() + "\n");
        bw.write("camera.offsetY = " + def.getCameraOffsetY() + "\n");
        bw.write("fighters.height = " + def.getFightersHeight() + "\n");
        bw.write("\n");

        bw.write("p1.start.x = " + def.getP1StartX() + "\n");
        bw.write("p2.start.x = " + def.getP2StartX() + "\n");
        bw.write("\n");

        bw.write("area.right = " + def.getAreaR() + "\n");
        bw.write("area.left = " + def.getAreaL() + "\n");
        bw.write("area.top = " + def.getAreaT() + "\n");
        bw.write("area.bottom = " + def.getAreaB() + "\n");
        bw.write("\n\n");

        // Sprite defs
        for( int l=0; l<def.getLayers().length; l++ ) {
            for (StageSpriteDef s : def.getLayers()[l]) {
                bw.write("[Sprite " + s.getName() + "]\n");
                bw.write("start = " + s.getStartX() + "," + s.getStartY() + "\n");
                bw.write("scrollFactor = " + s.getScrollFactorX() + "," + s.getScrollFactorY() + "\n");
                bw.write("layer = " + s.getLayer() + "\n");
                bw.write("resource = " + s.getResource().getName() + "\n\n");
            }
        }

        bw.close();
    }
}
