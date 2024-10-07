package listeners;

import annotation.FrameworkAnnotation;
import driverFactories.DriverFactory;
import enums.CategoryType;
import org.testng.*;
import reports.ExtentReport;

import static enums.LogType.*;
import static reports.FrameworkLogger.log;


/**
 * Implements {@link ITestListener} and
 * {@link ISuiteListener} to leverage the abstract methods
 * Mostly used to help in extent report generation
 *
 * <pre>
 * Please make sure to add the listener details in the testng.xml file
 * </pre>
 *
 * @author Ansuman
 */
public class ListenerClass implements ITestListener, ISuiteListener {

    /**
     * Initialise the reports with the file name
     */
    @Override
    public void onStart(ISuite suite) {
        try {
            ExtentReport.initReports();
        } catch (Exception e) {
            log(FAIL, "Exception occurred during report initialization: " + e.getMessage());
        }
    }

    /**
     * Terminate the reports
     */
    @Override
    public void onFinish(ISuite suite) {
        ExtentReport.flushReports();
    }

    /**
     * Starts a test node for each TestNG test
     */
    @Override
    public void onTestStart(ITestResult result) {
        String description = result.getMethod().getDescription();
        ExtentReport.createTest(description.isEmpty() ? result.getMethod().getMethodName() : description);

        FrameworkAnnotation annotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class);
        String os = System.getProperty("os.name");
        String device = os + "--" + DriverFactory.browserName + "-" + DriverFactory.browserVersion;

        if (annotation != null) {
            ExtentReport.addAuthors(annotation.author());
            ExtentReport.addCategories(annotation.category());
            ExtentReport.addDevices(new String[]{device});
        } else {
            ExtentReport.addAuthors(new String[]{"Arjun", "Ansuman"});
            ExtentReport.addCategories(new CategoryType[]{CategoryType.SMOKE, CategoryType.MINIREGRESSION});
            ExtentReport.addDevices(new String[]{device});
        }
    }

    /**
     * Handles test success scenario
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        log(PASS, result.getMethod().getMethodName() + " is passed");
        // ELKUtils.sendDetailsToElk(result.getMethod().getDescription(), "pass");
    }

    /**
     * Handles test failure scenario, captures logs and handles retries
     */
    @Override
    public void onTestFailure(ITestResult result) {
        log(FAIL, result.getMethod().getMethodName() + " has failed");
        log(INFO, result.getThrowable().getMessage());

        RetryAnalyzerImpl retryAnalyzer = new RetryAnalyzerImpl();
        retryAnalyzer.retry(result);

        // ELKUtils.sendDetailsToElk(result.getMethod().getDescription(), "fail");
    }

    /**
     * Handles test skip scenario
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        log(SKIP, result.getMethod().getMethodName() + " is skipped");
        log(INFO, result.getThrowable().getMessage());
        // ELKUtils.sendDetailsToElk(result.getMethod().getDescription(), "skip");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // No implementation required for this at the moment
    }

    @Override
    public void onStart(ITestContext context) {
        // No special implementation as of now
    }

    @Override
    public void onFinish(ITestContext context) {
        // No special implementation as of now
    }
}
