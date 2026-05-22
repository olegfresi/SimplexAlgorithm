package Test;

import Model.SimplexResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class BenchmarkWriter {

    private static final Logger logger = Logger.getLogger(BenchmarkWriter.class.getName());
    private static final double NANOS_TO_MILLIS = 1_000_000.0;

    private BenchmarkWriter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static void exportProblemMetrics(String outputFileName, Map<String, Long> times, Map<String, SimplexResult> algebraMetrics) {
        File targetDir = new File("Benchmarks");
        if (!targetDir.exists() && !targetDir.mkdirs()) {
            logger.log(Level.SEVERE, "Could not create structural Benchmarks directory workspace.");
            return;
        }

        File targetFile = new File(targetDir, outputFileName);

        try (PrintWriter writer = new PrintWriter(new FileWriter(targetFile, StandardCharsets.UTF_8))) {
            writer.println("Strategy_Tag,Execution_Time_ms,Total_Pivot_Count,Null_Pivot_Count,Constraints_M,Variables_N");

            for (Map.Entry<String, Long> entry : times.entrySet()) {
                String tag = entry.getKey();
                double milliseconds = entry.getValue() / NANOS_TO_MILLIS;

                SimplexResult res = algebraMetrics.get(tag);

                int totalPivots = (res != null) ? res.getPivotCount() : 0;
                int nullPivots  = (res != null) ? res.getNullPivotCount() : 0;
                int m           = (res != null) ? res.getNumConstraints() : 0;
                int n           = (res != null) ? res.getNumVariables() : 0;

                String cleanTag = tag.replace("\"", "\"\"");
                writer.printf("\"%s\",%.3f,%d,%d,%d,%d%n", cleanTag, milliseconds, totalPivots, nullPivots, m, n);
            }

            logger.log(Level.INFO, "Analytical spreadsheet generated successfully at: {0}", targetFile.getAbsolutePath());

        } catch (IOException _) {
            logger.log(Level.SEVERE, "Critical I/O error occurred while writing benchmark sheets to {0}", outputFileName);
        }
    }
}