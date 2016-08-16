package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.lksoft.yugen.FsmLexer;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsmlang.visitor.FighterSetupVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * Created by Lake on 11/06/2016.
 */
public class FsmDefReader {

    // Def file handle
    private FileHandle fsmFile;

    /**
     * Create new reader for fsmDef
     * @param fsmFile
     */
    public FsmDefReader(FileHandle fsmFile){
        this.fsmFile = fsmFile;
    }

    /**
     * Reads fighterdef from .def file
     */
    public void read(FsmDef fsmDef) throws IOException {
        // Infer resources
        fsmDef.setFsm(fsmFile);
        fsmDef.setAtlas(new FileHandle(fsmFile.pathWithoutExtension() + ".atlas"));
        fsmDef.setFrm(new FileHandle(fsmFile.pathWithoutExtension() + ".frm"));
        fsmDef.setAnm(new FileHandle(fsmFile.pathWithoutExtension() + ".anm"));
        fsmDef.setCmd(new FileHandle(fsmFile.pathWithoutExtension() + ".cmd"));
        fsmDef.setSnd(new FileHandle(fsmFile.pathWithoutExtension() + ".snd"));
        fsmDef.setHit(new FileHandle(fsmFile.pathWithoutExtension() + ".hit"));

        // Load animationPack
        if( fsmDef.getAnm().exists() ) {
            TextureAtlas atlas = new TextureAtlas(fsmDef.getAtlas());
            SpritePack spritePack = new SpritePackReader(fsmDef.getFrm()).read(atlas);
            AnimationPack animationPack = AnimationPack.read(fsmDef.getAnm(), spritePack);
            fsmDef.setAnimationPack(animationPack);
        }

        // Load hit pack
        if( fsmDef.getHit().exists() ) {
            HitPack hitPack = new HitPackReader(fsmDef.getHit()).read();
            if( fsmDef.getHitPack() != null ){
                fsmDef.getHitPack().merge(hitPack);
            } else {
                fsmDef.setHitPack(hitPack);
            }
        }

        // Load commands
        if( fsmDef.getCmd().exists() ) {
            Commands commands = new Commands(fsmDef.getCmd());
            if( fsmDef.getCommands() != null ) {
                fsmDef.getCommands().merge(commands);
            } else {
                fsmDef.setCommands(commands);
            }
        }

        // Parse FSM
        parseFsm(fsmDef.getFsm(), fsmDef);
    }

    /**
     * Reads fighterdef from .def file
     */
    public FsmDef read() throws IOException {
        FsmDef fsmDef = new FsmDef();
        read(fsmDef);
        return fsmDef;
    }


    // Parse given fsm file and update this fighter definition
    private void parseFsm(FileHandle fsmFile, FsmDef fsmDef) throws IOException {
        // Parse script
        FsmLexer lexer = new FsmLexer(new ANTLRInputStream(fsmFile.read()));
        FsmParser parser = new FsmParser(new CommonTokenStream(lexer));
        FsmParser.FsmContext fsm = parser.fsm();

        // Create params and states
        FighterSetupVisitor visitor = new FighterSetupVisitor(fsmDef);
        fsm.accept(visitor);
    }
}
