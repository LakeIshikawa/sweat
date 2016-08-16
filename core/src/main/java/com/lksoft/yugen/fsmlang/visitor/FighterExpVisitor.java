package com.lksoft.yugen.fsmlang.visitor;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.fsmlang.Bop;
import com.lksoft.yugen.fsmlang.Functions;
import com.lksoft.yugen.fsmlang.Type;
import com.lksoft.yugen.fsmlang.Value;
import com.lksoft.yugen.stateful.Fsm;
import com.lksoft.yugen.stateless.AnimationDef;
import com.lksoft.yugen.stateless.CommandDef;
import com.lksoft.yugen.stateless.HitPack;
import com.lksoft.yugen.stateless.Settings;

/**
 * Created by Lake on 12/06/2016.
 */
public class FighterExpVisitor extends FsmBaseVisitor<Void>{
    // Results
    private Value result = new Value(Type.BOOL, false);
    private Array<Value> results = new Array<>();

    // Error
    private String error;

    // Fsm parent
    private Fsm fsm;

    // Fsm expression context
    private Fsm fsmContext;

    /**
     * Create an expression evaluator
     * @param fsm Fsm parent of the script
     */
    public FighterExpVisitor(Fsm fsm){
        this.fsm = fsm;
    }

    /**
     * Evaluate an expression
     * @param e expression
     */
    public void evaluate(FsmParser.EContext e){
        error = null;
        getResults().clear();
        e.accept(this);
    }

    @Override
    public Void visitNotExp(FsmParser.NotExpContext ctx) {
        ctx.e().accept(this);
        result.setBoolValue(!result.getBoolValue());
        return null;
    }

    @Override
    public Void visitAddExp(FsmParser.AddExpContext ctx) {
        Bop.ADD.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitSubExp(FsmParser.SubExpContext ctx) {
        Bop.SUB.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitMulExp(FsmParser.MulExpContext ctx) {
        Bop.MUL.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitDivExp(FsmParser.DivExpContext ctx) {
        Bop.DIV.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitModExp(FsmParser.ModExpContext ctx) {
        Bop.MOD.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitAndExp(FsmParser.AndExpContext ctx) {
        Bop.AND.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitOrExp(FsmParser.OrExpContext ctx) {
        Bop.OR.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitLtEqExp(FsmParser.LtEqExpContext ctx) {
        Bop.LTEQ.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitGtEqExp(FsmParser.GtEqExpContext ctx) {
        Bop.GTEQ.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitGtExp(FsmParser.GtExpContext ctx) {
        Bop.GT.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitLtExp(FsmParser.LtExpContext ctx) {
        Bop.LT.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitEqExp(FsmParser.EqExpContext ctx) {
        Bop.EQ.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitNeqExp(FsmParser.NeqExpContext ctx) {
        Bop.NEQ.execute(this, ctx.e(0), ctx.e(1));
        return null;
    }

    @Override
    public Void visitParExp(FsmParser.ParExpContext ctx) {
        ctx.e().accept(this);
        return null;
    }

    @Override
    public Void visitCondExp(FsmParser.CondExpContext ctx){
        ctx.e(0).accept(this);
        if( getError() != null ) return null;
        if( getResult().getType() != Type.BOOL ){
            setError("Conditional expression expects Bool, found " + getResult().getType());
            return null;
        }

        // Branch
        if( getResult().getBoolValue() ){
            ctx.e(1).accept(this);
        } else {
            ctx.e(2).accept(this);
        }

        return null;
    }

    @Override
    public Void visitFCallExp(FsmParser.FCallExpContext ctx) {
        // Execute
        Functions.Function function = Functions.getFunction(ctx.fcall().ID().getText());
        if( function != null ){
            function.execute(this, ctx.fcall());
        } else {
            setError("Unkown function: " + function.getSignature());
        }
        return null;
    }

    @Override
    public Void visitFsmExp(FsmParser.FsmExpContext ctx){
        Fsm fsm = Yugen.i.getFSM(ctx.ID().getText());
        if( fsm == null ){
            setError("FSM not found: " + ctx.ID());
            return null;
        }

        fsmContext = fsm;
        ctx.e().accept(this);
        fsmContext = null;
        return null;
    }

    @Override
    public Void visitFcall(FsmParser.FcallContext ctx) {
        if( ctx.elist() != null ) {
            ctx.elist().accept(this);
        }
        return null;
    }

    @Override
    public Void visitElistE(FsmParser.ElistEContext ctx) {
        ctx.e().accept(this);
        getResults().insert(0, getResult());
        return null;
    }

    @Override
    public Void visitEListEElist(FsmParser.EListEElistContext ctx) {
        ctx.e().accept(this);
        getResults().insert(0, getResult());

        ctx.elist().accept(this);
        return null;
    }

    @Override
    public Void visitIdLiteral(FsmParser.IdLiteralContext ctx) {
        // If the id is defined in memory, return its value
        Value value = getVar(ctx.getText());
        if( value != null ) result.copyFrom(value);
        else setError("Undefined id " + ctx.getText());

        return null;
    }

    @Override
    public Void visitFloatLiteral(FsmParser.FloatLiteralContext ctx) {
        setFloatResult(Float.parseFloat(ctx.getText()));
        return null;
    }

    @Override
    public Void visitIntLiteral(FsmParser.IntLiteralContext ctx) {
        setIntResult(Integer.parseInt(ctx.getText()));
        return null;
    }

    @Override
    public Void visitStringLiteral(FsmParser.StringLiteralContext ctx) {
        setStringResult(ctx.getText().replaceAll("\"", ""));
        return null;
    }

    @Override
    public Void visitBoolLiteral(FsmParser.BoolLiteralContext ctx) {
        setBoolResult(Boolean.parseBoolean(ctx.getText()));
        return null;
    }

    @Override
    public Void visitAnimLiteral(FsmParser.AnimLiteralContext ctx) {
        String name = ctx.ANIM().getText().substring(2);
        AnimationDef animationDef = getAnimationDef(name);
        setAnimResult(animationDef);
        return null;
    }

    @Override
    public Void visitCommandLiteral(FsmParser.CommandLiteralContext ctx) {
        String name = ctx.COMMAND().getText().substring(2);
        CommandDef commandDef = getCommand(name);
        if( commandDef != null ){
            setBoolResult(matchCommand(commandDef));
        } else {
            setError("Command not found: " + name);
        }
        return null;
    }

    @Override
    public Void visitHitLiteral(FsmParser.HitLiteralContext ctx) {
        String name = ctx.HIT().getText().substring(2);
        HitPack.HitDef hitDef = getHitDef(name);
        if( hitDef != null ){
            setHitResult(hitDef);
        } else {
            setError("Hitdef not found: " + name);
        }
        return null;
    }

    @Override
    public Void visitKeysLiteral(FsmParser.KeysLiteralContext ctx){
        String name = ctx.KEYS().getText().substring(2);
        switch (name){
            case "p1": setKeysResult(Yugen.i.getSettings().getP1Keys()); break;
            case "p2": setKeysResult(Yugen.i.getSettings().getP2Keys()); break;
            default:
                setError("Keys not found: " + name);
        }

        return null;
    }

    // Get var value from memory
    private Value getVar(String name){
        Value res = null;
        if( fsmContext != null ) res = fsmContext.getVar(name);
        if( res == null ) res = fsm.getVar(name);
        return res;
    }

    // Get animation from fsm
    private AnimationDef getAnimationDef(String name){
        AnimationDef res = null;
        if( fsmContext != null ) res = fsmContext.getAnimationDef(name);
        if( res == null ) res = fsm.getAnimationDef(name);
        return res;
    }

    // Get hitdef from fsm
    private HitPack.HitDef getHitDef(String name){
        HitPack.HitDef res = null;
        if( fsmContext != null ) res = fsmContext.getHitDef(name);
        if( res == null ) res = fsm.getHitDef(name);
        return res;
    }

    // Get command from fsm
    private CommandDef getCommand(String name){
        CommandDef res = null;
        if( fsmContext != null ) res = fsmContext.getCommand(name);
        if( res == null ) res = fsm.getCommand(name);
        return res;
    }

    // Match command from fsm
    private boolean matchCommand(CommandDef command){
        if( fsmContext != null ) return fsmContext.matchCommand(command);
        else return fsm.matchCommand(command);
    }

    // Get current result
    public Value getResult(){
        return result;
    }
    // Get error message
    public String getError() {return error;}

    public void setError(String error){ this.error = error; }
    public void setBoolResult(boolean b) {
        result.setBoolValue(b);
    }
    public void setIntResult(int i){
        result.setIntValue(i);
    }
    public void setFloatResult(float f){
        result.setFloatValue(f);
    }
    public void setIdResult(String id){
        result.setIdValue(id);
    }
    public void setStringResult(String string){
        result.setStringValue(string);
    }
    public void setAnimResult(AnimationDef animation) { result.setAnimationValue(animation);}
    public void setHitResult(HitPack.HitDef hit) {result.setHitValue(hit);}
    public void setKeysResult(Settings.KeySettings keys) {result.setKeysValue(keys);}
    public void setFsmResult(Fsm fsm) {result.setFsmValue(fsm);}

    public Array<Value> getResults() {
        return results;
    }

    public Fsm getFsm() {
        return fsm;
    }
}
