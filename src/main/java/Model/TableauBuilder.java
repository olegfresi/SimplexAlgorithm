package Model;

import java.util.*;

public class TableauBuilder {

    public static Tableau buildTableau(List<StandardizedConstraint> constraints, List<String> chosenVariableOrder) {
        int numRows = constraints.size();
        int numCols = chosenVariableOrder.size() + 1; // +1 for the RHS column (constant terms)

        double[] matrix = new double[numRows * numCols];

        String[] basicVars = new String[numRows];
        String[] nonBasicVars = new String[numCols - 1];

        // Initialize non-basic vars using our chosen execution sorting
        for (int j = 0; j < chosenVariableOrder.size(); j++)
            nonBasicVars[j] = chosenVariableOrder.get(j);

        // Populate matrix rows from constraints
        for (int i = 0; i < numRows; i++) {
            StandardizedConstraint constraint = constraints.get(i);

            // Assign a placeholder name for the row basis
            basicVars[i] = "Basis_Row_" + (i + 1);

            for (int j = 0; j < chosenVariableOrder.size(); j++) {
                String varName = chosenVariableOrder.get(j);
                // Safely inject 0.0 if the specific variable is absent in this constraint
                double coefficient = constraint.getVars().getOrDefault(varName, 0.0);

                matrix[i * numCols + j] = coefficient;
            }

            // Last cell always holds the constant term (RHS)
            matrix[i * numCols + (numCols - 1)] = constraint.getConstTerm();
        }

        return new Tableau(matrix, basicVars, nonBasicVars,chosenVariableOrder, numRows, numCols);
    }

    private TableauBuilder() {
        // Private constructor to prevent instantiation
    }
}