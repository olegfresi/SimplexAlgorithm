package Model;

import org.apache.commons.numbers.fraction.BigFraction;
import java.util.HashMap;
import java.util.Map;

public class StandardizedConstraint {
    private final Map<String, BigFraction> vars = new HashMap<>();
    private String relOp = "=";
    private BigFraction constTerm = BigFraction.ZERO;

    public void addVariable(String name, BigFraction value) { vars.put(name, vars.getOrDefault(name, BigFraction.ZERO).add(value));}
    public void setRelOp(String relOp) { this.relOp = relOp; }
    public void setConstTerm(BigFraction constTerm) { this.constTerm = constTerm; }

    public Map<String, BigFraction> getVars() { return vars; }

    @Override
    public String toString() { return vars + " " + relOp + " " + constTerm; }
}