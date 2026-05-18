package Parser.Visitor;

import Parser.Generated.*;
import org.apache.commons.numbers.fraction.BigFraction;

import java.util.HashMap;
import java.util.Map;


public class ExpressionProcessor extends ConstraintsGrammarBaseVisitor<Void> {
    private final BigFraction sideMultiplier;
    private final Map<String, BigFraction> variables = new HashMap<>();
    private BigFraction constant = BigFraction.ZERO;
    private BigFraction currentSign = BigFraction.ONE;

    public ExpressionProcessor(BigFraction sideMultiplier) {
        this.sideMultiplier = sideMultiplier;
    }

    public Map<String, BigFraction> getVariables() {
        return variables;
    }

    public BigFraction getConstant() {
        return constant;
    }

    public void process(ConstraintsGrammarParser.ExprContext ctx) {
        if (ctx == null)
            return;

        // Check if the expression begins with a unary minus (e.g., -x1 + x2)
        // It verifies if a SUB token exists and if it occupies the very first position among children
        boolean startsWithMinus = ctx.SUB(0) != null && ctx.getChild(0).getText().equals("-");

        // Set the initial sign multiplier based on the presence of the leading minus
        currentSign = startsWithMinus ? BigFraction.ONE.negate() : BigFraction.ONE;

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
                currentSign = op.equals("-") ? BigFraction.ONE.negate() : BigFraction.ONE;
            }

            // Visit the current term, which will inherit the updated 'currentSign' context
            this.visit(ctx.term(i));
        }
    }

    private BigFraction parseNumber(String numStr) {
        if (numStr.contains("/")) {
            String[] parts = numStr.split("/");
            long numerator = Long.parseLong(parts[0]);
            long denominator = Long.parseLong(parts[1]);
            return BigFraction.of(numerator, denominator);
        }

        long value = Long.parseLong(numStr);
        return BigFraction.of(value);
    }

    @Override
    public Void visitCoeffVar(ConstraintsGrammarParser.CoeffVarContext ctx) {
        BigFraction coefficient = currentSign.multiply(sideMultiplier).multiply(parseNumber(ctx.NUMBER().getText()));
        String varName = ctx.VARIABLE().getText();

        variables.put(varName, variables.getOrDefault(varName, BigFraction.ZERO).add(coefficient));
        return null;
    }

    @Override
    public Void visitVar(ConstraintsGrammarParser.VarContext ctx) {
        BigFraction coefficient = currentSign.multiply(sideMultiplier).multiply(BigFraction.ONE);
        String varName = ctx.VARIABLE().getText();

        variables.put(varName, variables.getOrDefault(varName, BigFraction.ZERO).add(coefficient));
        return null;
    }

    @Override
    public Void visitNumber(ConstraintsGrammarParser.NumberContext ctx) {
        BigFraction val = currentSign.multiply(parseNumber(ctx.NUMBER().getText()));
        constant = constant.add(val);
        return null;
    }

    @Override
    public Void visitNegTerm(ConstraintsGrammarParser.NegTermContext ctx) {
        BigFraction backupSign = currentSign;
        currentSign = currentSign.negate();
        this.visit(ctx.term());
        currentSign = backupSign;
        return null;
    }

    @Override
    public Void visitParens(ConstraintsGrammarParser.ParensContext ctx) {
        ExpressionProcessor subProcessor = new ExpressionProcessor(this.sideMultiplier.multiply(currentSign));
        subProcessor.process(ctx.expr());

        subProcessor.getVariables().forEach((k, v) ->
                variables.put(k, variables.getOrDefault(k, BigFraction.ZERO).add(v))
        );
        this.constant = this.constant.add(subProcessor.getConstant());

        return null;
    }
}