// Generated from E:/Development/yugen/core/src/main/antlr\Fsm.g4 by ANTLR 4.5.3
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
	 * Visit a parse tree produced by {@link FsmParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(FsmParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(FsmParser.StatementContext ctx);
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
	 * Visit a parse tree produced by the {@code IdLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdLiteral(FsmParser.IdLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LtEqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLtEqExp(FsmParser.LtEqExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(FsmParser.FloatLiteralContext ctx);
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
	 * Visit a parse tree produced by the {@code ParExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExp(FsmParser.ParExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExp(FsmParser.OrExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StateChange}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStateChange(FsmParser.StateChangeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringListeral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringListeral(FsmParser.StringListeralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteral(FsmParser.IntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NeqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNeqExp(FsmParser.NeqExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#fcall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFcall(FsmParser.FcallContext ctx);
	/**
	 * Visit a parse tree produced by {@link FsmParser#elist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElist(FsmParser.ElistContext ctx);
}