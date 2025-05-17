package constants;

import enums.ConfigProperties;
import utilities.PropertyUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FrameworkConstants {

	private static final int EXPLICIT_WAIT = 10;
	private static final String RESOURCES_PATH = STR."\{System.getProperty("user.dir")}/src/test/resources";
	private static final String CONFIG_FILE_PATH = STR."\{RESOURCES_PATH}/configuration/config.properties";
	private static final String JSON_CONFIG_FILE_PATH = STR."\{RESOURCES_PATH}/configuration/lambadaTestConfig.json";
	private static final String EXCEL_PATH = STR."\{RESOURCES_PATH}/excel/testdata.xlsx";
	private static final String EXTENT_REPORT_FOLDER_PATH = STR."\{System.getProperty("user.dir")}/Reports";
	private static final String EMAIL_CONFIG_FILE_PATH = STR."\{RESOURCES_PATH}/configuration/configEmailReports.json";

	private static String extentReportFilePath = "";

	private static String createReportPath() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyy_hhmma");
		String formattedDateTime = LocalDateTime.now().format(dtf);
		String reportName = STR."report-\{formattedDateTime}.html";

		if ("no".equalsIgnoreCase(PropertyUtils.get(ConfigProperties.OVERRIDEREPORTS))) {
			return STR."\{EXTENT_REPORT_FOLDER_PATH}/\{reportName}";
		}
		return STR."\{EXTENT_REPORT_FOLDER_PATH}/report.html";
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
		return CONFIG_FILE_PATH;
	}

	public static String getJsonConfigFilePath() {
		return JSON_CONFIG_FILE_PATH;
	}

	public static String getEmailConfigFilePath() {
		return EMAIL_CONFIG_FILE_PATH;
	}

	public static String getExcelPath() {
		return EXCEL_PATH;
	}
}
