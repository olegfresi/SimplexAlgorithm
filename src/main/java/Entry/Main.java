package Entry;

import Model.LinearConstraint;
import Model.OrderingStrategy;
import Solver.SimplexSolver;
import Test.BenchmarkWriter;
import Test.TestSuite;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    static void main() {
        java.awt.EventQueue.invokeLater(() ->
                new Window().setVisible(true));

        Logger logger = Logger.getLogger(Main.class.getName());
        TestSuite suite = new TestSuite();
        File problemsDir = new File("Problems");

        if (!problemsDir.exists() || !problemsDir.isDirectory()) {
            logger.log(Level.SEVERE, "Warning: 'Problems' directory not found!");
            return;
        }

        File[] problemFiles = problemsDir.listFiles((_, name) ->
                name.startsWith("problem") && name.endsWith(".txt")
        );

        if (problemFiles == null || problemFiles.length == 0)
            return;

        Arrays.sort(problemFiles, Comparator.comparing(File::getName));

        for (File file : problemFiles) {
            String fileName = file.getName();
            String relativePath = "Problems/" + fileName;

            suite.registerTestCase("Standard Order", () -> {
                List<LinearConstraint> prob = TestSuite.getProblem(relativePath);
                return new SimplexSolver(prob, OrderingStrategy.STANDARD).solve();
            });

            suite.registerTestCase("Reverse Order", () -> {
                List<LinearConstraint> prob = TestSuite.getProblem(relativePath);
                return new SimplexSolver(prob, OrderingStrategy.REVERSE).solve();
            });

            suite.registerTestCase("Random Order", () -> {
                List<LinearConstraint> prob = TestSuite.getProblem(relativePath);
                return new SimplexSolver(prob, OrderingStrategy.RANDOM).solve();
            });

            suite.runAllTests();

            String csvName = fileName.replace(".txt", "_analytics.csv");

            BenchmarkWriter.exportProblemMetrics(
                    csvName,
                    suite.getProfiler().getAccumulatedTimes(),
                    suite.getResultsMap()
            );

            suite.clearTests();
        }
    }
}