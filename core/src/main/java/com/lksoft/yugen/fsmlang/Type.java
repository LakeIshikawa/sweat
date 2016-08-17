package com.lksoft.yugen.fsmlang;

/**
 * Created by Lake on 12/06/2016.
 */
public enum Type {
    ID('D'),
    STRING('S'),
    BOOL('B'),
    FLOAT('F'),
    INT('I'),

    ANIMPACK('P'),
    ANIM('A'),
    HIT('H'),
    KEYS('K'),
    FSM('M');

    Type(char idChar){
        this.idChar = idChar;
    }
    private char idChar;
    public char getIdChar() {
        return idChar;
    }
}
