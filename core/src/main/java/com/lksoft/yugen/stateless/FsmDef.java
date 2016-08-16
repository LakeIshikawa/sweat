package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by Lake on 11/06/2016.
 */
public class FsmDef {
    // Files
    private FileHandle atlas;
    private FileHandle frm;
    private FileHandle anm;
    private FileHandle fsm;
    private FileHandle cmd;
    private FileHandle snd;
    private FileHandle hit;

    // AnimationPack
    private AnimationPack animationPack;
    // Hit pack
    private HitPack hitPack;
    // Commands
    private Commands commands;

    // States
    private ObjectMap<String, FsmState> states = new ObjectMap<>();
    private String initialState;
    // Stateless triggers
    private Array<FsmState.FighterTrigger> triggers = new Array<>();

    // Empty constructor
    public FsmDef(){}

    /**
     * Disposes of associated resources.
     */
    public void dispose(){
        animationPack.dispose();
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
    public FileHandle getHit() {
        return hit;
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
    public void setHit(FileHandle hit) {
        this.hit = hit;
    }

    public ObjectMap<String, FsmState> getStates() {
        return states;
    }
    public Array<FsmState.FighterTrigger> getTriggers() {
        return triggers;
    }

    public AnimationPack getAnimationPack() {
        return animationPack;
    }
    public void setAnimationPack(AnimationPack animationPack) {
        this.animationPack = animationPack;
    }
    public HitPack getHitPack() { return hitPack; }
    public void setHitPack(HitPack pack){
        this.hitPack = pack;
    }
    public Commands getCommands() {
        return commands;
    }
    public void setCommands(Commands commands) {
        this.commands = commands;
    }
    public String getInitialState() {
        return initialState;
    }
    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }
}
