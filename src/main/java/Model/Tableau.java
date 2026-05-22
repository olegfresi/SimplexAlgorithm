package Model;

import org.apache.commons.numbers.fraction.BigFraction;


public class Tableau {

    private final BigFraction[] matrix;
    private final String[]      basicVars;
    private final String[]      nonBasicVars;
    private final int           numRows;
    private final int           numCols;

    public Tableau(BigFraction[] matrix, String[] basicVars, String[] nonBasicVars, int numRows, int numCols) {
        this.matrix      = matrix;
        this.basicVars   = basicVars;
        this.nonBasicVars = nonBasicVars;
        this.numRows     = numRows;
        this.numCols     = numCols;
    }

    public int      getNumRows()     { return numRows; }
    public int      getNumCols()     { return numCols; }

    public String[] getBasicVars()   { return basicVars; }
    public String[] getNonBasicVars() { return nonBasicVars; }

    public BigFraction getValue(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols)
            throw new IndexOutOfBoundsException(
                    "Index out of range: row=" + row + " col=" + col
                            + " (numRows=" + numRows + " numCols=" + numCols + ")");
        return matrix[row * numCols + col];
    }

    public void setValue(int row, int col, BigFraction value) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols)
            throw new IndexOutOfBoundsException(
                    "Index out of range: row=" + row + " col=" + col
                            + " (numRows=" + numRows + " numCols=" + numCols + ")");
        matrix[row * numCols + col] = value;
    }
}