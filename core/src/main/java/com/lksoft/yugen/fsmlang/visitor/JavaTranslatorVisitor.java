package com.lksoft.yugen.fsmlang.visitor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.StringBuilder;
import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.stateful.Fsm;
import com.lksoft.yugen.stateless.HitPack;
import com.lksoft.yugen.stateless.Settings;

import java.io.IOException;

/**
 * Created by Stallman on 17/08/2016.
 */
public class JavaTranslatorVisitor extends FsmBaseVisitor<String> {

    // Class name
    private String className;
    // Symbol table
    private ObjectMap<String, Class> symbolTable;
    // State table
    private ObjectMap<String, Integer> stateIds = new ObjectMap<>();
    private int id = 0;

    // Indent level
    private int indentationLevel = 0;

    // Current state
    private String currentStateName;
    // Current system triggers
    private String enterTrigger;
    private String exitTrigger;

    // Context stack


    // System vars
    private Array<String> systemVars = new Array<>(new String[]{
        "pos.x", "pos.y", "vel.x", "vel.y", "scrollFactor.x", "scrollFactor.y",
            "anim", "animpack", "layer", "name", "ctrl", "attackhit", "keys", "active"});

    /**
     * Create translation visitor with symbol table
     * @param symbolTable
     */
    public JavaTranslatorVisitor(ObjectMap<String, Class> symbolTable, String className) {
        this.symbolTable = symbolTable;
        this.className = className;
    }


    @Override public String visitFsm(FsmParser.FsmContext ctx) {
        String extendName = ctx.includeOpt().accept(this);
        if( extendName == null ) extendName = "Fsm";

        // Write header
        StringBuilder header = new StringBuilder(
                "package com.lksoft.yugen.fsmlang;\n" +
                "import com.lksoft.yugen.stateful.Fsm;\n\n" +
                "public class " + className + " extends "+extendName+" {\n");
        for( ObjectMap.Entry e : symbolTable ){
            header.append("\t");
            if( e.value == Float.class ){
                header.append("float ");
            } else if( e.value == Integer.class ){
                header.append("int ");
            } else if( e.value == String.class ){
                header.append("String ");
            } else if( e.value == Settings.KeySettings.class ){
                header.append("KeySettings ");
            } else if( e.value == HitPack.HitDef.class ){
                header.append("HitPack.HitDef ");
            } else if( e.value == Fsm.class ){
                header.append("Fsm ");
            }
            header.append(e.key);
            header.append(";\n");
        }

        // Go parse states
        String states = ctx.statesOpt().accept(this);

        // Write footer
        String footer = "}\n";
        return header + "\n" + states + "\n" + footer;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public String visitIncludeOpt(FsmParser.IncludeOptContext ctx) {
        if( ctx.STRING() == null ) return null;

        // Read and load fsm
        String path = ctx.STRING().getText();
        FileHandle handle = Gdx.files.internal(path);

        try {
            Yugen.i.loadFSMClass(handle);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return handle.nameWithoutExtension();
    }

    @Override public String visitStateless(FsmParser.StatelessContext ctx) { return ctx.triggers().accept(this); }
    @Override public String visitStatesOpt(FsmParser.StatesOptContext ctx) { return ctx.states().accept(this); }

    @Override public String visitStateStart(FsmParser.StateStartContext ctx) {
        currentStateName = ctx.ID().getText();

        enterTrigger = "\tprivate void " + currentStateName + "Enter(){}\n\n";
        exitTrigger = "\tprivate void " + currentStateName + "Exit(){}\n\n";
        indentationLevel++;
        String body = ctx.triggers().accept(this);
        indentationLevel--;

        int newid = id++;
        stateIds.put(currentStateName, newid);

        String init = "\t@Override\n\tpublic int getInitialState(){ return " + newid + "; }\n\n";
        String f = "\tprivate void " + currentStateName + "(){\n" + body + "\t}\n\n";

        return init + f + enterTrigger + exitTrigger;
    }
    @Override public String visitStateNormal(FsmParser.StateNormalContext ctx) {
        String name = ctx.ID().getText();

        enterTrigger = "";
        exitTrigger = "";
        indentationLevel++;
        String body = ctx.triggers().accept(this);
        indentationLevel--;

        int newid = id++;
        stateIds.put(name, newid);

        String f = "\tprivate void " + name + "(){\n" + body + "\t}\n\n";
        return f + enterTrigger + exitTrigger;
    }
    @Override public String visitSystemTrigger(FsmParser.SystemTriggerContext ctx) {
        String id = ctx.ID().getText();
        indentationLevel++;
        String body = ctx.statementsOpt().accept(this);
        indentationLevel--;

        switch (id){
            case "Enter": enterTrigger = idt() + "private void " + currentStateName + "Enter(){\n" + body + idt() + "}\n\n"; break;
            case "Exit": exitTrigger = idt() + "private void " + currentStateName + "Exit(){\n" + body + idt() + "}\n\n"; break;
        }
        return "";
    }
    @Override public String visitSingleCondTrigger(FsmParser.SingleCondTriggerContext ctx) {
        indentationLevel++;
        String statements = ctx.statementsOpt().accept(this);
        indentationLevel--;
        return idt() + "if(" + ctx.e().accept(this) + ") {\n" +
                statements + idt()+"}\n\n";
    }
    @Override public String visitStatements(FsmParser.StatementsContext ctx){
        if( ctx.statements() == null ) return ctx.statement().accept(this);
        return ctx.statement().accept(this) + ctx.statements().accept(this);
    }
    @Override public String visitAssignmentStmt(FsmParser.AssignmentStmtContext ctx) {
        String lhs = ctx.ID().getText();
        String e = ctx.e().accept(this);

        if( systemVars.contains(lhs, false) ){
            lhs = lhs.substring(0, 1).toUpperCase() + lhs.substring(1);
            return idt() + "set"+lhs+ "(" + e + ");\n";
        } else {
            return idt() + lhs + " = " + e + ";\n";
        }
    }
    @Override public String visitFsmAssignmentStmt(FsmParser.FsmAssignmentStmtContext ctx) {
        String context = ctx.ID(0).getText();
        String id = ctx.ID(1).getText();
        String e = ctx.e().accept(this);

        return idt() + context+"."+id+" = "  + e;
    }
    @Override public String visitSwitchStmt(FsmParser.SwitchStmtContext ctx) {
        return ctx.switchcase().accept(this);
    }
    @Override public String visitIteStmt(FsmParser.IteStmtContext ctx) {
        return ctx.ite().accept(this);
    }
    @Override public String visitFCallStmt(FsmParser.FCallStmtContext ctx) {
        return idt() + ctx.fcall().accept(this) + ";\n";
    }
    @Override public String visitFsmFCallStmt(FsmParser.FsmFCallStmtContext ctx) {
        String context = ctx.ID().getText();
        return idt() + context + "." + ctx.fcall().accept(this) + ";\n";
    }
    @Override public String visitStateChangeStmt(FsmParser.StateChangeStmtContext ctx) {
        int sid = stateIds.get(ctx.ID().getText());
        return idt()+"changeState(" + sid + ");\n";
    }
    @Override public String visitSwitchcase(FsmParser.SwitchcaseContext ctx) {
        String e = ctx.e().accept(this);
        indentationLevel++;
        String stmt = ctx.caselist().accept(this);
        indentationLevel--;

        return idt() + "switch {\n" + stmt + idt()+"}\n\n";
    }
    @Override public String visitCaselist(FsmParser.CaselistContext ctx) {
        if( ctx.caselist() == null ) return ctx.scase().accept(this);
        return ctx.scase().accept(this) + ctx.caselist().accept(this);
    }
    @Override public String visitScase(FsmParser.ScaseContext ctx) {
        indentationLevel++;
        String stmts = ctx.statements().accept(this);
        indentationLevel--;
        return idt() + "case " + ctx.e().accept(this) + ":\n" + stmts + idt() + "\tbreak;\n";
    }
    @Override public String visitIfThen(FsmParser.IfThenContext ctx) {
        String e = ctx.e().accept(this);
        indentationLevel++;
        String iff = ctx.statements().accept(this);
        indentationLevel--;

        return idt() + "if( " + e + " ){\n" + iff + idt()+"}\n\n";
    }
    @Override public String visitIfThenElse(FsmParser.IfThenElseContext ctx) {
        String e = ctx.e().accept(this);
        indentationLevel++;
        String iff = ctx.statements(0).accept(this);
        String elsee = ctx.statements(1).accept(this);
        indentationLevel--;

        return idt() + "if( " + e + " ){\n" + iff + idt()+"} else {\n" + elsee + idt()+"}\n\n";
    }
    @Override public String visitNotExp(FsmParser.NotExpContext ctx) {
        return "!"+ctx.e().accept(this);
    }
    @Override public String visitAndExp(FsmParser.AndExpContext ctx) {
        return ctx.e(0) + "&&" + ctx.e(1);
    }
    @Override public String visitFCallExp(FsmParser.FCallExpContext ctx) {
        return ctx.fcall().accept(this);
    }
    @Override public String visitLtEqExp(FsmParser.LtEqExpContext ctx) {
        return ctx.e(0) + "<=" + ctx.e(1);
    }
    @Override public String visitAddExp(FsmParser.AddExpContext ctx) {
        return ctx.e(0) + "+" + ctx.e(1);
    }
    @Override public String visitGtEqExp(FsmParser.GtEqExpContext ctx) {
        return ctx.e(0) + ">=" + ctx.e(1);
    }
    @Override public String visitGtExp(FsmParser.GtExpContext ctx) {
        return ctx.e(0) + ">" + ctx.e(1);
    }
    @Override public String visitMulExp(FsmParser.MulExpContext ctx) {
        return ctx.e(0) + "*" + ctx.e(1);
    }
    @Override public String visitParExp(FsmParser.ParExpContext ctx) {
        return "(" + ctx.e().accept(this) + ")";
    }
    @Override public String visitCondExp(FsmParser.CondExpContext ctx) {
        return ctx.e(0).accept(this) + "?" + ctx.e(1).accept(this) + "+" + ctx.e(2).accept(this);
    }
    @Override public String visitBoolLiteral(FsmParser.BoolLiteralContext ctx) {
        return ctx.BOOL().getText();
    }
    @Override public String visitAnimLiteral(FsmParser.AnimLiteralContext ctx) {
        return "animationPack.getAnimationDef("+ctx.ANIM().getText().substring(2)+")";
    }
    @Override public String visitOrExp(FsmParser.OrExpContext ctx) {
        return ctx.e(0) + "||" + ctx.e(1);
    }
    @Override public String visitIntLiteral(FsmParser.IntLiteralContext ctx) {
        return ctx.INT().getText();
    }
    @Override public String visitSubExp(FsmParser.SubExpContext ctx) {
        return ctx.e(0) + "-" + ctx.e(1);
    }
    @Override public String visitNeqExp(FsmParser.NeqExpContext ctx) {
        return ctx.e(0) + "!=" + ctx.e(1);
    }
    @Override public String visitIdLiteral(FsmParser.IdLiteralContext ctx) {
        return ctx.ID().getText();
    }
    @Override public String visitFloatLiteral(FsmParser.FloatLiteralContext ctx) {
        return ctx.FLOAT().getText();
    }
    @Override public String visitHitLiteral(FsmParser.HitLiteralContext ctx) {
        return "hitPack.getHitDef(" + ctx.HIT().getText().substring(2) + ")";
    }
    @Override public String visitFsmExp(FsmParser.FsmExpContext ctx) {
        String context = ctx.ID().getText();
        return
    }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitKeysLiteral(FsmParser.KeysLiteralContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitDivExp(FsmParser.DivExpContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitCommandLiteral(FsmParser.CommandLiteralContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitLtExp(FsmParser.LtExpContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitEqExp(FsmParser.EqExpContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitStringLiteral(FsmParser.StringLiteralContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitModExp(FsmParser.ModExpContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitFcall(FsmParser.FcallContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitElistE(FsmParser.ElistEContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitEListEElist(FsmParser.EListEElistContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitCommandline(FsmParser.CommandlineContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitCommandSeqCToken(FsmParser.CommandSeqCTokenContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitCTokenList(FsmParser.CTokenListContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitCtoken(FsmParser.CtokenContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitExclusiveOpt(FsmParser.ExclusiveOptContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitHoldMod(FsmParser.HoldModContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitReleaseMod(FsmParser.ReleaseModContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitNoMod(FsmParser.NoModContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitTimeOpt(FsmParser.TimeOptContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitKeylist(FsmParser.KeylistContext ctx) { return visitChildren(ctx); }

    // Indent
    private String idt(){
        StringBuilder res = new StringBuilder();
        for( int i=0; i<indentationLevel; i++ ){
            res.append("\t");
        }
        return res.toString();
    }
}