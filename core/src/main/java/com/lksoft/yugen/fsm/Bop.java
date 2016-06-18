package com.lksoft.yugen.fsm;

import com.lksoft.yugen.FsmParser;
import com.lksoft.yugen.fsm.visitor.FighterExpVisitor;
import com.lksoft.yugen.stateless.AnimationDef;
import com.lksoft.yugen.stateless.PhysicsDef;

/**
 * Created by Lake on 12/06/2016.
 */
public enum Bop {
    LTEQ() {
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            relationalOp(this, visitor, left, right);
        }
    },
    GTEQ() {
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            relationalOp(this, visitor, left, right);
        }
    },
    LT() {
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            relationalOp(this, visitor, left, right);
        }
    },
    GT() {
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            relationalOp(this, visitor, left, right);
        }
    },
    EQ() {
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            equalityOp(this, visitor, left, right);
        }
    },
    NEQ() {
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            equalityOp(this, visitor, left, right);
        }
    },
    AND() {
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            logicOp(this, visitor, left, right);
        }
    },
    OR() {
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            logicOp(this, visitor, left, right);
        }
    },
    ADD(){
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            arithOp(this, visitor, left, right);
        }
    },
    SUB(){
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            arithOp(this, visitor, left, right);
        }
    },
    MUL(){
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            arithOp(this, visitor, left, right);
        }
    },
    DIV(){
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            arithOp(this, visitor, left, right);
        }
    },
    MOD(){
        @Override
        public void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
            arithOp(this, visitor, left, right);
        }
    };

    // Perform arithmetical op
    private static void arithOp(Bop bop, FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
        left.accept(visitor);
        if( visitor.getError() != null ) return;

        switch (visitor.getResult().getType()){
            case ID:
            case STRING:
            case BOOL:
            case ANIM:
            case PHYSICS:
                visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + left.getText());
                break;

            case INT:
                int leftInt = visitor.getResult().getIntValue();
                right.accept(visitor);
                if( visitor.getError() != null ) return;
                if( visitor.getResult().getType() != Type.INT ){
                    visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + right.getText());
                }

                switch (bop){
                    case ADD: visitor.setIntResult(leftInt + visitor.getResult().getIntValue()); break;
                    case SUB: visitor.setIntResult(leftInt - visitor.getResult().getIntValue()); break;
                    case MUL: visitor.setIntResult(leftInt * visitor.getResult().getIntValue()); break;
                    case DIV: visitor.setIntResult(leftInt / visitor.getResult().getIntValue()); break;
                    case MOD: visitor.setIntResult(leftInt % visitor.getResult().getIntValue()); break;
                }
                break;
            case FLOAT:
                float leftFloat = visitor.getResult().getFloatValue();
                right.accept(visitor);
                if( visitor.getError() != null ) return;
                if( visitor.getResult().getType() != Type.FLOAT ){
                    visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + right.getText());
                }
                switch (bop){
                    case ADD: visitor.setFloatResult(leftFloat + visitor.getResult().getFloatValue()); break;
                    case SUB: visitor.setFloatResult(leftFloat - visitor.getResult().getFloatValue()); break;
                    case MUL: visitor.setFloatResult(leftFloat * visitor.getResult().getFloatValue()); break;
                    case DIV: visitor.setFloatResult(leftFloat / visitor.getResult().getFloatValue()); break;
                    case MOD: visitor.setFloatResult(leftFloat % visitor.getResult().getFloatValue()); break;
                }
                break;
        }
    }

    // Perform logical op
    private static void logicOp(Bop bop, FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
        left.accept(visitor);
        if( visitor.getError() != null ) return;

        switch (visitor.getResult().getType()){
            case ID:
            case STRING:
            case FLOAT:
            case INT:
            case ANIM:
            case PHYSICS:
                visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + left.getText());
                return;

            case BOOL:
                switch (bop){
                    case AND:
                        if(visitor.getResult().getBoolValue()) {
                            right.accept(visitor);
                        }
                        break;
                    case OR:
                        if( !visitor.getResult().getBoolValue()) {
                            right.accept(visitor);
                        }
                        break;
                }
                break;
        }

        // Result check
        if( visitor.getResult().getType() != Type.BOOL ){
            visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + left.getText());
        }
    }

    // Perform equality BOP
    private static void equalityOp(Bop bop, FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
        left.accept(visitor);
        if( visitor.getError() != null ) return;

        switch (visitor.getResult().getType()){
            case ID:
            case STRING:
                visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + left.getText());
                return;

            case BOOL:
                boolean leftBool = visitor.getResult().getBoolValue();
                right.accept(visitor);
                if( visitor.getError() != null ) return;
                if( visitor.getResult().getType() != Type.BOOL ){
                    visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + right.getText());
                    return;
                }
                switch (bop) {
                    case EQ:
                        visitor.setBoolResult(leftBool == visitor.getResult().getBoolValue());
                        break;
                    case NEQ:
                        visitor.setBoolResult(leftBool != visitor.getResult().getBoolValue());
                        break;
                }
                break;

            case FLOAT:
                float leftFloat = visitor.getResult().getFloatValue();
                right.accept(visitor);
                if( visitor.getError() != null ) return;
                if( visitor.getResult().getType() != Type.FLOAT ){
                    visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + right.getText());
                    return;
                }

                switch (bop) {
                    case EQ:
                        visitor.setBoolResult(leftFloat == visitor.getResult().getFloatValue());
                        break;
                    case NEQ:
                        visitor.setBoolResult(leftFloat != visitor.getResult().getFloatValue());
                        break;
                }
                break;

            case INT:
                int leftInt = visitor.getResult().getIntValue();
                right.accept(visitor);
                if( visitor.getError() != null ) return;
                if( visitor.getResult().getType() != Type.INT ){
                    visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + right.getText());
                    return;
                }

                switch (bop) {
                    case EQ:
                        visitor.setBoolResult(leftInt == visitor.getResult().getIntValue());
                        break;
                    case NEQ:
                        visitor.setBoolResult(leftInt != visitor.getResult().getIntValue());
                        break;
                }
                break;
            case ANIM:
                AnimationDef leftAnim = visitor.getResult().getAnimationValue();
                right.accept(visitor);
                if( visitor.getError() != null ) return;
                if( visitor.getResult().getType() != Type.ANIM ){
                    visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + right.getText());
                    return;
                }

                switch (bop) {
                    case EQ:
                        visitor.setBoolResult(leftAnim == visitor.getResult().getAnimationValue());
                        break;
                    case NEQ:
                        visitor.setBoolResult(leftAnim != visitor.getResult().getAnimationValue());
                        break;
                }
                break;

            case PHYSICS:
                PhysicsDef leftPhy = visitor.getResult().getPhysicsValue();
                right.accept(visitor);
                if( visitor.getError() != null ) return;
                if( visitor.getResult().getType() != Type.PHYSICS ){
                    visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + right.getText());
                    return;
                }

                switch (bop) {
                    case EQ:
                        visitor.setBoolResult(leftPhy == visitor.getResult().getPhysicsValue());
                        break;
                    case NEQ:
                        visitor.setBoolResult(leftPhy != visitor.getResult().getPhysicsValue());
                        break;
                }
                break;
        }
    }

    // Perform relational BOP
    private static void relationalOp(Bop bop, FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right) {
        left.accept(visitor);
        if( visitor.getError() != null ) return;

        switch (visitor.getResult().getType()){
            case ID:
            case STRING:
            case BOOL:
            case ANIM:
            case PHYSICS:
                visitor.setError("Unexpected " + visitor.getResult().getType() + " type exp: " + left.getText());
                break;

            case FLOAT:
                float leftFloat = visitor.getResult().getFloatValue();
                right.accept(visitor);
                if( visitor.getError() != null ) return;
                if( visitor.getResult().getType() != Type.FLOAT ){
                    visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + right.getText());
                    return;
                }

                switch (bop) {
                    case LTEQ: visitor.setBoolResult(leftFloat <= visitor.getResult().getFloatValue()); break;
                    case GTEQ: visitor.setBoolResult(leftFloat >= visitor.getResult().getFloatValue()); break;
                    case LT: visitor.setBoolResult(leftFloat < visitor.getResult().getFloatValue()); break;
                    case GT: visitor.setBoolResult(leftFloat > visitor.getResult().getFloatValue()); break;
                }
                break;

            case INT:
                int leftInt = visitor.getResult().getIntValue();
                right.accept(visitor);
                if( visitor.getError() != null ) return;
                if( visitor.getResult().getType() != Type.INT ){
                    visitor.setError("Unexpected " + visitor.getResult().getType() +" type exp: " + right.getText());
                    return;
                }

                switch (bop) {
                    case LTEQ: visitor.setBoolResult(leftInt <= visitor.getResult().getIntValue()); break;
                    case GTEQ: visitor.setBoolResult(leftInt >= visitor.getResult().getIntValue()); break;
                    case LT: visitor.setBoolResult(leftInt < visitor.getResult().getIntValue()); break;
                    case GT: visitor.setBoolResult(leftInt > visitor.getResult().getIntValue()); break;
                }
                break;
        }
    }

    // Operations
    public abstract void execute(FighterExpVisitor visitor, FsmParser.EContext left, FsmParser.EContext right);
}
