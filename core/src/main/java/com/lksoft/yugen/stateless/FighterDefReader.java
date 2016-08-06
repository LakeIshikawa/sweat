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
        }

        // Infer resources
        fighterDef.setAtlas(new FileHandle(defFile.pathWithoutExtension() + ".atlas"));
        fighterDef.setFrm(new FileHandle(defFile.pathWithoutExtension() + ".frm"));
        fighterDef.setAnm(new FileHandle(defFile.pathWithoutExtension() + ".anm"));
        fighterDef.setCmd(new FileHandle(defFile.pathWithoutExtension() + ".cmd"));
        fighterDef.setFsm(new FileHandle(defFile.pathWithoutExtension() + ".fsm"));
        fighterDef.setSnd(new FileHandle(defFile.pathWithoutExtension() + ".snd"));

        // Load animationPack
        TextureAtlas atlas = new TextureAtlas(fighterDef.getAtlas());
        Frames frames = new Frames(atlas, fighterDef.getFrm());
        AnimationPack animationPack = new AnimationPackReader(fighterDef.getAnm()).read(frames);
        fighterDef.setAnimationPack(animationPack);

        // Load commands
        Commands commands = new Commands(Gdx.files.internal("shared/basic.cmd"));
        Commands customCommands = new Commands(fighterDef.getCmd());
        commands.merge(customCommands);
        fighterDef.setCommands(commands);
    }
}
