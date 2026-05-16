package Model;

import java.util.HashMap;
import java.util.Map;

public class LinearConstraint {
    private final Map<String, Double> vars = new HashMap<>();
    private String relOp;
    private Double constTerm;

    public LinearConstraint() {
        this.relOp = "";
        this.constTerm = 0.0;
    }

    public void addVariable(String name, double value) {
        vars.put(name, vars.getOrDefault(name, 0.0) + value);
    }
    public void setRelOp(String relOp) { this.relOp = relOp; }
    public void setConstTerm(Double constTerm) { this.constTerm = constTerm; }

    @Override
    public String toString() {
        return vars + " " + relOp + " " + constTerm;
    }
    public String getRelOp() { return relOp; }
}