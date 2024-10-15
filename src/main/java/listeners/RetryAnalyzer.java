package listeners;

import enums.ConfigProperties;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utilities.PropertyUtils;

/**
 * RetryAnalyzerImpl class implements {@link IRetryAnalyzer} to handle rerunning failed tests based on configuration.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 3;
    private static final String RETRY_ENABLED = "yes";

    /**
     * Returns true if the test needs to be retried based on configuration and retry limit.
     *
     * @param result ITestResult object containing the details of the test result.
     * @return true if the test should be retried, false otherwise.
     */
    @Override
    public boolean retry(ITestResult result) {
        boolean shouldRetry = PropertyUtils.get(ConfigProperties.RETRYFAILEDTESTS).equalsIgnoreCase(RETRY_ENABLED);
        if (shouldRetry && retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            return true;
        }
        return false;
    }
}
