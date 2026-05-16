package Model;

public class Tableau {

    private final double [] matrix;
    private final int[] basicVars;
    private final int[] nonBasicVars;
    private final int numRows;
    private final int numCols;

    public Tableau(double[] matrix, int[] basicVars, int[] nonBasicVars, int numRows, int numCols) {
        this.matrix = matrix;
        this.basicVars = basicVars;
        this.nonBasicVars = nonBasicVars;
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public double[] getMatrix() {
        return matrix;
    }
    public int[]    getBasicVars() {
        return basicVars;
    }
    public int[]    getNonBasicVars() {
        return nonBasicVars;
    }

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
}