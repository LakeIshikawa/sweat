package com.lksoft.yugen.fsmlang.visitor;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.fsmlang.*;
import com.lksoft.yugen.stateful.Fsm;
import com.lksoft.yugen.stateless.AnimationDef;
import com.lksoft.yugen.stateless.CommandDef;
import com.lksoft.yugen.stateless.HitPack;
import com.lksoft.yugen.stateless.Settings;

import java.util.Stack;

/**
 * Created by Lake on 12/06/2016.
 */
public class FighterExpVisitor extends FsmBaseVisitor<Void>{
    // Results
    private Value result = new Value(Type.BOOL, false);

    // Argvalues
    private Stack<Value> argStack = new Stack<>();
    private Stack<Integer> argNum = new Stack<>();
    private Array<Value> argValues = new Array<>();

    // Error
    private String error;

    // Fsm parent
    private Stack<Fsm> memory;

    /**
     * Create an expression evaluator
     * @param memory The stack memory
     */
    public FighterExpVisitor(Stack<Fsm> memory){
        this.memory = memory;
    }

    /**
     * Evaluate an expression
     * @param e expression
     */
    public void evaluate(FsmParser.EContext e){
        error = null;
        argStack.clear();
        argNum.clear();
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
    public Void visitFsmExp(FsmParser.FsmExpContext ctx){
        Fsm fsm = Yugen.i.getFSM(ctx.ID().getText());
        if( fsm == null ){
            setError("FSM not found: " + ctx.ID());
            return null;
        }

        memory.push(fsm);
        ctx.e().accept(this);
        memory.pop();
        return null;
    }

    @Override
    public Void visitFcall(FsmParser.FcallContext ctx) {
        // Evaluate arguments
        argNum.push(0);
        if( ctx.elist() != null ) {
            ctx.elist().accept(this);
        }
        if( getError() != null ) return null;

        // Get number of arguments
        int args = argNum.pop();
        // Make signature
        String signature = ctx.ID().getText();
        argValues.clear();
        for( int i=0; i<args; i++ ){
            Value v = argStack.pop();
            argValues.insert(0, v);
            signature += v.getType().getIdChar();
        }

        // Find function
        Function f = Functions.getFunction(signature);

        if( f == null ){
            setError("Unkown function: " + signature);
            return null;
        }

        f.execute(argValues, this);
        return null;
    }

    @Override
    public Void visitElistE(FsmParser.ElistEContext ctx) {
        ctx.e().accept(this);
        argStack.push(new Value(getResult()));
        argNum.push(argNum.pop()+1);
        return null;
    }

    @Override
    public Void visitEListEElist(FsmParser.EListEElistContext ctx) {
        ctx.e().accept(this);
        argStack.push(new Value(getResult()));
        argNum.push(argNum.pop()+1);

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
    Value getVar(String name){
        Value res = null;
        for( int i=memory.size()-1; i>=0; i-- ){
            Fsm mem = memory.get(i);
            res = mem.getVar(name);
            if( res != null ) return res;
        }

        return null;
    }

    // Get animation from fsm
    private AnimationDef getAnimationDef(String name){
        AnimationDef res = null;
        for( int i=memory.size()-1; i>=0; i++ ){
            Fsm mem = memory.get(i);
            res = mem.getAnimationDef(name);
            if( res != null ) return res;
        }

        return null;
    }

    // Get hitdef from fsm
    private HitPack.HitDef getHitDef(String name){
        HitPack.HitDef res = null;
        for( int i=memory.size()-1; i>=0; i++ ){
            Fsm mem = memory.get(i);
            res = mem.getHitDef(name);
            if( res != null ) return res;
        }

        return null;
    }

    // Get command from fsm
    private CommandDef getCommand(String name){
        CommandDef res = null;
        for( int i=memory.size()-1; i>=0; i++ ){
            Fsm mem = memory.get(i);
            res = mem.getCommand(name);
            if( res != null ) return res;
        }

        return null;
    }

    // Match command from fsm
    private boolean matchCommand(CommandDef command){
        return memory.peek().matchCommand(command);
    }

    // Get current topping memory
    public Fsm getTargetFsm(){
        return memory.peek();
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
}
