package Solver;

import Model.LinearConstraint;
import Model.StandardizedConstraint;
import org.apache.commons.numbers.fraction.BigFraction;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class SimplexStandardizer {

    private int scvCounter = 1;
    private String generateScvName() { return "_scv" + (scvCounter++); }

    public static class StandardizationResult {
        public final List<StandardizedConstraint> equations;
        public final Map<String, BigFraction> lowerBounds;
        public final Map<String, BigFraction> upperBounds;
        public final List<String> slackVars;

        public StandardizationResult(List<StandardizedConstraint> eq,
                                     Map<String, BigFraction> lb,
                                     Map<String, BigFraction> ub,
                                     List<String> slackVars) {
            this.equations   = eq;
            this.lowerBounds = lb;
            this.upperBounds = ub;
            this.slackVars   = slackVars;
        }
    }

    public StandardizationResult standardize(List<LinearConstraint> constraints) {
        List<StandardizedConstraint> equations  = new ArrayList<>();
        Map<String, BigFraction>     lowerBounds = new LinkedHashMap<>();
        Map<String, BigFraction>     upperBounds = new LinkedHashMap<>();
        List<String>                 slackVars   = new ArrayList<>();

        for (LinearConstraint c : constraints)
            for (String v : c.getVars().keySet())
                lowerBounds.putIfAbsent(v, null);

        for (LinearConstraint original : constraints) {
            String scv = generateScvName();
            slackVars.add(scv);

            StandardizedConstraint eq = new StandardizedConstraint();
            original.getVars().forEach(eq::addVariable);
            eq.addVariable(scv, BigFraction.of(-1));
            eq.setConstTerm(BigFraction.ZERO);
            eq.setRelOp("=");
            equations.add(eq);

            BigFraction rhs = original.getConstTerm();
            switch (original.getRelOp()) {
                case ">=" -> lowerBounds.put(scv, rhs);
                case "<=" -> {
                    lowerBounds.put(scv, null);
                    upperBounds.put(scv, rhs);
                }
                case "=" -> {
                    lowerBounds.put(scv, rhs);
                    upperBounds.put(scv, rhs);
                }
                default -> throw new IllegalArgumentException("Operator not supported: " + original.getRelOp());
            }
        }

        return new StandardizationResult(equations, lowerBounds, upperBounds, slackVars);
    }
}