package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.math.Rectangle;
import com.lksoft.yugen.stateless.AnimationDef;
import com.lksoft.yugen.stateless.AnimationFrame;
import com.lksoft.yugen.stateless.AnimationPack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Lake on 08/06/2016.
 */
public class AnimationPackWriter {

    // Output file
    private File file;

    /**
     * Creates pack writer
     * @param file Output file
     */
    public AnimationPackWriter(File file) {
        this.file = file;
    }

    /**
     * Serialize the specified animation pack
     * @param pack Animation pack to serialize
     */
    public void write(AnimationPack pack) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        // Animations
        for(AnimationDef def : pack.getAnimations()) {
            bw.write("[Anim " + def.getName() + "]\n");
            for(AnimationFrame frame : def.getFrames()){
                // Loop point
                if( def.getLoopStartPos() != 0 && def.getLoopStartPos() == def.getFrames().indexOf(frame, true) ){
                    bw.write("loopstart\n");
                }
                bw.write(frame.frame.region.name);
                bw.write(" ");
                bw.write(""+frame.lengthTicks);
                bw.write(" ");

                if( !(frame.damageCollisions.size == 0 && frame.hitCollisions.size == 0)) {
                    for (Rectangle r : frame.damageCollisions) {
                        bw.write(r + ";");
                    }
                    bw.write("||");
                    for (Rectangle r : frame.hitCollisions) {
                        bw.write(r + ";");
                    }
                }
                bw.write("\n");
            }
            bw.write("\n");
        }

        bw.close();
    }
}
