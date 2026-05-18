package Solver;


import java.util.List;

// Data holder to pass execution statistics to the testing module
public class SimplexResult {
    private final boolean satisfiable;
    private final int pivotCount;
    private final List<String> usedOrdering;

    public SimplexResult(boolean satisfiable, int pivotCount, List<String> usedOrdering) {
        this.satisfiable = satisfiable;
        this.pivotCount = pivotCount;
        this.usedOrdering = usedOrdering;
    }

    public boolean isSatisfiable() { return satisfiable; }
    public int getPivotCount() { return pivotCount; }
    public List<String> getUsedOrdering() { return usedOrdering; }
}