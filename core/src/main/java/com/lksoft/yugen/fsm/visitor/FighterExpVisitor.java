package com.lksoft.yugen.fsm.visitor;

import com.badlogic.gdx.utils.Array;
import com.lksoft.yugen.FsmBaseVisitor;
import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.fsm.Bop;
import com.lksoft.yugen.fsm.Functions;
import com.lksoft.yugen.fsm.Type;
import com.lksoft.yugen.fsm.Value;
import com.lksoft.yugen.stateful.Fighter;
import com.lksoft.yugen.stateless.AnimationDef;
import com.lksoft.yugen.stateless.CommandDef;
import com.lksoft.yugen.stateless.PhysicsDef;

/**
 * Created by Lake on 12/06/2016.
 */
public class FighterExpVisitor extends FsmBaseVisitor<Void>{
    // Results
    private Value result = new Value(Type.BOOL, false);
    private Array<Value> results = new Array<>();

    // Error
    private String error;

    // Fighter parent
    private Fighter fighter;

    /**
     * Create an expression evaluator
     * @param fighter Fighter parent of the script
     */
    public FighterExpVisitor(Fighter fighter){
        this.fighter = fighter;
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
        Value value = fighter.getVar(ctx.getText());
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
        AnimationDef animationDef = fighter.getAnimationDef(name);
        setAnimResult(animationDef);
        return null;
    }

    @Override
    public Void visitPhysicsLiteral(FsmParser.PhysicsLiteralContext ctx) {
        String name = ctx.PHYSICS().getText().substring(2);
        PhysicsDef physicsDef = Yugen.i().getPhysicsDef(name);
        setPhysicsResult(physicsDef);
        return null;
    }

    @Override
    public Void visitCommandLiteral(FsmParser.CommandLiteralContext ctx) {
        String name = ctx.COMMAND().getText().substring(2);
        CommandDef commandDef = fighter.getCommand(name);
        if( commandDef != null ){
            setBoolResult(fighter.matchCommand(commandDef));
        } else {
            setError("Command not found: " + name);
        }
        return null;
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
    public void setPhysicsResult(PhysicsDef physics) {result.setPhysicsValue(physics);}

    public Array<Value> getResults() {
        return results;
    }

    public Fighter getFighter() {
        return fighter;
    }
}