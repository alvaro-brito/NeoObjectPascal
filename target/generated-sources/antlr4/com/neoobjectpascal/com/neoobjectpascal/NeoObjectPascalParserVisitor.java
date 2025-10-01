// Generated from com/neoobjectpascal/NeoObjectPascalParser.g4 by ANTLR 4.13.1
package com.neoobjectpascal;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link NeoObjectPascalParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface NeoObjectPascalParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(NeoObjectPascalParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(NeoObjectPascalParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#usesClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUsesClause(NeoObjectPascalParser.UsesClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#modulePath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModulePath(NeoObjectPascalParser.ModulePathContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(NeoObjectPascalParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(NeoObjectPascalParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(NeoObjectPascalParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(NeoObjectPascalParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(NeoObjectPascalParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(NeoObjectPascalParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(NeoObjectPascalParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(NeoObjectPascalParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(NeoObjectPascalParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(NeoObjectPascalParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(NeoObjectPascalParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#writeLnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteLnStatement(NeoObjectPascalParser.WriteLnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#readLnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadLnStatement(NeoObjectPascalParser.ReadLnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#showMenuStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShowMenuStatement(NeoObjectPascalParser.ShowMenuStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#jsonParseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonParseStatement(NeoObjectPascalParser.JsonParseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#csvParseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCsvParseStatement(NeoObjectPascalParser.CsvParseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(NeoObjectPascalParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(NeoObjectPascalParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(NeoObjectPascalParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(NeoObjectPascalParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(NeoObjectPascalParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link NeoObjectPascalParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(NeoObjectPascalParser.IdentifierContext ctx);
}