package Model;

import java.util.HashMap;
import java.util.Map;

public class StandardizedConstraint {
    private final Map<String, Double> vars = new HashMap<>();
    private String relOp = "=";
    private Double constTerm = 0.0;

    public void addVariable(String name, double value) { vars.put(name, vars.getOrDefault(name, 0.0) + value);}
    public void setRelOp(String relOp) { this.relOp = relOp; }
    public void setConstTerm(Double constTerm) { this.constTerm = constTerm; }

    public Map<String, Double> getVars() { return vars; }
    public String              getRelOp() { return relOp; }
    public Double              getConstTerm() { return constTerm; }

    @Override
    public String toString() { return vars + " " + relOp + " " + constTerm; }
}