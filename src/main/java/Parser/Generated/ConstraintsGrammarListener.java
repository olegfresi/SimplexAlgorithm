// Generated from ConstraintsGrammar.g4 by ANTLR 4.13.2

package Parser.Generated;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ConstraintsGrammarParser}.
 */
public interface ConstraintsGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ConstraintsGrammarParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(ConstraintsGrammarParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConstraintsGrammarParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(ConstraintsGrammarParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConstraintsGrammarParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(ConstraintsGrammarParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConstraintsGrammarParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(ConstraintsGrammarParser.FormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConstraintsGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(ConstraintsGrammarParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConstraintsGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(ConstraintsGrammarParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code coeffVar}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterCoeffVar(ConstraintsGrammarParser.CoeffVarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code coeffVar}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitCoeffVar(ConstraintsGrammarParser.CoeffVarContext ctx);
	/**
	 * Enter a parse tree produced by the {@code var}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterVar(ConstraintsGrammarParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code var}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitVar(ConstraintsGrammarParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by the {@code number}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterNumber(ConstraintsGrammarParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code number}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitNumber(ConstraintsGrammarParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negTerm}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterNegTerm(ConstraintsGrammarParser.NegTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negTerm}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitNegTerm(ConstraintsGrammarParser.NegTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterParens(ConstraintsGrammarParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link ConstraintsGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitParens(ConstraintsGrammarParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConstraintsGrammarParser#relop}.
	 * @param ctx the parse tree
	 */
	void enterRelop(ConstraintsGrammarParser.RelopContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConstraintsGrammarParser#relop}.
	 * @param ctx the parse tree
	 */
	void exitRelop(ConstraintsGrammarParser.RelopContext ctx);
}