package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Lake on 08/06/2016.
 */
public class StageDef {

    // Animations
    private transient Animations animations;

    // Frm file
    private FileHandle frmFile;
    // Atlas file
    private FileHandle atlasFile;
    // Anm file
    private FileHandle anmFile;

    // Basic info
    private String name = "newstage";
    private String author = "LK software";

    // Camera
    private int cameraW = 320;
    private int cameraH = 240;
    private int cameraOffsetY = 90;

    // Area
    private int areaR = 768;
    private int areaL = -768;
    private int areaT = 500;
    private int areaB = -200;

    // Player positions
    private int p1StartX = -70;
    private int p2StartX = 70;

    // Sprite definitions
    private Array<SpriteDef> layers[] = new Array[2];

    /**
     * Create a new stage
     * @param animations Loaded animation definitions
     */
    public StageDef(Animations animations, FileHandle anmFile, FileHandle frmFile, FileHandle atlasFile){
        this();
        this.setAnimations(animations);
        this.setAnmFile(anmFile);
        this.setFrmFile(frmFile);
        this.setAtlasFile(atlasFile);
    }

    // Constructor for deserialization
    StageDef(){
        // Create layers
        for(int l=0; l<layers.length; l++){
            layers[l] = new Array<>();
        }
    }

    // Change layer of sprite def
    public void changeLayer(SpriteDef currentDef, int layer) {
        if( layer == currentDef.getLayer() ) return;

        layers[currentDef.getLayer()].removeValue(currentDef, true);
        currentDef.setLayer(layer);
        layers[layer].add(currentDef);
    }

    public Animations getAnimations() {
        return animations;
    }

    public void setAnimations(Animations animations) {
        this.animations = animations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCameraW() {
        return cameraW;
    }

    public void setCameraW(int cameraW) {
        this.cameraW = cameraW;
    }

    public int getCameraH() {
        return cameraH;
    }

    public void setCameraH(int cameraH) {
        this.cameraH = cameraH;
    }

    public int getCameraOffsetY() {
        return cameraOffsetY;
    }

    public void setCameraOffsetY(int cameraOffsetY) {
        this.cameraOffsetY = cameraOffsetY;
    }

    public int getAreaR() {
        return areaR;
    }

    public void setAreaR(int areaR) {
        this.areaR = areaR;
    }

    public int getAreaL() {
        return areaL;
    }

    public void setAreaL(int areaL) {
        this.areaL = areaL;
    }

    public int getAreaT() {
        return areaT;
    }

    public void setAreaT(int areaT) {
        this.areaT = areaT;
    }

    public int getAreaB() {
        return areaB;
    }

    public void setAreaB(int areaB) {
        this.areaB = areaB;
    }

    public int getP1StartX() {
        return p1StartX;
    }

    public void setP1StartX(int p1StartX) {
        this.p1StartX = p1StartX;
    }

    public int getP2StartX() {
        return p2StartX;
    }

    public void setP2StartX(int p2StartX) {
        this.p2StartX = p2StartX;
    }

    public Array<SpriteDef>[] getLayers() {
        return layers;
    }

    public void addSpriteDef(SpriteDef spriteDef) {
        layers[spriteDef.getLayer()].add(spriteDef);
    }

    public FileHandle getFrmFile() {
        return frmFile;
    }

    public FileHandle getAtlasFile() {
        return atlasFile;
    }

    public FileHandle getAnmFile() {
        return anmFile;
    }

    public void setFrmFile(FileHandle frmFile) {
        this.frmFile = frmFile;
    }

    public void setAtlasFile(FileHandle atlasFile) {
        this.atlasFile = atlasFile;
    }

    public void setAnmFile(FileHandle anmFile) {
        this.anmFile = anmFile;
    }
}
