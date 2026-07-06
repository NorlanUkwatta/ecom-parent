package com.techmart.core.monitoring;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MetricsLogger {

    private static final Logger logger = Logger.getLogger("TechMartMetrics");
    private static boolean isInitialized = false;

    public static synchronized Logger getLogger() {
        if (!isInitialized) {

            try {
                File logDir = new File("logs");
                if (!logDir.exists()) logDir.mkdir();

                FileHandler fileHandler = new FileHandler("logs/techmart-performance.log", 10000000, 5, true);
                fileHandler.setFormatter(new SimpleFormatter());

                logger.addHandler(fileHandler);
                logger.setUseParentHandlers(true);
                isInitialized = true;

            } catch (Exception e) {
                System.err.println("Failed to initialize central logger.");
            }
        }
        return logger;
    }
}