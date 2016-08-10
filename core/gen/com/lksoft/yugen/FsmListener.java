// Generated from D:/lksoft/yugen/core/src/main/antlr\Fsm.g4 by ANTLR 4.5.3
package com.lksoft.yugen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FsmParser}.
 */
public interface FsmListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FsmParser#fsm}.
	 * @param ctx the parse tree
	 */
	void enterFsm(FsmParser.FsmContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#fsm}.
	 * @param ctx the parse tree
	 */
	void exitFsm(FsmParser.FsmContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterCmd(FsmParser.CmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitCmd(FsmParser.CmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#commandlist}.
	 * @param ctx the parse tree
	 */
	void enterCommandlist(FsmParser.CommandlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#commandlist}.
	 * @param ctx the parse tree
	 */
	void exitCommandlist(FsmParser.CommandlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(FsmParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(FsmParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#paramslist}.
	 * @param ctx the parse tree
	 */
	void enterParamslist(FsmParser.ParamslistContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#paramslist}.
	 * @param ctx the parse tree
	 */
	void exitParamslist(FsmParser.ParamslistContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(FsmParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(FsmParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#stateless}.
	 * @param ctx the parse tree
	 */
	void enterStateless(FsmParser.StatelessContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#stateless}.
	 * @param ctx the parse tree
	 */
	void exitStateless(FsmParser.StatelessContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#statesOpt}.
	 * @param ctx the parse tree
	 */
	void enterStatesOpt(FsmParser.StatesOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#statesOpt}.
	 * @param ctx the parse tree
	 */
	void exitStatesOpt(FsmParser.StatesOptContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#states}.
	 * @param ctx the parse tree
	 */
	void enterStates(FsmParser.StatesContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#states}.
	 * @param ctx the parse tree
	 */
	void exitStates(FsmParser.StatesContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#state}.
	 * @param ctx the parse tree
	 */
	void enterState(FsmParser.StateContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#state}.
	 * @param ctx the parse tree
	 */
	void exitState(FsmParser.StateContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#triggers}.
	 * @param ctx the parse tree
	 */
	void enterTriggers(FsmParser.TriggersContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#triggers}.
	 * @param ctx the parse tree
	 */
	void exitTriggers(FsmParser.TriggersContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SystemTrigger}
	 * labeled alternative in {@link FsmParser#trigger}.
	 * @param ctx the parse tree
	 */
	void enterSystemTrigger(FsmParser.SystemTriggerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SystemTrigger}
	 * labeled alternative in {@link FsmParser#trigger}.
	 * @param ctx the parse tree
	 */
	void exitSystemTrigger(FsmParser.SystemTriggerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SingleCondTrigger}
	 * labeled alternative in {@link FsmParser#trigger}.
	 * @param ctx the parse tree
	 */
	void enterSingleCondTrigger(FsmParser.SingleCondTriggerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SingleCondTrigger}
	 * labeled alternative in {@link FsmParser#trigger}.
	 * @param ctx the parse tree
	 */
	void exitSingleCondTrigger(FsmParser.SingleCondTriggerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MultiCondTrigger}
	 * labeled alternative in {@link FsmParser#trigger}.
	 * @param ctx the parse tree
	 */
	void enterMultiCondTrigger(FsmParser.MultiCondTriggerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MultiCondTrigger}
	 * labeled alternative in {@link FsmParser#trigger}.
	 * @param ctx the parse tree
	 */
	void exitMultiCondTrigger(FsmParser.MultiCondTriggerContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#triglist}.
	 * @param ctx the parse tree
	 */
	void enterTriglist(FsmParser.TriglistContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#triglist}.
	 * @param ctx the parse tree
	 */
	void exitTriglist(FsmParser.TriglistContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#trigel}.
	 * @param ctx the parse tree
	 */
	void enterTrigel(FsmParser.TrigelContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#trigel}.
	 * @param ctx the parse tree
	 */
	void exitTrigel(FsmParser.TrigelContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#statementsOpt}.
	 * @param ctx the parse tree
	 */
	void enterStatementsOpt(FsmParser.StatementsOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#statementsOpt}.
	 * @param ctx the parse tree
	 */
	void exitStatementsOpt(FsmParser.StatementsOptContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(FsmParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(FsmParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStmt(FsmParser.AssignmentStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStmt(FsmParser.AssignmentStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SwitchStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterSwitchStmt(FsmParser.SwitchStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SwitchStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitSwitchStmt(FsmParser.SwitchStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IteStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIteStmt(FsmParser.IteStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IteStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIteStmt(FsmParser.IteStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FCallStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterFCallStmt(FsmParser.FCallStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FCallStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitFCallStmt(FsmParser.FCallStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StateChangeStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStateChangeStmt(FsmParser.StateChangeStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StateChangeStmt}
	 * labeled alternative in {@link FsmParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStateChangeStmt(FsmParser.StateChangeStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#switchcase}.
	 * @param ctx the parse tree
	 */
	void enterSwitchcase(FsmParser.SwitchcaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#switchcase}.
	 * @param ctx the parse tree
	 */
	void exitSwitchcase(FsmParser.SwitchcaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#caselist}.
	 * @param ctx the parse tree
	 */
	void enterCaselist(FsmParser.CaselistContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#caselist}.
	 * @param ctx the parse tree
	 */
	void exitCaselist(FsmParser.CaselistContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#scase}.
	 * @param ctx the parse tree
	 */
	void enterScase(FsmParser.ScaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#scase}.
	 * @param ctx the parse tree
	 */
	void exitScase(FsmParser.ScaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfThen}
	 * labeled alternative in {@link FsmParser#ite}.
	 * @param ctx the parse tree
	 */
	void enterIfThen(FsmParser.IfThenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfThen}
	 * labeled alternative in {@link FsmParser#ite}.
	 * @param ctx the parse tree
	 */
	void exitIfThen(FsmParser.IfThenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfThenElse}
	 * labeled alternative in {@link FsmParser#ite}.
	 * @param ctx the parse tree
	 */
	void enterIfThenElse(FsmParser.IfThenElseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfThenElse}
	 * labeled alternative in {@link FsmParser#ite}.
	 * @param ctx the parse tree
	 */
	void exitIfThenElse(FsmParser.IfThenElseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(FsmParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(FsmParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterNotExp(FsmParser.NotExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitNotExp(FsmParser.NotExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterAndExp(FsmParser.AndExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitAndExp(FsmParser.AndExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FCallExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterFCallExp(FsmParser.FCallExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FCallExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitFCallExp(FsmParser.FCallExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LtEqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterLtEqExp(FsmParser.LtEqExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LtEqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitLtEqExp(FsmParser.LtEqExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterAddExp(FsmParser.AddExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitAddExp(FsmParser.AddExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GtEqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterGtEqExp(FsmParser.GtEqExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GtEqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitGtEqExp(FsmParser.GtEqExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GtExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterGtExp(FsmParser.GtExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GtExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitGtExp(FsmParser.GtExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterMulExp(FsmParser.MulExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitMulExp(FsmParser.MulExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterParExp(FsmParser.ParExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitParExp(FsmParser.ParExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CondExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterCondExp(FsmParser.CondExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CondExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitCondExp(FsmParser.CondExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterBoolLiteral(FsmParser.BoolLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitBoolLiteral(FsmParser.BoolLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AnimLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterAnimLiteral(FsmParser.AnimLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AnimLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitAnimLiteral(FsmParser.AnimLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterOrExp(FsmParser.OrExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitOrExp(FsmParser.OrExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterIntLiteral(FsmParser.IntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitIntLiteral(FsmParser.IntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PhysicsLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterPhysicsLiteral(FsmParser.PhysicsLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PhysicsLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitPhysicsLiteral(FsmParser.PhysicsLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterSubExp(FsmParser.SubExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitSubExp(FsmParser.SubExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NeqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterNeqExp(FsmParser.NeqExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NeqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitNeqExp(FsmParser.NeqExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterIdLiteral(FsmParser.IdLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitIdLiteral(FsmParser.IdLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(FsmParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(FsmParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code HitLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterHitLiteral(FsmParser.HitLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code HitLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitHitLiteral(FsmParser.HitLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DivExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterDivExp(FsmParser.DivExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DivExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitDivExp(FsmParser.DivExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CommandLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterCommandLiteral(FsmParser.CommandLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CommandLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitCommandLiteral(FsmParser.CommandLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LtExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterLtExp(FsmParser.LtExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LtExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitLtExp(FsmParser.LtExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterEqExp(FsmParser.EqExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EqExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitEqExp(FsmParser.EqExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(FsmParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(FsmParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ModExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void enterModExp(FsmParser.ModExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ModExp}
	 * labeled alternative in {@link FsmParser#e}.
	 * @param ctx the parse tree
	 */
	void exitModExp(FsmParser.ModExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#fcall}.
	 * @param ctx the parse tree
	 */
	void enterFcall(FsmParser.FcallContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#fcall}.
	 * @param ctx the parse tree
	 */
	void exitFcall(FsmParser.FcallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ElistE}
	 * labeled alternative in {@link FsmParser#elist}.
	 * @param ctx the parse tree
	 */
	void enterElistE(FsmParser.ElistEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ElistE}
	 * labeled alternative in {@link FsmParser#elist}.
	 * @param ctx the parse tree
	 */
	void exitElistE(FsmParser.ElistEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EListEElist}
	 * labeled alternative in {@link FsmParser#elist}.
	 * @param ctx the parse tree
	 */
	void enterEListEElist(FsmParser.EListEElistContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EListEElist}
	 * labeled alternative in {@link FsmParser#elist}.
	 * @param ctx the parse tree
	 */
	void exitEListEElist(FsmParser.EListEElistContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#commandline}.
	 * @param ctx the parse tree
	 */
	void enterCommandline(FsmParser.CommandlineContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#commandline}.
	 * @param ctx the parse tree
	 */
	void exitCommandline(FsmParser.CommandlineContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CommandSeqCToken}
	 * labeled alternative in {@link FsmParser#commandseq}.
	 * @param ctx the parse tree
	 */
	void enterCommandSeqCToken(FsmParser.CommandSeqCTokenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CommandSeqCToken}
	 * labeled alternative in {@link FsmParser#commandseq}.
	 * @param ctx the parse tree
	 */
	void exitCommandSeqCToken(FsmParser.CommandSeqCTokenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CTokenList}
	 * labeled alternative in {@link FsmParser#commandseq}.
	 * @param ctx the parse tree
	 */
	void enterCTokenList(FsmParser.CTokenListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CTokenList}
	 * labeled alternative in {@link FsmParser#commandseq}.
	 * @param ctx the parse tree
	 */
	void exitCTokenList(FsmParser.CTokenListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#ctoken}.
	 * @param ctx the parse tree
	 */
	void enterCtoken(FsmParser.CtokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#ctoken}.
	 * @param ctx the parse tree
	 */
	void exitCtoken(FsmParser.CtokenContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#exclusiveOpt}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveOpt(FsmParser.ExclusiveOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#exclusiveOpt}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveOpt(FsmParser.ExclusiveOptContext ctx);
	/**
	 * Enter a parse tree produced by the {@code HoldMod}
	 * labeled alternative in {@link FsmParser#modOpt}.
	 * @param ctx the parse tree
	 */
	void enterHoldMod(FsmParser.HoldModContext ctx);
	/**
	 * Exit a parse tree produced by the {@code HoldMod}
	 * labeled alternative in {@link FsmParser#modOpt}.
	 * @param ctx the parse tree
	 */
	void exitHoldMod(FsmParser.HoldModContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReleaseMod}
	 * labeled alternative in {@link FsmParser#modOpt}.
	 * @param ctx the parse tree
	 */
	void enterReleaseMod(FsmParser.ReleaseModContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReleaseMod}
	 * labeled alternative in {@link FsmParser#modOpt}.
	 * @param ctx the parse tree
	 */
	void exitReleaseMod(FsmParser.ReleaseModContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NoMod}
	 * labeled alternative in {@link FsmParser#modOpt}.
	 * @param ctx the parse tree
	 */
	void enterNoMod(FsmParser.NoModContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NoMod}
	 * labeled alternative in {@link FsmParser#modOpt}.
	 * @param ctx the parse tree
	 */
	void exitNoMod(FsmParser.NoModContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#timeOpt}.
	 * @param ctx the parse tree
	 */
	void enterTimeOpt(FsmParser.TimeOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#timeOpt}.
	 * @param ctx the parse tree
	 */
	void exitTimeOpt(FsmParser.TimeOptContext ctx);
	/**
	 * Enter a parse tree produced by {@link FsmParser#keylist}.
	 * @param ctx the parse tree
	 */
	void enterKeylist(FsmParser.KeylistContext ctx);
	/**
	 * Exit a parse tree produced by {@link FsmParser#keylist}.
	 * @param ctx the parse tree
	 */
	void exitKeylist(FsmParser.KeylistContext ctx);
}