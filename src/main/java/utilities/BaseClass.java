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

public class BaseClass {

	protected WebDriver driver = null;
	private static final Logger logger = LogManager.getLogger(BaseClass.class);

	public WebDriver getDriver() {
		return driver;
	}

	@BeforeSuite(groups = { "SMOKE", "SANITY" })
	public void bsConfig() {
		logger.info("====== DB Connection Successful ======");
	}

	@BeforeClass(alwaysRun = true)
	@Parameters("xmlbrowser")
	public void bcConfig(@Optional String xmlbrowser, ITestContext context) throws Exception {

		if (xmlbrowser != null) {
			driver = Driver.initDriver(xmlbrowser, DriverFactory.browserVersion);
		} else {
			String browser = PropertyUtils.get(ConfigProperties.BROWSER);
			driver = Driver.initDriver(browser, DriverFactory.browserVersion);
		}

	}

	@BeforeMethod(groups = { "SMOKE", "SANITY" })
	public void bmConfig() {
		String username = PropertyUtils.get(ConfigProperties.USERNAME);
		String password = PropertyUtils.get(ConfigProperties.PASSWORD);
		String tenantname = PropertyUtils.get(ConfigProperties.TENANTNAME);

		LoginPage lp = new LoginPage(driver);
		lp.loginToApp(tenantname, username, password);

		logger.info("====== Login to App Successful ======");
	}

	@AfterMethod(groups = { "SMOKE", "SANITY" })
	public void amConfig() {
		HomePage hp = new HomePage(driver); hp.clickOnLogoutIconLink();
		logger.info("====== Logout of App Successful ======");
	}

	@AfterClass(groups = { "SMOKE", "SANITY" })
	public void acConfig() {
		//Driver.quitDriver();
		logger.info("====== Browser Closed ======");
	}

	@AfterSuite(groups = { "SMOKE", "SANITY" })
	public void asConfig() {
		EmailUtility.sendReportEmail(FrameworkConstants.getExtentReportFilePath());
		logger.info("====== DB Connection Closed ======");
	}
}
