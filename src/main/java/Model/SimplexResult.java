package Model;


import org.apache.commons.numbers.fraction.BigFraction;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

// Data holder to pass execution statistics to the testing module
public class SimplexResult {
    private final boolean satisfiable;
    private final int pivotCount;
    private final int nullPivotCount;
    private final int numConstraints;
    private final int numVariables;
    private final List<String> usedOrdering;
    private final Map<String, BigFraction> model;

    public SimplexResult(boolean satisfiable, int pivotCount, int nullPivotCount, int numConstraints, int numVariables,
                         List<String> usedOrdering, Map<String, BigFraction> model) {
        this.satisfiable = satisfiable;
        this.pivotCount = pivotCount;
        this.usedOrdering = usedOrdering;
        this.model = model;
        this.nullPivotCount = nullPivotCount;
        this.numConstraints = numConstraints;
        this.numVariables = numVariables;
    }

    public boolean isSatisfiable() { return satisfiable; }
    public int getPivotCount() { return pivotCount; }
    public List<String> getUsedOrdering() { return usedOrdering; }
    public Map<String, BigFraction> getModel() { return model; }
    public int getNullPivotCount() { return nullPivotCount; }
    public int getNumConstraints() { return numConstraints; }
    public int getNumVariables() { return numVariables; }

    public String modelToString() {
        if (model == null || model.isEmpty()) {
            return "  No assignments available.\n";
        }

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, BigFraction> entry : model.entrySet()) {
            sb.append("  ")
                    .append(entry.getKey())
                    .append(" = ")
                    .append(formatFraction(entry.getValue()))
                    .append("\n");
        }

        return sb.toString();
    }

    private String formatFraction(BigFraction f) {
        BigInteger num = f.getNumerator();
        BigInteger den = f.getDenominator();

        if (num.equals(BigInteger.ZERO))
            return "0";

        if (den.signum() < 0) {
            num = num.negate();
            den = den.negate();
        }

        if (den.equals(BigInteger.ONE))
            return num.toString();

        return num + "/" + den;
    }
}