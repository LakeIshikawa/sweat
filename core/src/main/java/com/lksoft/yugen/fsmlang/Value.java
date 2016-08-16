package com.lksoft.yugen.fsmlang;

import com.lksoft.yugen.stateful.Fsm;
import com.lksoft.yugen.stateless.AnimationDef;
import com.lksoft.yugen.stateless.AnimationPack;
import com.lksoft.yugen.stateless.HitPack;
import com.lksoft.yugen.stateless.Settings;

/**
 * Created by Lake on 12/06/2016.
 */
public class Value {
    private String idValue;
    private String stringValue;
    private boolean boolValue;
    private float floatValue;
    private int intValue;
    private AnimationPack animationPackValue;
    private AnimationDef animationValue;
    private HitPack.HitDef hitValue;
    private Settings.KeySettings keysValue;
    private Fsm fsmValue;

    // Type result
    private Type type;

    /**
     * Explicit value creation
     * @param type
     * @param value
     */
    public Value(Type type, Object value) {
        set(type, value);
    }

    /**
     * Copy constructor
     * @param value
     */
    public Value(Value value){
        copyFrom(value);
    }

    @Override
    public boolean equals(Object other){
        Value otherv = (Value) other;
        if( otherv.getType() != getType() ) return false;
        else switch (type){
            case ID: return getIdValue().equals(otherv.getIdValue());
            case STRING: return getStringValue().equals(otherv.getStringValue());
            case BOOL: return getBoolValue() == otherv.getBoolValue();
            case FLOAT: return getFloatValue() == otherv.getFloatValue();
            case INT: return getIntValue() == otherv.getIntValue();
            case ANIM: return getAnimationValue() == otherv.getAnimationValue();
            case HIT: return getHitValue() == otherv.getHitValue();
            case KEYS: return getKeysValue() == otherv.getKeysValue();
            case FSM: return getFsmValue() == otherv.getFsmValue();
            case ANIMPACK: return getAnimPackValue() == otherv.getAnimPackValue();
        }
        return false;
    }

    /**
     * Set value
     * @param type
     * @param value
     */
    public void set(Type type, Object value) {
        this.type = type;
        switch (type){
            case ID: idValue = (String) value; break;
            case STRING: stringValue = (String) value; break;
            case BOOL: boolValue = (boolean) value; break;
            case FLOAT: floatValue = (float) value; break;
            case INT: intValue = (int) value; break;
            case ANIM: animationValue = (AnimationDef) value; break;
            case HIT: hitValue= (HitPack.HitDef) value; break;
            case KEYS: keysValue= (Settings.KeySettings) value; break;
            case FSM: fsmValue = (Fsm) value; break;
            case ANIMPACK: animationPackValue= (AnimationPack) value; break;
        }
    }

    /**
     * Copy type and value from other value
     * @param value
     */
    public void copyFrom(Value value) {
        this.type = value.getType();
        switch (type){
            case ID: idValue = value.getIdValue(); break;
            case STRING: stringValue = value.getStringValue(); break;
            case BOOL: boolValue = value.getBoolValue(); break;
            case FLOAT: floatValue = value.getFloatValue(); break;
            case INT: intValue = value.getIntValue(); break;
            case ANIM: animationValue = value.getAnimationValue(); break;
            case HIT: hitValue = value.getHitValue(); break;
            case KEYS: keysValue = value.getKeysValue(); break;
            case FSM: fsmValue = value.getFsmValue(); break;
            case ANIMPACK: animationPackValue = value.getAnimPackValue(); break;
        }
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
        type = Type.ID;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
        type = Type.STRING;
    }

    public boolean getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
        type = Type.BOOL;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
        type = Type.FLOAT;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
        type = Type.INT;
    }

    public AnimationPack getAnimPackValue() {
        return animationPackValue;
    }
    public void setAnimationPackValue(AnimationPack animationPack) {
        this.animationPackValue = animationPack;
        type = Type.ANIM;
    }

    public AnimationDef getAnimationValue() {
        return animationValue;
    }
    public void setAnimationValue(AnimationDef animationValue) {
        this.animationValue = animationValue;
        type = Type.ANIM;
    }

    public HitPack.HitDef getHitValue(){
        return hitValue;
    }
    public void setHitValue(HitPack.HitDef hit){
        this.hitValue = hit;
        type = Type.HIT;
    }

    public Settings.KeySettings getKeysValue(){
        return keysValue;
    }
    public void setKeysValue(Settings.KeySettings keys){
        this.keysValue = keys;
        type = Type.KEYS;
    }

    public Fsm getFsmValue(){
        return fsmValue;
    }
    public void setFsmValue(Fsm fsm){
        this.fsmValue = fsm;
        type = Type.FSM;
    }

    public Type getType() {
        return type;
    }
}
