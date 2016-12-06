package com.lksoft.sweat.stateful;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.lksoft.sweat.Resources;
import com.lksoft.sweat.Sweat;
import com.lksoft.sweat.stateless.*;

import java.io.IOException;

/**
 * Created by Lake on 11/06/2016.
 */
public abstract class Fsm<FsmClass, StateClass extends State<FsmClass>, HitClass> extends Sprite {

    // The state machine
    private DefaultStateMachine<FsmClass, StateClass> stateMachine =
            (DefaultStateMachine<FsmClass, StateClass>) new DefaultStateMachine<>((FsmClass)this);

    // Active
    private boolean active = true;
    // Name
    private String name;
    // Layer
    private int layer = 5;
    // Control
    private boolean ctrl = true;
    // State time
    private int statetime = 0;

    // Hit status
    private HitClass hit;
    // Attacking hit
    private HitClass attackHit;
    // Keys
    private Settings.KeySettings keySettings;
    // AnimationPack
    private AnimationPack animationPack;

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

        // Load animation pack if specified
        FsmResources resources = getClass().getAnnotation(FsmResources.class);
        if( resources != null && resources.anm() != null ){
            loadAnimationPack(resources.anm());
        }
    }

    @Override
    public void update(){
        if( !active ) return;

        // Set initial state if needed
        if( stateMachine.getCurrentState() == null ){
            changeState(getInitialState());
        }

        // Pause!
        if( pauseTime > 0 ){
            pauseTime--;
            return;
        }

        // Update input
        commandDetector.update();

        // Update state machine
        stateMachine.update();
        statetime++;
        // Stateless update
        statelessUpdate();

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

    /**
     * Dispose FSM resouces
     */
    public void dispose(){
        if( animationPack != null ){
            animationPack.release();
        }
    }

    // FSM API
    protected abstract StateClass getInitialState();
    protected abstract void statelessUpdate();

    public void changeState(StateClass newState){
        stateMachine.changeState(newState);
        statetime = 0;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean getActive() {
        return active;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isCtrl() {
        return ctrl;
    }
    public void setCtrl(boolean ctrl) {
        this.ctrl = ctrl;
    }
    public AnimationDef getAnimation(String name){ return animationPack.getAnimationDef(name);}
    public AnimationDef getAnimation(){ return animation.getAnimationDef(); }
    public void setAnimation(AnimationDef def){
        if( def == null ) return;
        if(animation != null &&  def == animation.getAnimationDef() ) return;
        animation = new Animation(def);
    }
    public void setAnimation(String name){
        setAnimation(getAnimation(name));
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
        if( old == layer ) return;
        this.layer = layer;
        Sweat.i.layerChanged(this, old);
    }
    public void setHit(HitClass hit){
        this.hit = hit;
    }
    public HitClass getHit(){
        return hit;
    }
    public HitClass getAttackHit() { return attackHit; }
    public void setAttackHit(HitClass attackHit) { this.attackHit = attackHit; }
    public Settings.KeySettings getKeySettings(){ return keySettings; }
    public void setKeySettings(Settings.KeySettings settings){ this.keySettings = settings; }
    public int getStatetime() {
        return statetime;
    }
    public int getAnimTime() {
        return animation.getTotalTime();
    }
    public int getAnimCycles() {
        return animation.getCycles();
    }

    /**
     * Read and set animation pack to current pack
     * @param path
     */
    public void loadAnimationPack(String path){
        this.animationPack = Resources.loadAnimationPack(path);
    }

    /**
     * Match specified command agains input
     * @param commandDef The command def to match
     * @return true if matching input
     */
    public boolean matchCommand(CommandDef commandDef) {
        boolean match = commandDetector.matchCommand(commandDef);
        if( match ){
            commandDetector.clearHistory();
        }
        return match;
    }

    /**
     * Add a collision target
     * @param fsm
     */
    public void addCollisionTarget(Fsm fsm){
        collisionTargets.add(fsm);
    }

    /**
     * Clear current hit
     */
    public void clearHit(){
        setHit(null);
    }

    /**
     * Load fsm from file
     * @param path
     * @param name
     * @return
     */
    public Fsm loadFSM(String path, String name){
        try {
            return Sweat.i.loadFSM(Gdx.files.internal(path), name);
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
    public Fsm createFSM(String name){
        return Sweat.i.createFSM(name);
    }

    /**
     * Destroy FSM
     * @param name
     * @return
     */
    public void destroyFSM(String name){
        Sweat.i.destroyFSM(name);
    }

    /**
     * Gets an fsm by name
     * @param name
     * @return The fsm
     */
    public Fsm getFSM(String name){
        return Sweat.i.getFSM(name);
    }

    /**
     * Load a scene
     * @param path
     * @return The scene
     */
    public SceneDef loadScene(String path){
        try {
            return Sweat.i.loadScene(Gdx.files.internal(Resources.BIN_FOLDER + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Determines if fsm is facing specified fsm
     * @param fsm
     * @return
     */
    public boolean facing(Fsm fsm){
        boolean leftOfOpp = pos.x < fsm.pos.x;
        return flip ^ leftOfOpp;
    }

    /**
     * Gets frame height of specified animationdef's 0th frame (first component)
     * @param anim
     * @return
     */
    public int frameHeight(AnimationDef anim){
        return anim.getFrameAt(0).components.get(0).spriteDef.region.originalHeight;
    }

    /**
     * @return Wether the fsm has been hit
     */
    public boolean isHit(){
        return hit != null;
    }

    /**
     * Init the camera to specified pos and size
     * @param x
     * @param y
     * @param ww
     * @param wh
     */
    public void initCamera(int x, int y, int ww, int wh){
        Sweat.i.getCamera().init(x, y, ww, wh);
    }

    /**
     * Sets camera position
     * @param x
     * @param y
     */
    public void setCamera(int x, int y){
        Sweat.i.getCamera().setPosition(x, y);
    }

    /**
     * @return Camera position
     */
    public Vector3 getCameraPos() {
        return Sweat.i.getCamera().getCamera().position;
    }

    /**
     * Checks for key held
     * @param key
     * @return
     */
    public boolean keyHold(String key){
        if( keySettings == null ) return false;

        boolean facingRight = !flip;
        switch (key){
            case "U":  return keySettings.up != null && Gdx.input.isKeyPressed(keySettings.up.key);
            case "D":  return keySettings.down != null && Gdx.input.isKeyPressed(keySettings.down.key);
            case "B1": return keySettings.b1 != null && Gdx.input.isKeyPressed(keySettings.b1.key);
            case "B2": return keySettings.b2 != null && Gdx.input.isKeyPressed(keySettings.b2.key);
            case "B3": return keySettings.b3 != null && Gdx.input.isKeyPressed(keySettings.b3.key);
            case "B4": return keySettings.b4 != null && Gdx.input.isKeyPressed(keySettings.b4.key);
            case "B5": return keySettings.b5 != null && Gdx.input.isKeyPressed(keySettings.b5.key);
            case "B6": return keySettings.b6 != null && Gdx.input.isKeyPressed(keySettings.b6.key);
            case "F":
                boolean l = keySettings.left != null && Gdx.input.isKeyPressed(keySettings.left.key);
                boolean r = keySettings.right != null && Gdx.input.isKeyPressed(keySettings.right.key);
                return (facingRight && r) || (!facingRight && l);
            case "B":
                l = keySettings.left != null && Gdx.input.isKeyPressed(keySettings.left.key);
                r = keySettings.right!= null && Gdx.input.isKeyPressed(keySettings.right.key);

                return (facingRight && l) || (!facingRight && r);
        }

        return false;
    }

    /**
     * Checks for key pressed
     * @param key
     * @return
     */
    public boolean keyPress(String key){
        if( keySettings == null ) return false;

        boolean facingRight = !flip;
        switch (key){
            case "U":  return keySettings.up != null && Gdx.input.isKeyJustPressed(keySettings.up.key);
            case "D":  return keySettings.down != null && Gdx.input.isKeyJustPressed(keySettings.down.key);
            case "B1": return keySettings.b1 != null && Gdx.input.isKeyJustPressed(keySettings.b1.key);
            case "B2": return keySettings.b2 != null && Gdx.input.isKeyJustPressed(keySettings.b2.key);
            case "B3": return keySettings.b3 != null && Gdx.input.isKeyJustPressed(keySettings.b3.key);
            case "B4": return keySettings.b4 != null && Gdx.input.isKeyJustPressed(keySettings.b4.key);
            case "B5": return keySettings.b5 != null && Gdx.input.isKeyJustPressed(keySettings.b5.key);
            case "B6": return keySettings.b6 != null && Gdx.input.isKeyJustPressed(keySettings.b6.key);
            case "F":
                boolean l = keySettings.left != null && Gdx.input.isKeyJustPressed(keySettings.left.key);
                boolean r = keySettings.right != null && Gdx.input.isKeyJustPressed(keySettings.right.key);
                return (facingRight && r) || (!facingRight && l);
            case "B":
                l = keySettings.left != null && Gdx.input.isKeyJustPressed(keySettings.left.key);
                r = keySettings.right != null && Gdx.input.isKeyJustPressed(keySettings.right.key);

                return (facingRight && l) || (!facingRight && r);
        }

        return false;
    }

    /**
     * Pause the FSM for the specified number of ticks
     * @param ticks
     */
    public void pause(int ticks){
        pauseTime = ticks;
    }

    /**
     * @return Sweat settings
     */
    public Settings getSettings(){
        return Sweat.i.getSettings();
    }
}
