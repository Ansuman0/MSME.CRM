package reports;

import com.aventstack.extentreports.ExtentTest;

import java.util.Objects;

/**
 * ExtentManager class to manage thread-safe {@link ExtentTest} instances.
 * This class provides methods to get, set, and remove {@link ExtentTest}
 * instances in a thread-safe manner using {@link ThreadLocal}.
 * </p>
 *
 * @author Ansuman
 */
public class ExtentManager {

    // Prevent instantiation of the utility class
    private ExtentManager() {
        throw new UnsupportedOperationException("ExtentManager is a utility class and cannot be instantiated");
    }

    private static final ThreadLocal<ExtentTest> extTest = new ThreadLocal<>();

    /**
     * Returns the thread-safe {@link ExtentTest} instance from the ThreadLocal variable.
     *
     * @return Thread-safe {@link ExtentTest} instance, or null if not set.
     */
    public static ExtentTest getExtentTest() {
        return extTest.get();
    }

    /**
     * Sets the {@link ExtentTest} instance in the ThreadLocal variable to ensure thread safety.
     *
     * @param test {@link ExtentTest} instance to be saved in the current thread context.
     */
    public static void setExtentTest(ExtentTest test) {
        if (Objects.nonNull(test)) {
            extTest.set(test);
        } else {
            throw new IllegalArgumentException("ExtentTest instance cannot be null");
        }
    }

    /**
     * Removes the current thread's {@link ExtentTest} instance from the ThreadLocal variable
     * to prevent potential memory leaks.
     */
    public static void unload() {
        extTest.remove();
    }
}
