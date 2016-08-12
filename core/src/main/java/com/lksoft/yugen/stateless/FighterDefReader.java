package com.lksoft.yugen.stateless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.lksoft.yugen.FsmLexer;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsm.visitor.FighterSetupVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * Created by Lake on 11/06/2016.
 */
public class FighterDefReader {

    // Def file handle
    private FileHandle defFile;

    /**
     * Create new reader for fighterDef
     * @param defFile
     */
    public FighterDefReader(FileHandle defFile){
        this.defFile = defFile;
    }

    /**
     * Reads fighterdef from .def file
     */
    public FighterDef read() throws IOException {
        FighterDef fighterDef = new FighterDef();

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
        fighterDef.setHit(new FileHandle(defFile.pathWithoutExtension() + ".hit"));

        // Load animationPack
        TextureAtlas atlas = new TextureAtlas(fighterDef.getAtlas());
        SpritePack spritePack = new SpritePackReader(fighterDef.getFrm()).read(atlas);
        AnimationPack animationPack = AnimationPack.read(fighterDef.getAnm(), spritePack);
        fighterDef.setAnimationPack(animationPack);

        // Load hit pack
        HitPack hitPack = new HitPackReader(fighterDef.getHit()).read();
        fighterDef.setHitPack(hitPack);

        // Load commands
        Commands commands = new Commands(Gdx.files.internal("shared/basic.cmd"));
        Commands customCommands = new Commands(fighterDef.getCmd());
        commands.merge(customCommands);
        fighterDef.setCommands(commands);

        // Parse FSM
        // Parse shared base character
        parseFsm(Gdx.files.internal("shared/basic.fsm"), fighterDef);
        // Parse fighter
        parseFsm(fighterDef.getFsm(), fighterDef);

        return fighterDef;
    }


    // Parse given fsm file and update this fighter definition
    private void parseFsm(FileHandle fsmFile, FighterDef fighterDef) throws IOException {
        // Parse script
        FsmLexer lexer = new FsmLexer(new ANTLRInputStream(fsmFile.read()));
        FsmParser parser = new FsmParser(new CommonTokenStream(lexer));
        FsmParser.FsmContext fsm = parser.fsm();

        // Create params and states
        FighterSetupVisitor visitor = new FighterSetupVisitor(fighterDef);
        fsm.accept(visitor);
    }
}
