package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Lake on 11/06/2016.
 */
class FighterDefReader {

    /**
     * Reads fighterdef from .def file
     * @param defFile
     * @param fighterDef
     */
    void read(FileHandle defFile, FighterDef fighterDef){
        String[] lines = defFile.readString().split("\\n");
        for( String line : lines ){
            line = line.trim();
            if( line.startsWith("name") ){
                fighterDef.setName(line.split("=")[1].trim());
            }
            else if( line.startsWith("author") ){
                fighterDef.setAuthor(line.split("=")[1].trim());
            }
            else if( line.startsWith("targetRes") ){
                fighterDef.setTargetRes(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("atlas") ){
                fighterDef.setAtlas(new FileHandle(defFile.parent() + "\\" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("frm") ){
                fighterDef.setFrm(new FileHandle(defFile.parent() + "\\" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("anm") ){
                fighterDef.setAnm(new FileHandle(defFile.parent() + "\\" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("fsm") ){
                fighterDef.setFsm(new FileHandle(defFile.parent() + "\\" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("cmd") ){
                fighterDef.setCmd(new FileHandle(defFile.parent() + "\\" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("snd") ){
                fighterDef.setSnd(new FileHandle(defFile.parent() + "\\" + line.split("=")[1].trim()));
            }
        }

        // Load animations
        TextureAtlas atlas = new TextureAtlas(fighterDef.getAtlas());
        Frames frames = new Frames(atlas, fighterDef.getFrm());
        Animations animations = new Animations(frames, fighterDef.getAnm());
        fighterDef.setAnimations(animations);
    }
}
