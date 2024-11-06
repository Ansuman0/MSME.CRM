package utilities;

import constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import driverFactories.Driver;
import driverFactories.DriverFactory;
import enums.ConfigProperties;
import pageobjects.HomePage;
import pageobjects.LoginPage;

import org.testng.ITestContext;
import org.testng.annotations.*;
import static enums.LogType.CONSOLE;
import static reports.FrameworkLogger.log;

public class BaseClass {

	protected WebDriver driver = null;

	public WebDriver getDriver() {
		return driver;
	}

	@BeforeSuite(groups = { "SMOKE", "SANITY" })
	public void bsConfig() {
		log(CONSOLE, String.format("====== DB Connection Successful ======"));
		//log(info("====== DB Connection Successful ======"));
	}

	@BeforeClass(alwaysRun = true)
	@Parameters({"xmlbrowser", "author"})
	public void bcConfig(@Optional String xmlbrowser, @Optional String author, ITestContext context) throws Exception {
		String browser = (xmlbrowser != null) ? xmlbrowser : PropertyUtils.get(ConfigProperties.BROWSER);
		driver = Driver.initDriver(browser, DriverFactory.getBrowserVersion());
	}

	@BeforeMethod(groups = { "SMOKE", "SANITY" })
	public void bmConfig() {
		String username = PropertyUtils.get(ConfigProperties.USERNAME);
		String password = PropertyUtils.get(ConfigProperties.PASSWORD);
		String tenantname = PropertyUtils.get(ConfigProperties.TENANTNAME);

		LoginPage lp = new LoginPage(driver);
		lp.loginToApp(tenantname, username, password);

		log(CONSOLE, String.format("====== Login to App Successful ======"));
	}

	@AfterMethod(groups = { "SMOKE", "SANITY" })
	public void amConfig() {
		HomePage hp = new HomePage(driver); hp.clickOnLogoutIconLink();
		log(CONSOLE, String.format("====== Logout of App Successful ======"));
	}

	@AfterClass(groups = { "SMOKE", "SANITY" })
	public void acConfig() {
		Driver.quitDriver();
		log(CONSOLE, String.format("====== Browser Closed ======"));
	}

	@AfterSuite(groups = { "SMOKE", "SANITY" })
	public void asConfig() {

		String emailtestreport = "yes"; // dynamically set to "yes" or "no"
		if (emailtestreport.equalsIgnoreCase(PropertyUtils.get(ConfigProperties.EMAILTESTREPORT))) {
				EmailUtility.sendReportEmail(FrameworkConstants.getExtentReportFilePath());
			}
		log(CONSOLE, String.format("====== DB Connection Closed ======"));
	}
}
