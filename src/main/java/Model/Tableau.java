package Model;

import java.util.ArrayList;
import java.util.List;

public class Tableau {
    private  double[] matrix;
    private final String[] basicVars;
    private final String[] nonBasicVars;
    private final List<String> variableOrder;
    private final int numRows;
    private final int numCols;


    public Tableau() {
        this.matrix = new double[0];
        this.basicVars = new String[0];
        this.nonBasicVars = new String[0];
        this.numRows = 0;
        this.numCols = 0;
        this.variableOrder = new ArrayList<>();
    }

    public Tableau(double[] matrix, String[] basicVars, String[] nonBasicVars, List<String> order, int numRows, int numCols) {
        this.matrix = matrix;
        this.basicVars = basicVars;
        this.nonBasicVars = nonBasicVars;
        this.numRows = numRows;
        this.numCols = numCols;
        this.variableOrder = order;
    }

    public int getNumRows() { return numRows; }
    public int getNumCols() { return numCols; }
    public double[] getMatrix() { return matrix; }
    public String[] getBasicVars() { return basicVars; }
    public String[] getNonBasicVars() { return nonBasicVars; }

    public double getValue(int row, int col) {
        if(row < 0 || row >= numRows || col < 0 || col >= numCols)
            throw new IndexOutOfBoundsException("Row or column index out of bounds");

        return matrix[row * numCols + col];
    }

    public void setValue(int row, int col, double value) {
        if(row < 0 || row >= numRows || col < 0 || col >= numCols)
            throw new IndexOutOfBoundsException("Row or column index out of bounds");

        matrix[row * numCols + col] = value;
    }

    public void printTableau() {
        System.out.print("\t");
        for (String nonBasic : nonBasicVars)
            System.out.print(nonBasic + "\t");

        System.out.println("RHS");

        for (int i = 0; i < numRows; i++) {
            System.out.print(basicVars[i] + "\t");
            for (int j = 0; j < numCols; j++)
                System.out.printf("%.2f\t", getValue(i, j));

            System.out.println();
        }
        System.out.println("----------------------------------------");
    }
}