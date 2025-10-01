// Generated from com/neoobjectpascal/NeoObjectPascalParser.g4 by ANTLR 4.13.1
package com.neoobjectpascal;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link NeoObjectPascalParser}.
 */
public interface NeoObjectPascalParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(NeoObjectPascalParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(NeoObjectPascalParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(NeoObjectPascalParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(NeoObjectPascalParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#usesClause}.
	 * @param ctx the parse tree
	 */
	void enterUsesClause(NeoObjectPascalParser.UsesClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#usesClause}.
	 * @param ctx the parse tree
	 */
	void exitUsesClause(NeoObjectPascalParser.UsesClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#modulePath}.
	 * @param ctx the parse tree
	 */
	void enterModulePath(NeoObjectPascalParser.ModulePathContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#modulePath}.
	 * @param ctx the parse tree
	 */
	void exitModulePath(NeoObjectPascalParser.ModulePathContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(NeoObjectPascalParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(NeoObjectPascalParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(NeoObjectPascalParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(NeoObjectPascalParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(NeoObjectPascalParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(NeoObjectPascalParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(NeoObjectPascalParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(NeoObjectPascalParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCall(NeoObjectPascalParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCall(NeoObjectPascalParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(NeoObjectPascalParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(NeoObjectPascalParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(NeoObjectPascalParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(NeoObjectPascalParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(NeoObjectPascalParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(NeoObjectPascalParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(NeoObjectPascalParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(NeoObjectPascalParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(NeoObjectPascalParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(NeoObjectPascalParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(NeoObjectPascalParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(NeoObjectPascalParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#writeLnStatement}.
	 * @param ctx the parse tree
	 */
	void enterWriteLnStatement(NeoObjectPascalParser.WriteLnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#writeLnStatement}.
	 * @param ctx the parse tree
	 */
	void exitWriteLnStatement(NeoObjectPascalParser.WriteLnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#readLnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReadLnStatement(NeoObjectPascalParser.ReadLnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#readLnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReadLnStatement(NeoObjectPascalParser.ReadLnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#showMenuStatement}.
	 * @param ctx the parse tree
	 */
	void enterShowMenuStatement(NeoObjectPascalParser.ShowMenuStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#showMenuStatement}.
	 * @param ctx the parse tree
	 */
	void exitShowMenuStatement(NeoObjectPascalParser.ShowMenuStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#jsonParseStatement}.
	 * @param ctx the parse tree
	 */
	void enterJsonParseStatement(NeoObjectPascalParser.JsonParseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#jsonParseStatement}.
	 * @param ctx the parse tree
	 */
	void exitJsonParseStatement(NeoObjectPascalParser.JsonParseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#csvParseStatement}.
	 * @param ctx the parse tree
	 */
	void enterCsvParseStatement(NeoObjectPascalParser.CsvParseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#csvParseStatement}.
	 * @param ctx the parse tree
	 */
	void exitCsvParseStatement(NeoObjectPascalParser.CsvParseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(NeoObjectPascalParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(NeoObjectPascalParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(NeoObjectPascalParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(NeoObjectPascalParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(NeoObjectPascalParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(NeoObjectPascalParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(NeoObjectPascalParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(NeoObjectPascalParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(NeoObjectPascalParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(NeoObjectPascalParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link NeoObjectPascalParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(NeoObjectPascalParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link NeoObjectPascalParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(NeoObjectPascalParser.IdentifierContext ctx);
}