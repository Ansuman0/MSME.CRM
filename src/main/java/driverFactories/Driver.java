package driverFactories;

import java.net.MalformedURLException;
import java.util.Objects;

import org.openqa.selenium.WebDriver;

import enums.ConfigProperties;
import driverFactories.DriverFactory;
import utilities.PropertyUtils;


public final class Driver {

	private Driver() {
	}
	
	public static WebDriver initDriver(String browser,String version) throws Exception {

		if (Objects.isNull(DriverManager.getDriver())) {
			try {
				DriverManager.setDriver(DriverFactory.getDriver(browser, version));
			}catch (MalformedURLException e) {
				throw new MalformedURLException ("Please check the capabilities of browser");
			}
			DriverManager.getDriver().manage().window().maximize();
			DriverManager.getDriver().get(PropertyUtils.get(ConfigProperties.URL));
		}
		return DriverManager.getDriver();
	}

	public static void quitDriver() {

		if (Objects.nonNull(DriverManager.getDriver())) {
			DriverManager.getDriver().quit();
			DriverManager.unload();
		}
	}

}
