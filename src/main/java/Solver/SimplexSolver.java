package Solver;

import Model.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.*;
import org.apache.commons.numbers.fraction.BigFraction;

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

        Map<String, BigFraction> lowerBounds = new HashMap<>();
        Map<String, BigFraction> upperBounds = new HashMap<>();
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

        // Ensure standard decision variables default to exactly BigFraction.ZERO if not explicit
        for (String variable : uniqueVars) {
            if (!variable.startsWith("_scv") && !lowerBounds.containsKey(variable)) {
                lowerBounds.put(variable, BigFraction.ZERO);
            }
        }

        List<String> chosenOrder = switch (this.strategy) {
            case REVERSE -> VariableOrderer.getReverseOrder(uniqueVars);
            case RANDOM -> VariableOrderer.getRandomOrder(uniqueVars);
            default -> VariableOrderer.getStandardOrder(uniqueVars);
        };

        // Tableau instantiation now allocates and receives exact fractional structures
        Tableau tableau = TableauBuilder.buildTableau(equationsOnly, chosenOrder);

        int pivotCount = 0;
        boolean isSatisfiable = true;
        boolean pivoting = true;
        int maxIterations = 10000;

        while (pivoting && pivotCount < maxIterations) {
            pivoting = false;
            int leavingRow = -1;
            int enteringCol = -1;

            // Look for bound violations by extracting double values strictly inside the conditional check
            for (int i = 0; i < tableau.getNumRows(); i++) {
                String basicVarName = tableau.getBasicVars()[i];
                BigFraction currentVal = tableau.getValue(i, tableau.getNumCols() - 1);

                if (lowerBounds.containsKey(basicVarName) &&
                        currentVal.doubleValue() < lowerBounds.get(basicVarName).doubleValue()) {
                    leavingRow = i;
                    break;
                }

                if (upperBounds.containsKey(basicVarName) &&
                        currentVal.doubleValue() > upperBounds.get(basicVarName).doubleValue()) {
                    leavingRow = i;
                    break;
                }
            }

            if (leavingRow != -1) {
                String basicVarName = tableau.getBasicVars()[leavingRow];
                BigFraction currentVal = tableau.getValue(leavingRow, tableau.getNumCols() - 1);

                // Evaluate the required direction using double comparison
                boolean mustIncrease = lowerBounds.containsKey(basicVarName) &&
                        currentVal.doubleValue() < lowerBounds.get(basicVarName).doubleValue();

                for (int j = 0; j < tableau.getNumCols() - 1; j++) {
                    BigFraction a_ir = tableau.getValue(leavingRow, j);
                    double a_irDouble = a_ir.doubleValue();

                    // Direction check performed over double values where inequality is mandatory
                    if ((mustIncrease && a_irDouble > 0.0) || (!mustIncrease && a_irDouble < 0.0)) {
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
        BigFraction pivotValue = tab.getValue(pivotRow, pivotCol);

        // Row normalization using exact fraction division
        for (int j = 0; j < numCols; j++) {
            BigFraction currentVal = tab.getValue(pivotRow, j);
            tab.setValue(pivotRow, j, currentVal.divide(pivotValue));
        }

        // Eliminate entries from adjacent rows using precise fractional subtraction and multiplication
        for (int i = 0; i < tab.getNumRows(); i++)
            if (i != pivotRow) {
                BigFraction factor = tab.getValue(i, pivotCol);
                for (int j = 0; j < numCols; j++) {
                    BigFraction targetRowValue = tab.getValue(i, j);
                    BigFraction pivotRowValue = tab.getValue(pivotRow, j);

                    // Formula: R_i = R_i - (factor * R_pivot)
                    BigFraction updatedValue = targetRowValue.subtract(factor.multiply(pivotRowValue));
                    tab.setValue(i, j, updatedValue);
                }
            }
        }
}