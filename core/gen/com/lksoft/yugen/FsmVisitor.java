// Generated from D:/lksoft/yugen/core/src/main/antlr\Fsm.g4 by ANTLR 4.5.3
package com.lksoft.yugen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FsmParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FsmVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FsmParser#fsm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFsm(FsmParser.FsmContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmd(FsmParser.CmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#commandlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandlist(FsmParser.CommandlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(FsmParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#paramslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamslist(FsmParser.ParamslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(FsmParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#stateless}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStateless(FsmParser.StatelessContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#statesOpt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatesOpt(FsmParser.StatesOptContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#states}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStates(FsmParser.StatesContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#state}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitState(FsmParser.StateContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#triggers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriggers(FsmParser.TriggersContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SystemTrigger}
	 * labeled alternative in {@link FsmParser#trigger}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSystemTrigger(FsmParser.SystemTriggerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SingleCondTrigger}
	 * labeled alternative in {@link FsmParser#trigger}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleCondTrigger(FsmParser.SingleCondTriggerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MultiCondTrigger}
	 * labeled alternative in {@link FsmParser#trigger}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiCondTrigger(FsmParser.MultiCondTriggerContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#triglist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriglist(FsmParser.TriglistContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#trigel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrigel(FsmParser.TrigelContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#statementsOpt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementsOpt(FsmParser.StatementsOptContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(FsmParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStmt(FsmParser.AssignmentStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SwitchStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStmt(FsmParser.SwitchStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IteStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIteStmt(FsmParser.IteStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FCallStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFCallStmt(FsmParser.FCallStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StateChangeStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStateChangeStmt(FsmParser.StateChangeStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#switchcase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchcase(FsmParser.SwitchcaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#caselist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaselist(FsmParser.CaselistContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#scase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScase(FsmParser.ScaseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfThen}
	 * labeled alternative in {@link FsmParser#ite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThen(FsmParser.IfThenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfThenElse}
	 * labeled alternative in {@link FsmParser#ite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElse(FsmParser.IfThenElseContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(FsmParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExp(FsmParser.NotExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExp(FsmParser.AndExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FCallExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFCallExp(FsmParser.FCallExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LtEqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLtEqExp(FsmParser.LtEqExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExp(FsmParser.AddExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GtEqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGtEqExp(FsmParser.GtEqExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GtExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGtExp(FsmParser.GtExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExp(FsmParser.MulExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExp(FsmParser.ParExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CondExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondExp(FsmParser.CondExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLiteral(FsmParser.BoolLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AnimLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnimLiteral(FsmParser.AnimLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExp(FsmParser.OrExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteral(FsmParser.IntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PhysicsLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPhysicsLiteral(FsmParser.PhysicsLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubExp(FsmParser.SubExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NeqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNeqExp(FsmParser.NeqExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdLiteral(FsmParser.IdLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(FsmParser.FloatLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code HitLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHitLiteral(FsmParser.HitLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DivExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivExp(FsmParser.DivExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandLiteral(FsmParser.CommandLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LtExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLtExp(FsmParser.LtExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqExp(FsmParser.EqExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(FsmParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ModExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModExp(FsmParser.ModExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#fcall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFcall(FsmParser.FcallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ElistE}
	 * labeled alternative in {@link FsmParser#elist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElistE(FsmParser.ElistEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EListEElist}
	 * labeled alternative in {@link FsmParser#elist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEListEElist(FsmParser.EListEElistContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#commandline}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandline(FsmParser.CommandlineContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandSeqCToken}
	 * labeled alternative in {@link FsmParser#commandseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandSeqCToken(FsmParser.CommandSeqCTokenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CTokenList}
	 * labeled alternative in {@link FsmParser#commandseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCTokenList(FsmParser.CTokenListContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#ctoken}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCtoken(FsmParser.CtokenContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#exclusiveOpt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusiveOpt(FsmParser.ExclusiveOptContext ctx);
	/**
	 * Visit a parse tree produced by the {@code HoldMod}
	 * labeled alternative in {@link FsmParser#modOpt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHoldMod(FsmParser.HoldModContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReleaseMod}
	 * labeled alternative in {@link FsmParser#modOpt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReleaseMod(FsmParser.ReleaseModContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NoMod}
	 * labeled alternative in {@link FsmParser#modOpt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoMod(FsmParser.NoModContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#timeOpt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeOpt(FsmParser.TimeOptContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#keylist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeylist(FsmParser.KeylistContext ctx);
}