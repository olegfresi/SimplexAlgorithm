package Solver;

import Model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.numbers.fraction.BigFraction;


public class SimplexSolver {

    private static final Logger logger = Logger.getLogger(SimplexSolver.class.getName());
    private final List<LinearConstraint> originalConstraints = new ArrayList<>();
    private final OrderingStrategy strategy;

    public SimplexSolver(List<LinearConstraint> constraints, OrderingStrategy strategy) {
        this.originalConstraints.addAll(constraints);
        this.strategy = strategy;
    }

    public SimplexResult solve() {
        SimplexStandardizer std = new SimplexStandardizer();
        SimplexStandardizer.StandardizationResult res = std.standardize(originalConstraints);

        List<StandardizedConstraint> equations = res.equations;
        Map<String, BigFraction>     lb        = res.lowerBounds;
        Map<String, BigFraction>     ub        = res.upperBounds;
        List<String>                 slackVars = res.slackVars;

        Set<String> userVarSet = new LinkedHashSet<>();
        for (LinearConstraint c : originalConstraints) {
            userVarSet.addAll(c.getVars().keySet());
        }

        // Capture initial structural dimensions for analytics
        int numConstraints = originalConstraints.size(); // m
        int numVariables   = userVarSet.size();          // n

        List<String> chosenOrder = switch (strategy) {
            case REVERSE -> VariableOrderer.getReverseOrder(userVarSet);
            case RANDOM  -> VariableOrderer.getRandomOrder(userVarSet);
            default      -> VariableOrderer.getStandardOrder(userVarSet);
        };

        List<String> fixedOrder = new ArrayList<>(chosenOrder);
        fixedOrder.addAll(slackVars);

        Tableau tab = buildPureTableau(equations, chosenOrder, slackVars);

        Map<String, BigFraction> alpha = new LinkedHashMap<>();
        for (String variable : fixedOrder) {
            alpha.put(variable, BigFraction.ZERO);
        }

        logInitialState(tab, lb, ub, alpha);

        int pivotCount     = 0;
        int nullPivotCount = 0; // Tracks degenerate pivots
        int maxIter        = 10000;

        while (pivotCount < maxIter) {
            // State object to hold result of leaving row scanner
            SelectionState state = findLeavingRow(tab, fixedOrder, alpha, lb, ub);

            // No violations found -> SAT
            if (state.row < 0) {
                Map<String, BigFraction> model = new LinkedHashMap<>();
                for (String variable : userVarSet) {
                    model.put(variable, alpha.get(variable));
                }
                // Return extended analytical metrics
                return new SimplexResult(true, pivotCount, nullPivotCount, numConstraints, numVariables, chosenOrder, model);
            }

            int enteringCol = findEnteringCol(tab, fixedOrder, state, alpha, lb, ub);

            // No suitable non-basic variable found -> UNSAT
            if (enteringCol < 0) {
                return new SimplexResult(false, pivotCount, nullPivotCount, numConstraints, numVariables, chosenOrder, new LinkedHashMap<>());
            }

            String xi = tab.getBasicVars()[state.row];
            String xj = tab.getNonBasicVars()[enteringCol];
            BigFraction aij = tab.getValue(state.row, enteringCol);

            logPivotDetails(pivotCount, enteringCol, state, tab, alpha, lb, ub);

            // 3. Compute Delta (theta) variation step size
            BigFraction target = state.mustIncrease ? lb.get(xi) : ub.get(xi);
            BigFraction delta = target.subtract(alpha.get(xi)).divide(aij);

            // --- DEGENERACY CHECK ---
            // If delta is exactly zero, the basis change does not shift the assignment, signifying a degenerate pivot.
            if (delta.signum() == 0)
                nullPivotCount++;

            // 4. Update the assignment mapping alpha
            updateAlphaAssignment(tab, alpha, xi, xj, enteringCol, target, delta);

            // 5. Perform the true algebraic substitution on the tableau matrix
            executeActualPivot(tab, state.row, enteringCol);

            // Swap variable tags between basis and non-basis sets
            tab.getBasicVars()[state.row]      = xj;
            tab.getNonBasicVars()[enteringCol] = xi;
            pivotCount++;

            logger.log(Level.INFO, "Tableau after pivot:");
            printTableauWithAlpha(tab, alpha);
            logger.log(Level.INFO, "");
        }

        return new SimplexResult(false, pivotCount, nullPivotCount, numConstraints, numVariables, chosenOrder, new LinkedHashMap<>());
    }


    private static class SelectionState {
        int row = -1;
        boolean mustIncrease = true;
    }

    private SelectionState findLeavingRow(Tableau tab, List<String> fixedOrder, Map<String, BigFraction> alpha,
                                          Map<String, BigFraction> lb, Map<String, BigFraction> ub) {
        SelectionState state = new SelectionState();
        for (String variable : fixedOrder) {
            int row = indexOf(tab.getBasicVars(), variable);

            if (row >= 0) {
                BigFraction currentVal = alpha.get(variable);
                BigFraction lo = lb.get(variable);
                BigFraction hi = ub.get(variable);

                if (lo != null && currentVal.doubleValue() < lo.doubleValue()) {
                    state.row = row;
                    state.mustIncrease = true;
                } else if (hi != null && currentVal.doubleValue() > hi.doubleValue()) {
                    state.row = row;
                    state.mustIncrease = false;
                }

                if (state.row >= 0) {
                    break;
                }
            }
        }
        return state;
    }

    private int findEnteringCol(Tableau tab, List<String> fixedOrder, SelectionState state,
                                Map<String, BigFraction> alpha, Map<String, BigFraction> lb, Map<String, BigFraction> ub) {
        int enteringCol = -1;
        for (String variable : fixedOrder) {
            int col = indexOf(tab.getNonBasicVars(), variable);

            if (col >= 0) {
                BigFraction aij = tab.getValue(state.row, col);
                BigFraction currentXj = alpha.get(variable);
                BigFraction lowXj = lb.get(variable);
                BigFraction highXj = ub.get(variable);

                if (isSuitableEnteringCol(state.mustIncrease, aij, currentXj, lowXj, highXj))
                    enteringCol = col;

                if (enteringCol >= 0)
                    break;
            }
        }
        return enteringCol;
    }

    private boolean isSuitableEnteringCol(boolean mustIncrease, BigFraction aij, BigFraction currentXj,
                                          BigFraction lowXj, BigFraction highXj) {
        if (mustIncrease) {
            return (aij.signum() > 0 && (highXj == null || currentXj.doubleValue() < highXj.doubleValue())) ||
                    (aij.signum() < 0 && (lowXj == null || currentXj.doubleValue() > lowXj.doubleValue()));
        }

        return (aij.signum() > 0 && (lowXj == null || currentXj.doubleValue() > lowXj.doubleValue())) ||
                (aij.signum() < 0 && (highXj == null || currentXj.doubleValue() < highXj.doubleValue()));
    }

    private void updateAlphaAssignment(Tableau tab, Map<String, BigFraction> alpha, String xi, String xj,
                                       int enteringCol, BigFraction target, BigFraction delta) {
        alpha.put(xj, alpha.get(xj).add(delta));
        for (int i = 0; i < tab.getNumRows(); i++) {
            String basicVar = tab.getBasicVars()[i];
            alpha.put(basicVar, alpha.get(basicVar).add(tab.getValue(i, enteringCol).multiply(delta)));
        }
        alpha.put(xi, target);
    }

    private void logInitialState(Tableau tab, Map<String, BigFraction> lb, Map<String, BigFraction> ub, Map<String, BigFraction> alpha) {
        logger.log(Level.INFO, "=== INITIAL TABLEAU STATE ===");
        logger.log(Level.INFO, "Non-Base: {0}", Arrays.toString(tab.getNonBasicVars()));
        logger.log(Level.INFO, "Base:     {0}", Arrays.toString(tab.getBasicVars()));
        logger.log(Level.INFO, "LB: {0}", Arrays.toString(lb.entrySet().stream().map(e -> e.getKey() + "=" + fmt(e.getValue())).toArray()));
        logger.log(Level.INFO, "UB: {0}", Arrays.toString(ub.entrySet().stream().map(e -> e.getKey() + "=" + fmt(e.getValue())).toArray()));
        logger.log(Level.INFO, "----------------------------------");
        printTableauWithAlpha(tab, alpha);
        logger.log(Level.INFO, "==================================\n");
    }

    private void logPivotDetails(int pivotCount, int enteringCol, SelectionState state, Tableau tab,
                                 Map<String, BigFraction> alpha, Map<String, BigFraction> lb, Map<String, BigFraction> ub) {
        if (logger.isLoggable(Level.INFO)) {
            String xi = tab.getBasicVars()[state.row];
            String xj = tab.getNonBasicVars()[enteringCol];
            BigFraction aij = tab.getValue(state.row, enteringCol);

            logger.info(String.format(
                    "--- PIVOT #%d: exiting=%s (val=%s, lb=%s, ub=%s, mustInc=%b)  entering=%s  elem=%s",
                    pivotCount + 1, xi,
                    fmt(alpha.get(xi)), lb.get(xi), ub.get(xi), state.mustIncrease,
                    xj, fmt(aij)
            ));
        }
    }

    private void executeActualPivot(Tableau tab, int pivotRow, int pivotCol) {
        int numCols = tab.getNumCols();
        BigFraction aij = tab.getValue(pivotRow, pivotCol);

        // 1. Transform the pivot row coefficients
        for (int j = 0; j < numCols; j++) {
            if (j == pivotCol)
                tab.setValue(pivotRow, j, BigFraction.ONE.divide(aij));
            else
                tab.setValue(pivotRow, j, tab.getValue(pivotRow, j).divide(aij).negate());
        }

        // 2. Eliminate entering variable from all other rows (delegated to reduce complexity)
        updateOtherRows(tab, pivotRow, pivotCol, aij);
    }

    private void updateOtherRows(Tableau tab, int pivotRow, int pivotCol, BigFraction aij) {
        int numRows = tab.getNumRows();

        for (int i = 0; i < numRows; i++) {
            if (i != pivotRow) {
                BigFraction ail = tab.getValue(i, pivotCol);

                if (ail.signum() != 0)
                    updateSingleRowCells(tab, i, pivotRow, pivotCol, ail, aij);
            }
        }
    }

    private void updateSingleRowCells(Tableau tab, int i, int pivotRow, int pivotCol, BigFraction ail, BigFraction aij) {
        int numCols = tab.getNumCols();

        for (int j = 0; j < numCols; j++) {
            if (j == pivotCol) {
                tab.setValue(i, j, ail.divide(aij));
            } else {
                BigFraction pivotRowNewCoefficient = tab.getValue(pivotRow, j);
                tab.setValue(i, j, tab.getValue(i, j).add(ail.multiply(pivotRowNewCoefficient)));
            }
        }
    }

    private Tableau buildPureTableau(List<StandardizedConstraint> equations,
                                     List<String> userVarOrder,
                                     List<String> slackVarOrder) {
        int numRows = equations.size();
        int numCols = userVarOrder.size();

        BigFraction[] matrix       = new BigFraction[numRows * numCols];
        String[]      basicVars    = new String[numRows];
        String[]      nonBasicVars = new String[numCols];

        Arrays.fill(matrix, BigFraction.ZERO);

        for (int j = 0; j < userVarOrder.size(); j++)
            nonBasicVars[j] = userVarOrder.get(j);


        for (int i = 0; i < numRows; i++) {
            StandardizedConstraint eq = equations.get(i);
            basicVars[i] = slackVarOrder.get(i);

            for (int j = 0; j < userVarOrder.size(); j++) {
                String variable = userVarOrder.get(j);
                BigFraction coefficient = eq.getVars().getOrDefault(variable, BigFraction.ZERO);
                matrix[i * numCols + j] = coefficient;
            }
        }

        return new Tableau(matrix, basicVars, nonBasicVars, numRows, numCols);
    }

    private int indexOf(String[] arr, String val) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i].equals(val)) return i;

        return -1;
    }

    private void printTableauWithAlpha(Tableau tab, Map<String, BigFraction> alpha) {
        String formatCell = "%-14s";

        if (!logger.isLoggable(Level.INFO))
            return;

        int nc = tab.getNumCols();
        StringBuilder sb = new StringBuilder();

        // 1. Header row
        sb.append(String.format(formatCell, "Base\\NBase"));
        for (int j = 0; j < nc; j++) {
            String nbv = tab.getNonBasicVars()[j];
            sb.append(String.format(formatCell, nbv + "(" + fmt(alpha.get(nbv)) + ")"));
        }
        logger.info(sb.toString());

        // 2. Data rows
        for (int i = 0; i < tab.getNumRows(); i++) {
            sb.setLength(0);
            String bv = tab.getBasicVars()[i];
            sb.append(String.format(formatCell, bv + "{" + fmt(alpha.get(bv)) + "}"));

            for (int j = 0; j < nc; j++)
                sb.append(String.format(formatCell, fmt(tab.getValue(i, j))));

            logger.info(sb.toString());
        }
    }

    private String fmt(BigFraction f) {
        if (f == null) return "0";
        return f.getDenominator().equals(java.math.BigInteger.ONE)
                ? f.getNumerator().toString()
                : f.getNumerator() + "/" + f.getDenominator();
    }
}