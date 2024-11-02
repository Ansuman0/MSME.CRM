package driverFactories;

import java.net.MalformedURLException;
import java.util.Objects;
import org.openqa.selenium.WebDriver;
import enums.ConfigProperties;
import utilities.PropertyUtils;

public final class Driver {

	private Driver() {
	}

	/**
	 * Initializes the WebDriver based on the specified browser and version.
	 * @param browser the browser type (e.g., Chrome, Firefox)
	 * @param version the browser version
	 * @return WebDriver instance
	 * @throws Exception if any WebDriver initialization issue occurs
	 */
	public static WebDriver initDriver(String browser, String version) throws MalformedURLException {
		WebDriver driver = DriverManager.getDriver();

		if (Objects.isNull(driver)) {
			try {
				driver = DriverFactory.getDriver(browser, version);
				DriverManager.setDriver(driver);
			} catch (Exception e) {
				throw new MalformedURLException(String.format("Please check the browser capabilities for: %s", browser));
			}

			driver.manage().window().maximize();
			driver.get(PropertyUtils.get(ConfigProperties.URL));
		}

		return driver;
	}

	/**
	 * Quits the WebDriver session if it is initialized.
	 */
	public static void quitDriver() {
		WebDriver driver = DriverManager.getDriver();

		if (Objects.nonNull(driver)) {
			driver.quit();
			DriverManager.unload();
		}
	}
}
