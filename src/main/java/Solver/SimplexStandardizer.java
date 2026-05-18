package Solver;

import Model.LinearConstraint;
import Model.StandardizedConstraint;
import java.util.ArrayList;
import java.util.List;

public class SimplexStandardizer {

    private int scvCounter = 1;

    private String generateScvName() { return "_scv" + (scvCounter++); }

    public List<StandardizedConstraint> standardize(List<LinearConstraint> parsedConstraints) {
        List<StandardizedConstraint> standardizedList = new ArrayList<>();

        for (LinearConstraint original : parsedConstraints) {

            // Ensure RHS is positive and flip operator if necessary
            LinearConstraint normalized = normalizeRhs(original);

            switch (normalized.getRelOp()) {
                case "<=": {
                    // Transformation: expr <= const  =>  expr - _scv = 0 AND _scv <= const
                    String slackVar = generateScvName();

                    // Constraint 1: expr - _scv = 0
                    StandardizedConstraint eqPart = new StandardizedConstraint();
                    normalized.getVars().forEach(eqPart::addVariable);
                    eqPart.addVariable(slackVar, -1.0);
                    eqPart.setConstTerm(0.0);
                    standardizedList.add(eqPart);

                    // Constraint 2: _scv <= const
                    StandardizedConstraint limitPart = new StandardizedConstraint();
                    limitPart.addVariable(slackVar, 1.0);
                    limitPart.setRelOp("<=");
                    limitPart.setConstTerm(normalized.getConstTerm());
                    standardizedList.add(limitPart);
                    break;
                }

                case ">=": {
                    // Transformation: expr >= const  =>  expr - _scv = 0 AND _scv >= const
                    String surplusVar = generateScvName();

                    // Constraint 1: expr - _scv = 0
                    StandardizedConstraint eqPart = new StandardizedConstraint();
                    normalized.getVars().forEach(eqPart::addVariable);
                    eqPart.addVariable(surplusVar, -1.0);
                    eqPart.setConstTerm(0.0);
                    standardizedList.add(eqPart);

                    // Constraint 2: _scv >= const
                    StandardizedConstraint limitPart = new StandardizedConstraint();
                    limitPart.addVariable(surplusVar, 1.0);
                    limitPart.setRelOp(">=");
                    limitPart.setConstTerm(normalized.getConstTerm());
                    standardizedList.add(limitPart);
                    break;
                }

                case "=": {
                    // Transformation: expr = const  =>  expr - _scv = 0 AND _scv <= const AND _scv >= const
                    String bridgeVar = generateScvName();

                    // Constraint 1: expr - _scv = 0
                    StandardizedConstraint eqPart = new StandardizedConstraint();
                    normalized.getVars().forEach(eqPart::addVariable);
                    eqPart.addVariable(bridgeVar, -1.0);
                    eqPart.setConstTerm(0.0);
                    standardizedList.add(eqPart);

                    // Constraint 2: _scv <= const
                    StandardizedConstraint upperLimit = new StandardizedConstraint();
                    upperLimit.addVariable(bridgeVar, 1.0);
                    upperLimit.setRelOp("<=");
                    upperLimit.setConstTerm(normalized.getConstTerm());
                    standardizedList.add(upperLimit);

                    // Constraint 3: _scv >= const
                    StandardizedConstraint lowerLimit = new StandardizedConstraint();
                    lowerLimit.addVariable(bridgeVar, 1.0);
                    lowerLimit.setRelOp(">=");
                    lowerLimit.setConstTerm(normalized.getConstTerm());
                    standardizedList.add(lowerLimit);
                    break;
                }

                default:
                    throw new IllegalArgumentException("Unsupported operator: " + normalized.getRelOp());
            }
        }

        return standardizedList;
    }

    // Helper to keep RHS always non-negative
    private LinearConstraint normalizeRhs(LinearConstraint c) {
        if (c.getConstTerm() >= 0) return c;

        LinearConstraint inverted = new LinearConstraint();
        c.getVars().forEach((k, v) -> inverted.addVariable(k, -v));
        inverted.setConstTerm(-c.getConstTerm());

        if (c.getRelOp().equals("<=")) inverted.setRelOp(">=");
        else if (c.getRelOp().equals(">=")) inverted.setRelOp("<=");
        else inverted.setRelOp("=");

        return inverted;
    }
}