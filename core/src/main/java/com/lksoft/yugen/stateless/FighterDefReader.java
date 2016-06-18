package com.lksoft.yugen.stateless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.io.IOException;

/**
 * Created by Lake on 11/06/2016.
 */
class FighterDefReader {

    /**
     * Reads fighterdef from .def file
     * @param defFile
     * @param fighterDef
     */
    void read(FileHandle defFile, FighterDef fighterDef) throws IOException {
        String[] lines = defFile.readString().split("\\n");
        for( String line : lines ){
            line = line.trim();
            if( line.startsWith("name") ){
                fighterDef.setName(line.split("=")[1].trim());
            }
            else if( line.startsWith("author") ){
                fighterDef.setAuthor(line.split("=")[1].trim());
            }
            else if( line.startsWith("scale") ){
                fighterDef.setScale(Float.parseFloat(line.split("=")[1].trim()));
            }
            else if( line.startsWith("atlas") ){
                fighterDef.setAtlas(Gdx.files.internal(defFile.parent() + "/" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("frm") ){
                fighterDef.setFrm(Gdx.files.internal(defFile.parent() + "/" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("anm") ){
                fighterDef.setAnm(Gdx.files.internal(defFile.parent() + "/" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("fsm") ){
                fighterDef.setFsm(Gdx.files.internal(defFile.parent() + "/" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("cmd") ){
                fighterDef.setCmd(Gdx.files.internal(defFile.parent() + "/" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("snd") ){
                fighterDef.setSnd(Gdx.files.internal(defFile.parent() + "/" + line.split("=")[1].trim()));
            }
        }

        // Load animations
        TextureAtlas atlas = new TextureAtlas(fighterDef.getAtlas());
        Frames frames = new Frames(atlas, fighterDef.getFrm());
        Animations animations = new Animations(frames, fighterDef.getAnm());
        fighterDef.setAnimations(animations);

        // Load commands
        Commands commands = new Commands(Gdx.files.internal("shared/basic.cmd"));
        Commands customCommands = new Commands(fighterDef.getCmd());
        commands.merge(customCommands);
        fighterDef.setCommands(commands);
    }
}
