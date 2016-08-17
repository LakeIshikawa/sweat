package com.lksoft.yugen.stateful;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.stateless.*;

import java.io.IOException;

/**
 * Created by Lake on 11/06/2016.
 */
public class Fsm extends Sprite {

    // Active
    private boolean active;
    // Name
    private String name;
    // Layer
    private int layer = 5;
    // Control
    private boolean ctrl = true;
    // State time
    private int statetime = 0;
    // Current state
    private int currentState = -1;
    // Anim time
    private int animTime;
    // Anim cycles
    private int animCycles;

    // Hit status
    private HitPack.HitDef currentHit;
    // Attacking hit
    private HitPack.HitDef attackHit;
    // Keys
    private Settings.KeySettings keySettings;
    // AnimationPack
    private AnimationPack animationPack;
    // Hit pack
    private HitPack hitPack;
    // Commands
    private Commands commands;

    // Combo detector
    private CommandDetector commandDetector = new CommandDetector(this);
    // Collision targets
    private Array<Fsm> collisionTargets = new Array<>();

    // Pause time
    private int pauseTime = 0;

    // Rectangle for collision rendering
    private Rectangle collRect = new Rectangle();

    /**
     * Create an fsm from a def with a name
     */
    public Fsm() {
        super(null);
    }

    @Override
    public void update(){
        if( !active ) return;

        // Set initial state
        if( currentState == -1 && getInitialState() != -1 ) {
            changeState(getInitialState());
            return;
        }

        // Pause!
        if( pauseTime > 0 ){
            pauseTime--;
            return;
        }

        // Update input
        commandDetector.update();

        // Update fsm step
        updateFsm();

        // Update sprite
        super.update();
    }

    /**
     * Renders collision rectangles
     * @param shapeRenderer
     */
    public void renderCollision(ShapeRenderer shapeRenderer) {
        if( !active || animation == null ) return;
        shapeRenderer.setColor(Color.WHITE);
        for( Rectangle r : animation.getCurrentFrame().damageCollisions ){
            collRect.set(r);
            getRectWorld(collRect);
            shapeRenderer.rect(collRect.x, collRect.y, collRect.width, collRect.height);
        }

        shapeRenderer.setColor(Color.RED);
        for( Rectangle r : animation.getCurrentFrame().hitCollisions ){
            collRect.set(r);
            getRectWorld(collRect);
            shapeRenderer.rect(collRect.x, collRect.y, collRect.width, collRect.height);
        }
    }

    /**
     * Transform a local rectangle to world coordinates
     * @param r Collision rectangle (Will be modified)
     */
    public void getRectWorld(Rectangle r) {
        if( flip ){
            r.set(pos.x - scale*(r.x + r.width), pos.y + scale*r.y, scale*r.width, scale*r.height);
        } else {
            r.set(pos.x + scale*r.x, pos.y + scale*r.y, scale*r.width, scale*r.height);
        }
    }

    /**
     * @return All the collision targets
     */
    public Array<Fsm> getCollisionTargets(){
        return collisionTargets;
    }


    // FSM API
    // The script-defined updation
    protected void updateFsm() {}
    protected int getInitialState() {return -1;}
    protected void changeState(int newState){}


    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean isActive() {
        return active;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean getCtrl() {
        return ctrl;
    }
    public void setCtrl(boolean ctrl) {
        this.ctrl = ctrl;
    }
    public AnimationDef getAnimation(){ return animation.getAnimationDef(); }
    public void setAnimation(AnimationDef def){
        if( def == null ) return;
        if(animation != null &&  def == animation.getAnimationDef() ) return;
        animation = new Animation(def);
        animTime = 0;
        animCycles = 0;
    }
    public AnimationPack getAnimationPack() { return animationPack; }
    public void setAnimationPack(AnimationPack pack){
        if( pack != null ) {
            this.animationPack = pack;
        }
    }
    public int getLayer() {
        return layer;
    }
    public void setLayer(int layer) {
        int old = getLayer();
        this.layer = layer;
        Yugen.i.layerChanged(this, old);
    }
    public void setCurrentHit(HitPack.HitDef hit){
        this.currentHit = hit;
    }
    public HitPack.HitDef getCurrentHit(){
        return currentHit;
    }
    public HitPack.HitDef getAttackHit() { return attackHit; }
    public void setAttackHit(HitPack.HitDef attackHit) { this.attackHit = attackHit; }
    public Settings.KeySettings getKeySettings(){ return keySettings; }
    public void setKeySettings(Settings.KeySettings settings){ this.keySettings = settings; }

    /**
     * Match specified command agains input
     * @param commandDef The command def to match
     * @return true if matching input
     */
    protected boolean matchCommand(CommandDef commandDef) {
        boolean match = commandDetector.matchCommand(commandDef);
        if( match ){
            commandDetector.clearHistory();
        }
        return match;
    }

    /**
     * @param value
     * @return Abs of value
     */
    protected float abs(float value){
        return Math.abs(value);
    }

    /**
     * Add a collision target
     * @param fsm
     */
    protected void addCollisionTarget(Fsm fsm){
        collisionTargets.add(fsm);
    }

    /**
     * Clear current hit
     */
    protected void clearHit(){
        setCurrentHit(null);
    }

    /**
     * Load fsm from file
     * @param path
     * @param name
     * @return
     */
    protected Fsm loadFSM(String path, String name){
        try {
            return Yugen.i.loadFSM(Gdx.files.internal(path), name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create FSM
     * @param name
     * @return
     */
    protected Fsm createFSM(String name){
        return Yugen.i.createFSM(name);
    }

    /**
     * Destroy FSM
     * @param name
     * @return
     */
    protected void destroyFSM(String name){
        Yugen.i.destroyFSM(name);
    }

    /**
     * Gets an fsm by name
     * @param name
     * @return The fsm
     */
    protected Fsm getFSM(String name){
        return Yugen.i.getFSM(name);
    }

    /**
     * Determines if fsm is facing specified fsm
     * @param fsm
     * @return
     */
    protected boolean facing(Fsm fsm){
        boolean leftOfOpp = pos.x < fsm.pos.x;
        return flip ^ leftOfOpp;
    }

    /**
     * Gets frame height of specified animationdef's 0th frame (first component)
     * @param anim
     * @return
     */
    protected int frameHeight(AnimationDef anim){
        return anim.getFrameAt(0).components.get(0).spriteDef.region.originalHeight;
    }

    /**
     * Gets a property from a hitdef
     * @param name
     * @return
     */
    protected Object hitGet(String name){
        return currentHit.get(name);
    }

    /**
     * Checks for hit property existance
     * @param name
     * @return
     */
    protected boolean hitHas(String name){
        return currentHit.get(name) != null;
    }

    /**
     * @return Wether the fsm has been hit
     */
    protected boolean isHit(){
        return currentHit != null;
    }

    /**
     * Init the camera to specified pos and size
     * @param x
     * @param y
     * @param ww
     * @param wh
     */
    protected void initCamera(int x, int y, int ww, int wh){
        Yugen.i.getCamera().init(x, y, ww, wh);
    }

    /**
     * Sets camera position
     * @param x
     * @param y
     */
    protected void setCamera(int x, int y){
        Yugen.i.getCamera().setPosition(x, y);
    }

    /**
     * Checks for key held
     * @param key
     * @return
     */
    protected boolean keyHold(String key){
        if( keySettings == null ) return false;

        boolean facingRight = !flip;
        switch (key){
            case "U":  return Gdx.input.isKeyPressed(keySettings.up);
            case "D":  return Gdx.input.isKeyPressed(keySettings.down);
            case "B1": return Gdx.input.isKeyPressed(keySettings.b1);
            case "B2": return Gdx.input.isKeyPressed(keySettings.b2);
            case "B3": return Gdx.input.isKeyPressed(keySettings.b3);
            case "B4": return Gdx.input.isKeyPressed(keySettings.b4);
            case "B5": return Gdx.input.isKeyPressed(keySettings.b5);
            case "B6": return Gdx.input.isKeyPressed(keySettings.b6);
            case "F":
                boolean l = Gdx.input.isKeyPressed(keySettings.left);
                boolean r = Gdx.input.isKeyPressed(keySettings.right);
                return (facingRight && r) || (!facingRight && l);
            case "B":
                l = Gdx.input.isKeyPressed(keySettings.left);
                r = Gdx.input.isKeyPressed(keySettings.right);

                return (facingRight && l) || (!facingRight && r);
        }

        return false;
    }

    /**
     * Checks for key pressed
     * @param key
     * @return
     */
    protected boolean keyPress(String key){
        if( keySettings == null ) return false;

        boolean facingRight = !flip;
        switch (key){
            case "U":  return Gdx.input.isKeyJustPressed(keySettings.up);
            case "D":  return Gdx.input.isKeyJustPressed(keySettings.down);
            case "B1": return Gdx.input.isKeyJustPressed(keySettings.b1);
            case "B2": return Gdx.input.isKeyJustPressed(keySettings.b2);
            case "B3": return Gdx.input.isKeyJustPressed(keySettings.b3);
            case "B4": return Gdx.input.isKeyJustPressed(keySettings.b4);
            case "B5": return Gdx.input.isKeyJustPressed(keySettings.b5);
            case "B6": return Gdx.input.isKeyJustPressed(keySettings.b6);
            case "F":
                boolean l = Gdx.input.isKeyJustPressed(keySettings.left);
                boolean r = Gdx.input.isKeyJustPressed(keySettings.right);
                return (facingRight && r) || (!facingRight && l);
            case "B":
                l = Gdx.input.isKeyJustPressed(keySettings.left);
                r = Gdx.input.isKeyJustPressed(keySettings.right);

                return (facingRight && l) || (!facingRight && r);
        }

        return false;
    }

    /**
     * Pause the FSM for the specified number of ticks
     * @param ticks
     */
    protected void pause(int ticks){
        pauseTime = ticks;
    }

    /**
     * Substring check
     * @param string
     * @param sub
     * @return
     */
    protected boolean strHas(String string, String sub){
        return string.contains(sub);
    }
}
