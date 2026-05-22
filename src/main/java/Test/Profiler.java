package Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class Profiler {

    private static final Logger logger = Logger.getLogger(Profiler.class.getName());
    private static final double NANOS_TO_MILLIS = 1_000_000.0;
    private final Map<String, Long> startTimes = new LinkedHashMap<>();
    private final Map<String, Long> accumulatedTimes = new LinkedHashMap<>();

    public void start(String tag) {
        startTimes.put(tag, System.nanoTime());
    }

    public void stop(String tag) {
        Long startTime = startTimes.remove(tag);
        if (startTime == null) {
            logger.log(Level.WARNING, "Profiler.stop() called for unstarted tag: {0}", tag);
            return;
        }

        long elapsed = System.nanoTime() - startTime;
        accumulatedTimes.put(tag, accumulatedTimes.getOrDefault(tag, 0L) + elapsed);
    }

    public void reset() {
        startTimes.clear();
        accumulatedTimes.clear();
    }

    public Map<String, Long> getAccumulatedTimes() {
        return new LinkedHashMap<>(accumulatedTimes);
    }

    public void logReport() {
        if (!logger.isLoggable(Level.INFO))
            return;

        logger.info("=== PROFILER PERFORMANCE REPORT ===");

        for (Map.Entry<String, Long> entry : accumulatedTimes.entrySet()) {
            String tag = entry.getKey();
            double millis = entry.getValue() / NANOS_TO_MILLIS;

            logger.info(String.format("Tag: %-25s | Time: %10.3f ms", tag, millis));
        }

        // Warn the user about profiles that were started but never closed
        if (!startTimes.isEmpty())
            logger.log(Level.WARNING, "The following tags were started but never stopped: {0}", startTimes.keySet());

        logger.info("===================================");
    }
}