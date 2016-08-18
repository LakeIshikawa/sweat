package com.lksoft.yugen.fsmlang.visitor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.stateful.Fsm;
import com.lksoft.yugen.stateless.AnimationDef;
import com.lksoft.yugen.stateless.HitPack;
import com.lksoft.yugen.stateless.Settings;

import java.lang.reflect.Method;

/**
 * Created by Stallman on 17/08/2016.
 */
public class SymbolTableVisitor extends FsmBaseVisitor<Class> {
    // Symbol table
    private ObjectMap<String, Class> symbolTable = new ObjectMap<>();

    @Override
    public Class visitAssignmentStmt(FsmParser.AssignmentStmtContext ctx) {
        String lhs = ctx.ID().getText();
        Class right = ctx.e().accept(this);
        symbolTable.put(lhs, right);
        return null;
    }

    @Override public Class visitNotExp(FsmParser.NotExpContext ctx) { return ctx.e().accept(this); }
    @Override public Class visitAndExp(FsmParser.AndExpContext ctx) { return Boolean.class; }
    @Override public Class visitFCallExp(FsmParser.FCallExpContext ctx) { return ctx.fcall().accept(this); }
    @Override public Class visitLtEqExp(FsmParser.LtEqExpContext ctx) { return Boolean.class; }
    @Override public Class visitAddExp(FsmParser.AddExpContext ctx) {
        Class left = ctx.e(0).accept(this);
        Class right = ctx.e(1).accept(this);
        return (left == Float.class || right == Float.class) ? Float.class : Integer.class;
    }
    @Override public Class visitGtEqExp(FsmParser.GtEqExpContext ctx) { return Boolean.class; }
    @Override public Class visitGtExp(FsmParser.GtExpContext ctx) { return Boolean.class; }
    @Override public Class visitMulExp(FsmParser.MulExpContext ctx) {
        Class left = ctx.e(0).accept(this);
        Class right = ctx.e(1).accept(this);
        return (left == Float.class || right == Float.class) ? Float.class : Integer.class;
    }
    @Override public Class visitParExp(FsmParser.ParExpContext ctx) { return ctx.e().accept(this); }
    @Override public Class visitCondExp(FsmParser.CondExpContext ctx) { return ctx.e(1).accept(this); }
    @Override public Class visitBoolLiteral(FsmParser.BoolLiteralContext ctx) { return Boolean.class; }
    @Override public Class visitAnimLiteral(FsmParser.AnimLiteralContext ctx) { return AnimationDef.class; }
    @Override public Class visitOrExp(FsmParser.OrExpContext ctx) { return Boolean.class; }
    @Override public Class visitIntLiteral(FsmParser.IntLiteralContext ctx) { return Integer.class; }
    @Override public Class visitSubExp(FsmParser.SubExpContext ctx) {
        Class left = ctx.e(0).accept(this);
        Class right = ctx.e(1).accept(this);
        return (left == Float.class || right == Float.class) ? Float.class : Integer.class;
    }
    @Override public Class visitNeqExp(FsmParser.NeqExpContext ctx) { return Boolean.class; }
    @Override public Class visitIdLiteral(FsmParser.IdLiteralContext ctx) { return symbolTable.get(ctx.ID().getText()); }
    @Override public Class visitFloatLiteral(FsmParser.FloatLiteralContext ctx) { return Float.class; }
    @Override public Class visitHitLiteral(FsmParser.HitLiteralContext ctx) { return HitPack.HitDef.class; }
    @Override public Class visitFsmIdExp(FsmParser.FsmIdExpContext ctx) { return ctx.ID().accept(this); }
    @Override public Class visitKeysLiteral(FsmParser.KeysLiteralContext ctx) { return Settings.KeySettings.class; }
    @Override public Class visitDivExp(FsmParser.DivExpContext ctx) {
        Class left = ctx.e(0).accept(this);
        Class right = ctx.e(1).accept(this);
        return (left == Float.class || right == Float.class) ? Float.class : Integer.class;
    }
    @Override public Class visitCommandLiteral(FsmParser.CommandLiteralContext ctx) { return Boolean.class; }
    @Override public Class visitLtExp(FsmParser.LtExpContext ctx) { return Boolean.class; }
    @Override public Class visitEqExp(FsmParser.EqExpContext ctx) { return visitChildren(ctx); }
    @Override public Class visitStringLiteral(FsmParser.StringLiteralContext ctx) { return String.class; }
    @Override public Class visitModExp(FsmParser.ModExpContext ctx) { Class left = ctx.e(0).accept(this);
        Class right = ctx.e(1).accept(this);
        return (left == Float.class || right == Float.class) ? Float.class : Integer.class;
    }
    @Override public Class visitFcall(FsmParser.FcallContext ctx) {
        // Find method (No overloading)
        Method[] methods = Fsm.class.getDeclaredMethods();
        for( Method m : methods ){
            if( m.getName().equals(ctx.ID().getText()) ){
                return m.getReturnType();
            }
        }

        Gdx.app.error("FSM", "Function not found: " + ctx.ID().getText());
        return null;
    }

    // Accessors
    public ObjectMap<String, Class> getSymbolTable() {
        return symbolTable;
    }
}
