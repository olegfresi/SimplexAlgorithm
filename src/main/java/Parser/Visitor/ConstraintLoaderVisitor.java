package Parser.Visitor;

import Model.LinearConstraint;
import Parser.Generated.*;
import java.util.ArrayList;
import java.util.List;

public class ConstraintLoaderVisitor extends ConstraintsGrammarBaseVisitor<Void> {
    private final List<LinearConstraint> constraints = new ArrayList<>();

    public List<LinearConstraint> getConstraints() {
        return constraints;
    }

    @Override
    public Void visitFile(ConstraintsGrammarParser.FileContext ctx) {
        for (ConstraintsGrammarParser.FormulaContext formulaCtx : ctx.formula())
            this.visitFormula(formulaCtx);

        return null;
    }

    @Override
    public Void visitFormula(ConstraintsGrammarParser.FormulaContext ctx) {
        LinearConstraint constraint = new LinearConstraint();
        constraint.setRelOp(ctx.relop().getText());

        // Process lhs without changing signs
        main.java.Parser.Visitor.ExpressionProcessor leftProcessor = new main.java.Parser.Visitor.ExpressionProcessor(1.0);
        leftProcessor.process(ctx.expr(0));

        // Process rhs with a negative multiplier to move all terms to the left-hand side
        main.java.Parser.Visitor.ExpressionProcessor rightProcessor = new main.java.Parser.Visitor.ExpressionProcessor(-1.0);
        rightProcessor.process(ctx.expr(1));

        leftProcessor.getVariables().forEach(constraint::addVariable);
        rightProcessor.getVariables().forEach(constraint::addVariable);

        // The constant term gets the correct sign based on its position (left or right)
        constraint.setConstTerm(leftProcessor.getConstant() - rightProcessor.getConstant());

        constraints.add(constraint);
        return null;
    }
}