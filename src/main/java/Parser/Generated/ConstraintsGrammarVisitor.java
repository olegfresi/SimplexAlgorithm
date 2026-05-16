// Generated from ConstraintsGrammar.g4 by ANTLR 4.13.2

package Parser.Generated;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ConstraintsGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ConstraintsGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ConstraintsGrammarParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(ConstraintsGrammarParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConstraintsGrammarParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormula(ConstraintsGrammarParser.FormulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConstraintsGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(ConstraintsGrammarParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code coeffVar}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCoeffVar(ConstraintsGrammarParser.CoeffVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code var}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(ConstraintsGrammarParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code number}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(ConstraintsGrammarParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negTerm}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegTerm(ConstraintsGrammarParser.NegTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(ConstraintsGrammarParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by {@link ConstraintsGrammarParser#relop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelop(ConstraintsGrammarParser.RelopContext ctx);
}