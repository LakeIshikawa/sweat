package com.lksoft.yugen.stateless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmLexer;
import com.lksoft.yugen.FsmParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Lake on 11/06/2016.
 */
public class FighterDef {

    // Name
    private String name = "Yugen";
    // Author
    private String author = "LK Soft";
    // Target Y resolution
    private int targetRes = 360;

    // Files
    private FileHandle atlas;
    private FileHandle frm;
    private FileHandle anm;
    private FileHandle fsm;
    private FileHandle cmd;
    private FileHandle snd;

    // Animations
    private Animations animations;

    // Params
    private HashMap<String, Float> params = new HashMap<>();
    // States
    private HashMap<String, FighterState> states = new HashMap<>();
    // Stateless triggers
    private Array<FighterState.FighterTrigger> triggers = new Array<>();

    /**
     * Parse fighter def from .def file
     * @return Parsed Fighter definition
     */
    public FighterDef(FileHandle defFile) throws IOException {
        new FighterDefReader().read(defFile, this);

        // Parse shared base character
        parseFsm(Gdx.files.internal("shared/basic.fsm"));
        // Parse fighter
        parseFsm(getFsm());
    }

    /**
     * Disposes of associated resources.
     */
    public void dispose(){
        animations.dispose();
    }

    // Parse given fsm file and update this fighter definition
    private void parseFsm(FileHandle fsmFile) throws IOException {
        // Parse script
        FsmLexer lexer = new FsmLexer(new ANTLRInputStream(fsmFile.read()));
        FsmParser parser = new FsmParser(new CommonTokenStream(lexer));
        FsmParser.FsmContext fsm = parser.fsm();

        // Create params and states
        FighterSetupVisitor visitor = new FighterSetupVisitor(this);
        fsm.accept(visitor);
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getTargetRes() {
        return targetRes;
    }

    public FileHandle getAtlas() {
        return atlas;
    }

    public FileHandle getFrm() {
        return frm;
    }

    public FileHandle getAnm() {
        return anm;
    }

    public FileHandle getFsm() {
        return fsm;
    }

    public FileHandle getCmd() {
        return cmd;
    }

    public FileHandle getSnd() {
        return snd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTargetRes(int targetRes) {
        this.targetRes = targetRes;
    }

    public void setAtlas(FileHandle atlas) {
        this.atlas = atlas;
    }

    public void setFrm(FileHandle frm) {
        this.frm = frm;
    }

    public void setAnm(FileHandle anm) {
        this.anm = anm;
    }

    public void setFsm(FileHandle fsm) {
        this.fsm = fsm;
    }

    public void setCmd(FileHandle cmd) {
        this.cmd = cmd;
    }

    public void setSnd(FileHandle snd) {
        this.snd = snd;
    }

    public Animations getAnimations() {
        return animations;
    }

    public void setAnimations(Animations animations) {
        this.animations = animations;
    }

    public HashMap<String, FighterState> getStates() {
        return states;
    }
    public Array<FighterState.FighterTrigger> getTriggers() {
        return triggers;
    }
    public HashMap<String, Float> getParams() {
        return params;
    }
}
