package constants;

import enums.ConfigProperties;
import utilities.PropertyUtils;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * FrameworkConstants is a utility class that holds constant configuration values.
 */
public final class FrameworkConstants {

	private static final int EXPLICIT_WAIT = 10;
	private static final String DATE_TIME_FORMAT = "ddMMyy_hhmma";
	private static final String REPORT_NAME_PREFIX = "report-";
	private static final String REPORT_NAME_SUFFIX = ".html";

	private static final Path RESOURCES_PATH = Path.of(System.getProperty("user.dir"), "src", "test", "resources");
	private static final Path CONFIG_FILE_PATH = RESOURCES_PATH.resolve("configuration/config.properties");
	private static final Path JSON_CONFIG_FILE_PATH = RESOURCES_PATH.resolve("configuration/lambadaTestConfig.json");
	private static final Path EXCEL_PATH = RESOURCES_PATH.resolve("excel/testdata.xlsx");
	private static final Path EXTENT_REPORT_FOLDER_PATH = Path.of(System.getProperty("user.dir"), "Reports");
	private static final Path EMAIL_CONFIG_FILE_PATH = RESOURCES_PATH.resolve("configuration/configEmailReports.json");

	private static String extentReportFilePath = "";

	private FrameworkConstants() {
		// Prevent instantiation
	}

	private static String createReportPath() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
		String formattedDateTime = LocalDateTime.now().format(dtf);
		String reportName = REPORT_NAME_PREFIX + formattedDateTime + REPORT_NAME_SUFFIX;

		if ("no".equalsIgnoreCase(PropertyUtils.get(ConfigProperties.OVERRIDEREPORTS))) {
			return EXTENT_REPORT_FOLDER_PATH.resolve(reportName).toString();
		}
		return EXTENT_REPORT_FOLDER_PATH.resolve("report.html").toString();
	}

	public static String getExtentReportFilePath() {
		if (extentReportFilePath.isEmpty()) {
			extentReportFilePath = createReportPath();
		}
		return extentReportFilePath;
	}

	public static int getExplicitWait() {
		return EXPLICIT_WAIT;
	}

	public static String getConfigFilePath() {
		return CONFIG_FILE_PATH.toString();
	}

	public static String getJsonConfigFilePath() {
		return JSON_CONFIG_FILE_PATH.toString();
	}

	public static String getEmailConfigFilePath() {
		return EMAIL_CONFIG_FILE_PATH.toString();
	}

	public static String getExcelPath() {
		return EXCEL_PATH.toString();
	}
}
