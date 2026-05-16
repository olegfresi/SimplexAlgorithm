package main.java.Parser.Visitor;

import Parser.Generated.*;
import java.util.HashMap;
import java.util.Map;


public class ExpressionProcessor extends ConstraintsGrammarBaseVisitor<Void> {
    private final double sideMultiplier;
    private final Map<String, Double> variables = new HashMap<>();
    private double constant = 0.0;
    private double currentSign = 1.0;

    public ExpressionProcessor(double sideMultiplier) { this.sideMultiplier = sideMultiplier; }

    public Map<String, Double> getVariables() { return variables; }
    public double              getConstant() { return constant; }

    public void process(ConstraintsGrammarParser.ExprContext ctx) {
        if (ctx == null) return;

        // Check if the expression begins with a unary minus (e.g., -x1 + x2)
        // It verifies if a SUB token exists and if it occupies the very first position among children
        boolean startsWithMinus = ctx.SUB(0) != null && ctx.getChild(0).getText().equals("-");

        // Set the initial sign multiplier based on the presence of the leading minus
        currentSign = startsWithMinus ? -1.0 : 1.0;

        // Process the mandatory first term of the expression
        if (!ctx.term().isEmpty())
            this.visit(ctx.term(0));

        // Iterate through all subsequent terms, starting from the second one
        for (int i = 1; i < ctx.term().size(); i++) {
            // Calculate the precise index of the operator token (+ or -) preceding the current term.
            // If it starts with a minus, all children indices are shifted forward by 1 slot.
            int operatorChildIndex = startsWithMinus ? (i * 2) : (i * 2) - 1;

            // If the calculated operator index is valid, extract its text to update the contextual sign
            if (operatorChildIndex < ctx.getChildCount()) {
                String op = ctx.getChild(operatorChildIndex).getText();
                currentSign = op.equals("-") ? -1.0 : 1.0;
            }

            // Visit the current term, which will inherit the updated 'currentSign' context
            this.visit(ctx.term(i));
        }
    }

    private double parseNumber(String numStr) {
        if (numStr.contains("/")) {
            String[] parts = numStr.split("/");
            return Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
        }

        return Double.parseDouble(numStr);
    }

    @Override
    public Void visitCoeffVar(ConstraintsGrammarParser.CoeffVarContext ctx) {
        double coef = currentSign * sideMultiplier * parseNumber(ctx.NUMBER().getText());
        String varName = ctx.VARIABLE().getText();
        variables.put(varName, variables.getOrDefault(varName, 0.0) + coef);
        return null;
    }

    @Override
    public Void visitVar(ConstraintsGrammarParser.VarContext ctx) {
        double coef = currentSign * sideMultiplier * 1.0;
        String varName = ctx.VARIABLE().getText();
        variables.put(varName, variables.getOrDefault(varName, 0.0) + coef);
        return null;
    }

    @Override
    public Void visitNumber(ConstraintsGrammarParser.NumberContext ctx) {
        double val = currentSign * parseNumber(ctx.NUMBER().getText());
        constant += val;
        return null;
    }

    @Override
    public Void visitNegTerm(ConstraintsGrammarParser.NegTermContext ctx) {
        double backupSign = currentSign;
        currentSign = currentSign * -1.0;
        this.visit(ctx.term());
        currentSign = backupSign;
        return null;
    }

    @Override
    public Void visitParens(ConstraintsGrammarParser.ParensContext ctx) {
        double backupSign = currentSign;
        ExpressionProcessor subProcessor = new ExpressionProcessor(this.sideMultiplier * currentSign);
        subProcessor.process(ctx.expr());

        subProcessor.getVariables().forEach((k, v) -> variables.put(k, variables.getOrDefault(k, 0.0) + v));
        this.constant += subProcessor.getConstant();

        currentSign = backupSign;
        return null;
    }
}