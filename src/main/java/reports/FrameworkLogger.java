package reports;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import enums.ConfigProperties;
import enums.LogType;
import utilities.PropertyUtils;

import com.aventstack.extentreports.MediaEntityBuilder;
import utilities.ScreenShortUtils;

/**
 * FrameworkLogger manages logging for test cases, providing various log types and
 * their corresponding actions, including taking screenshots based on configuration.
 * <p>
 * This utility class is not intended to be instantiated.
 * </p>
 */
public final class FrameworkLogger {

    // Prevent instantiation
    private FrameworkLogger() {
        throw new UnsupportedOperationException("FrameworkLogger is a utility class and cannot be instantiated");
    }

    private static final Consumer<String> PASS = message -> ExtentManager.getExtentTest().pass(message);
    private static final Consumer<String> FAIL = message -> ExtentManager.getExtentTest().fail(message);
    private static final Consumer<String> SKIP = message -> ExtentManager.getExtentTest().skip(message);
    private static final Consumer<String> INFO = message -> ExtentManager.getExtentTest().info(message);
    private static final Consumer<String> WARNING = message -> ExtentManager.getExtentTest().warning(message);
    private static final Consumer<String> CONSOLE = message -> System.out.println(String.format("INFO---->%s", message));

    private static final Consumer<String> EXTENT_AND_CONSOLE = PASS.andThen(CONSOLE);

    private static final Consumer<String> TAKE_SCREENSHOT = message -> {
        try {
            String base64Image = ScreenShortUtils.getBase64Image();
            ExtentManager.getExtentTest().info("",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
        } catch (Exception e) {
            System.err.printf("Failed to take screenshot: %s%n", e.getMessage());
        }
    };

    private static final Map<LogType, Consumer<String>> LOG_MAP = new EnumMap<>(LogType.class);
    private static final Map<LogType, Consumer<String>> SCREENSHOT_MAP = new EnumMap<>(LogType.class);

    static {
        initializeLogMaps();
    }

    /**
     * Initializes the log action mappings for different log types.
     */
    private static void initializeLogMaps() {
        LOG_MAP.put(LogType.PASS, PASS);
        LOG_MAP.put(LogType.FAIL, FAIL.andThen(TAKE_SCREENSHOT));
        LOG_MAP.put(LogType.SKIP, SKIP);
        LOG_MAP.put(LogType.INFO, INFO);
        LOG_MAP.put(LogType.WARNING, WARNING);
        LOG_MAP.put(LogType.CONSOLE, CONSOLE);
        LOG_MAP.put(LogType.EXTENTANDCONSOLE, EXTENT_AND_CONSOLE);

        SCREENSHOT_MAP.put(LogType.PASS, PASS.andThen(TAKE_SCREENSHOT));
        SCREENSHOT_MAP.put(LogType.FAIL, FAIL.andThen(TAKE_SCREENSHOT));
        SCREENSHOT_MAP.put(LogType.SKIP, SKIP.andThen(TAKE_SCREENSHOT));
        SCREENSHOT_MAP.put(LogType.INFO, INFO);
        SCREENSHOT_MAP.put(LogType.CONSOLE, CONSOLE);
        SCREENSHOT_MAP.put(LogType.EXTENTANDCONSOLE, EXTENT_AND_CONSOLE.andThen(TAKE_SCREENSHOT));
    }

    /**
     * Logs a message with the specified log type. Takes screenshots if
     * configured to do so.
     *
     * @param status  The type of log (PASS, FAIL, etc.).
     * @param message The message to be logged.
     */
    public static void log(LogType status, String message) {
        boolean shouldTakeScreenshots = PropertyUtils.get(ConfigProperties.PASSEDSTEPSSCREENSHOTS).equalsIgnoreCase("YES");
        Consumer<String> logAction = shouldTakeScreenshots
                ? SCREENSHOT_MAP.getOrDefault(status, EXTENT_AND_CONSOLE)
                : LOG_MAP.getOrDefault(status, EXTENT_AND_CONSOLE);
        logAction.accept(message);
    }
}
