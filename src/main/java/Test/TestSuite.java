package Test;

import Model.LinearConstraint;
import Model.SimplexResult;
import Parser.Generated.ConstraintsGrammarLexer;
import Parser.Generated.ConstraintsGrammarParser;
import Parser.Visitor.ConstraintLoaderVisitor;
import Parser.Visitor.ErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class TestSuite {

    private static final Logger logger = Logger.getLogger(TestSuite.class.getName());
    private final List<NamedTestRecord> testRecords = new ArrayList<>();
    private final Profiler profiler = new Profiler();
    private final Map<String, SimplexResult> resultsMap = new LinkedHashMap<>();

    public static List<LinearConstraint> getProblem(String fileName) {
        Path filePath = resolveFilePath(fileName);
        if (filePath == null) {
            logger.log(Level.SEVERE, "Target file could not be resolved: {0}", fileName);
            return new ArrayList<>();
        }
        try {
            CharStream input = CharStreams.fromPath(filePath);
            ConstraintsGrammarLexer lexer = new ConstraintsGrammarLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new ErrorListener());
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ConstraintsGrammarParser parser = new ConstraintsGrammarParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new ErrorListener());
            ParseTree tree = parser.file();
            ConstraintLoaderVisitor loader = new ConstraintLoaderVisitor();
            loader.visit(tree);
            return loader.getConstraints();
        } catch (IOException _) {
            logger.log(Level.SEVERE, "Failed to parse system configuration via ANTLR pipeline for: {0}", fileName);
            return new ArrayList<>();
        }
    }

    private static Path resolveFilePath(String fileName) {
        var resource = TestSuite.class.getClassLoader().getResource(fileName);
        if (resource != null) return Path.of(new File(resource.getFile()).toURI());
        File file = new File(fileName);
        if (file.exists()) return file.toPath();
        return null;
    }

    public void registerTestCase(String name, Supplier<SimplexResult> testLogic) {
        testRecords.add(new NamedTestRecord(name, () -> {
            profiler.start(name);
            try {
                SimplexResult res = testLogic.get();
                if (res != null) {
                    resultsMap.put(name, res);
                }
            } catch (Exception _) {
                logger.log(Level.SEVERE, "Exception occurred during execution of: {0}", name);
            } finally {
                profiler.stop(name);
            }
        }));
    }

    public void runAllTests() {
        logger.info("Starting Simplex Algorithm Test Suite with Extended Metrics...");
        profiler.reset();
        resultsMap.clear();
        for (NamedTestRecord rec : testRecords)
            rec.logic().run();

        logger.info("All test cases completed.");
        profiler.logReport();
    }

    public Profiler getProfiler() { return profiler; }
    public Map<String, SimplexResult> getResultsMap() { return resultsMap; }
    public void clearTests() { testRecords.clear(); profiler.reset(); resultsMap.clear(); }

    private record NamedTestRecord(String name, Runnable logic) {}
}