package Solver;

import Model.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.*;

public class SimplexSolver {
    private final List<LinearConstraint> originalConstraints = new ArrayList<>();
    private final OrderingStrategy strategy;

    public SimplexSolver(List<LinearConstraint> constraints, OrderingStrategy strategy) {
        this.originalConstraints.addAll(constraints);
        this.strategy = strategy;
    }

    public SimplexResult solve() {
        SimplexStandardizer standardizer = new SimplexStandardizer();
        List<StandardizedConstraint> standardizedConstraints = standardizer.standardize(originalConstraints);

        List<StandardizedConstraint> equationsOnly = new ArrayList<>();
        Map<String, Double> lowerBounds = new HashMap<>();
        Map<String, Double> upperBounds = new HashMap<>();
        Set<String> uniqueVars = new HashSet<>();

        for (StandardizedConstraint sc : standardizedConstraints) {
            uniqueVars.addAll(sc.getVars().keySet());

            if (sc.getRelOp().equals("="))
                equationsOnly.add(sc);
            else {
                String varName = sc.getVars().keySet().iterator().next();
                if (sc.getRelOp().equals(">="))
                    lowerBounds.put(varName, sc.getConstTerm());
                else if (sc.getRelOp().equals("<="))
                    upperBounds.put(varName, sc.getConstTerm());
            }
        }

        // Ensure standard decision variables (like x, y) default to a lower bound of 0.0 if not explicit
        for (String variable : uniqueVars)
            if (!variable.startsWith("_scv") && !lowerBounds.containsKey(variable))
                lowerBounds.put(variable, 0.0);

        List<String> chosenOrder = switch (this.strategy) {
            case REVERSE -> VariableOrderer.getReverseOrder(uniqueVars);
            case RANDOM -> VariableOrderer.getRandomOrder(uniqueVars);
            default -> VariableOrderer.getStandardOrder(uniqueVars);
        };

        Tableau tableau = TableauBuilder.buildTableau(equationsOnly, chosenOrder);

        int pivotCount = 0;
        boolean isSatisfiable = true;
        boolean pivoting = true;
        int maxIterations = 10000;

        while (pivoting && pivotCount < maxIterations) {
            pivoting = false;
            int leavingRow = -1;
            int enteringCol = -1;

            // Look for bound violations using the updated headers mapping
            for (int i = 0; i < tableau.getNumRows(); i++) {
                String basicVarName = tableau.getBasicVars()[i];
                double currentVal = tableau.getValue(i, tableau.getNumCols() - 1);

                if (lowerBounds.containsKey(basicVarName) && currentVal < lowerBounds.get(basicVarName) - 1e-9) {
                    leavingRow = i;
                    break;
                }

                if (upperBounds.containsKey(basicVarName) && currentVal > upperBounds.get(basicVarName) + 1e-9) {
                    leavingRow = i;
                    break;
                }
            }

            if (leavingRow != -1) {
                String basicVarName = tableau.getBasicVars()[leavingRow];
                double currentVal = tableau.getValue(leavingRow, tableau.getNumCols() - 1);

                boolean mustIncrease = lowerBounds.containsKey(basicVarName) && currentVal < lowerBounds.get(basicVarName);

                for (int j = 0; j < tableau.getNumCols() - 1; j++) {
                    double a_ir = tableau.getValue(leavingRow, j);

                    if ((mustIncrease && a_ir > 0.0) || (!mustIncrease && a_ir < 0.0)) {
                        enteringCol = j;
                        pivoting = true;
                        break;
                    }
                }

                if (!pivoting) {
                    isSatisfiable = false;
                    break;
                }
            }

            if (pivoting) {
                gaussianElimination(tableau, leavingRow, enteringCol);
                swapVariables(tableau.getBasicVars(), tableau.getNonBasicVars(), enteringCol, leavingRow);
                pivotCount++;
            }
        }

        if (pivotCount >= maxIterations)
            isSatisfiable = false;

        return new SimplexResult(isSatisfiable, pivotCount, chosenOrder);
    }

    private void swapVariables(String[] basicVars, String[] nonBasicVars, int enteringIndex, int leavingIndex) {
        String temp = basicVars[leavingIndex];
        basicVars[leavingIndex] = nonBasicVars[enteringIndex];
        nonBasicVars[enteringIndex] = temp;
    }

    private void gaussianElimination(Tableau tab, int pivotRow, int pivotCol) {
        int numCols = tab.getNumCols();
        double pivotValue = tab.getValue(pivotRow, pivotCol);

        for (int j = 0; j < numCols; j++) {
            double currentVal = tab.getValue(pivotRow, j);
            tab.setValue(pivotRow, j, currentVal / pivotValue);
        }

        for (int i = 0; i < tab.getNumRows(); i++)
            if (i != pivotRow) {
                double factor = tab.getValue(i, pivotCol);
                for (int j = 0; j < numCols; j++) {
                    double targetRowValue = tab.getValue(i, j);
                    double pivotRowValue = tab.getValue(pivotRow, j);
                    tab.setValue(i, j, targetRowValue - (factor * pivotRowValue));
                }
            }
    }
}