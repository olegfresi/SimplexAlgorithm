package Solver;

import java.util.*;


public class VariableOrderer {

    // Standard order: User variables first (alphabetically), then internal _scv tokens
    public static List<String> getStandardOrder(Set<String> allVariables) {
        List<String> sortedVars = new ArrayList<>(allVariables);

        sortedVars.sort((v1, v2) -> {
            boolean isScv1 = v1.startsWith("_scv");
            boolean isScv2 = v2.startsWith("_scv");

            // If one is _scv and the other is not, the user variable comes first
            if (isScv1 && !isScv2) return 1;
            if (!isScv1 && isScv2) return -1;

            // If both are user variables or both are _scv, resolve using standard string comparison
            // This naturally sorts x1 < x2 < x3 and _scv1 < _scv2
            return v1.compareTo(v2);
        });

        return sortedVars;
    }

    // Alternative order to test performance (e.g., Reverse order: _scv first, then user variables)
    public static List<String> getReverseOrder(Set<String> allVariables) {
        List<String> sortedVars = getStandardOrder(allVariables);
        Collections.reverse(sortedVars);
        return sortedVars;
    }

    // Randomized order to benchmark how chaotic column layouts affect pivoting counts
    public static List<String> getRandomOrder(Set<String> allVariables) {
        List<String> sortedVars = new ArrayList<>(allVariables);
        Collections.shuffle(sortedVars);
        return sortedVars;
    }

    private VariableOrderer() {
        // Private constructor to prevent instantiation
    }
}