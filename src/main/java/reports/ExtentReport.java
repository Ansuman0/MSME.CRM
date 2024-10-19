package reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import constants.FrameworkConstants;
import enums.CategoryType;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import exceptions.FrameworkException;

/**
 * ExtentReport handles the initialization, creation, and flushing of
 * {@link ExtentReports}. It ensures thread safety via {@link ExtentManager}.
 *
 * <p>
 * This utility class prevents instantiation and provides static methods to manage
 * report lifecycle.
 * </p>
 *
 * @author Ansuman
 */
public final class ExtentReport {

    private static ExtentReports extent;

    /**
     * Private constructor to prevent instantiation.
     */
    private ExtentReport() {
        throw new UnsupportedOperationException("ExtentReport is a utility class and cannot be instantiated");
    }

    /**
     * Initializes the Extent Reports configuration and specifies the report
     * generation path.
     */
    public static void initReports() {
        if (Objects.isNull(extent)) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(FrameworkConstants.getExtentReportFilePath());
            extent.attachReporter(spark);
            configureSparkReporter(spark);
        }
    }

    /**
     * Configures the Spark Reporter with theme, document title, and report name.
     *
     * @param spark The {@link ExtentSparkReporter} instance to configure.
     */
    private static void configureSparkReporter(ExtentSparkReporter spark) {
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Automation Reports");
        spark.config().setReportName("MSME.CRM");
    }

    /**
     * Flushes the reports to ensure all logs are written properly. Opens the
     * report in the default desktop browser and unloads ThreadLocal variables.
     */
    public static void flushReports() {
        if (Objects.nonNull(extent)) {
            extent.flush();
            ExtentManager.unload();
            openReportInBrowser();
        }
    }

    /**
     * Opens the generated report in the default web browser.
     */
    private static void openReportInBrowser() throws FrameworkException {
        try {
            Desktop.getDesktop().browse(new File(FrameworkConstants.getExtentReportFilePath()).toURI());
        } catch (IOException e) {
            throw new FrameworkException("Failed to open the report in browser.", e);
        }
    }

    /**
     * Creates a test case node in the report.
     *
     * @param testCaseName Name of the test case to be created.
     */
    public static void createTest(String testCaseName) {
        ExtentManager.setExtentTest(extent.createTest(testCaseName));
    }

    /**
     * Adds authors to the test case in the report.
     *
     * @param authors Array of authors for the test case.
     */
    public static void addAuthors(String[] authors) {
        for (String author : authors) {
            ExtentManager.getExtentTest().assignAuthor(author);
        }
    }

    /**
     * Assigns categories (tags) to the test case in the report.
     *
     * @param categories Array of {@link CategoryType} the test belongs to.
     */
    public static void addCategories(CategoryType[] categories) {
        for (CategoryType category : categories) {
            ExtentManager.getExtentTest().assignCategory(category.toString());
        }
    }

    /**
     * Assigns devices (e.g., OS name and browser version) to the test case in
     * the report.
     *
     * @param devices Array of device names for the test case.
     */
    public static void addDevices(String[] devices) {
        for (String device : devices) {
            ExtentManager.getExtentTest().assignDevice(device);
        }
    }
}
