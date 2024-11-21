package reports;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class Loggers {
    private static final Map<Class<?>, Logger> logger = new HashMap<>();

    // Private constructor to prevent instantiation
    private Loggers() {}

    private static Logger getLogger(Class<?> clazz) {
        return logger.computeIfAbsent(clazz, LogManager::getLogger);
    }

    public static void info(Class<?> clazz, Supplier<String> messageSupplier) {
        Logger logger = getLogger(clazz);
        if (logger.isInfoEnabled()) {
            logger.info(messageSupplier.get());
        }
    }

    public static void error(Class<?> clazz, Supplier<String> messageSupplier) {
        Logger logger = getLogger(clazz);
        if (logger.isErrorEnabled()) {
            logger.error(messageSupplier.get());
        }
    }

    public static void debug(Class<?> clazz, Supplier<String> messageSupplier) {
        Logger logger = getLogger(clazz);
        if (logger.isDebugEnabled()) {
            logger.debug(messageSupplier.get());
        }
    }

    public static void warn(Class<?> clazz, Supplier<String> messageSupplier) {
        Logger logger = getLogger(clazz);
        if (logger.isWarnEnabled()) {
            logger.warn(messageSupplier.get());
        }
    }
}