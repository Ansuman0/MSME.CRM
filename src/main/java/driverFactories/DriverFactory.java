package driverFactories;

import exceptions.DriverCreationException; // Import your custom exception
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.PageLoadStrategy;
import org.testng.annotations.Parameters;

import enums.ConfigProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.JsonUtils;
import utilities.PropertyUtils;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import org.openqa.selenium.MutableCapabilities;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static String browserVersion = "";
    public static String browserName = "";
    static WebDriverManager driverManager;

    @Parameters("browser")
    public static WebDriver getDriver(String browser, String version) throws Exception {
        WebDriver driver;
        String runMode = PropertyUtils.get(ConfigProperties.RUNMODE);

        switch (runMode.toLowerCase()) {
            case "local":
                driver = createLocalDriver(browser);
                break;
            case "remote":
                driver = createRemoteDriver(getBrowserOptions(browser));
                break;
            case "lambdatest":
                driver = createLambdaTestDriver(browser);
                break;
            default:
                throw new IllegalArgumentException("Unsupported run mode: " + runMode);
        }

        setBrowserDetails(driver);
        return driver;
    }

    private static WebDriver createLocalDriver(String browser) {

        switch (browser.toLowerCase()) {

            case "chrome":
                driverManager = WebDriverManager.chromedriver();
                ChromeOptions chromeOptions = new ChromeOptions();
                setCommonOptions(chromeOptions);
                return new ChromeDriver(chromeOptions);
            case "firefox":
                driverManager = WebDriverManager.firefoxdriver();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                setCommonOptions(firefoxOptions);
                return new FirefoxDriver(firefoxOptions);
            case "edge":
                driverManager = WebDriverManager.edgedriver();
                EdgeOptions edgeOptions = new EdgeOptions();
                setCommonOptions(edgeOptions);
                return new EdgeDriver(edgeOptions);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static MutableCapabilities getBrowserOptions(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return new ChromeOptions();
            case "firefox":
                return new FirefoxOptions();
            case "edge":
                return new EdgeOptions();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static WebDriver createRemoteDriver(MutableCapabilities options) throws DriverCreationException {
        try {
            setCommonOptions(options);
            URI seleniumGridUri = new URI(JsonUtils.get(ConfigProperties.SELENIUMGRIDURL));
            URL seleniumGridUrl = seleniumGridUri.toURL();
            return new RemoteWebDriver(seleniumGridUrl, options);
        } catch (Exception e) {
            throw new DriverCreationException("Failed to create Remote WebDriver", e);
        }
    }

    private static WebDriver createLambdaTestDriver(String browser) throws Exception {
        MutableCapabilities browserOptions = getBrowserOptions(browser);

        // Add LambdaTest specific capabilities
        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", JsonUtils.get(ConfigProperties.USERNAME));
        ltOptions.put("accessKey", JsonUtils.get(ConfigProperties.ACCESSKEY));
        ltOptions.put("project", JsonUtils.get(ConfigProperties.PROJECT));
        ltOptions.put("selenium_version", JsonUtils.get(ConfigProperties.SELENIUMVERSION));
        ltOptions.put("w3c", true);

        browserOptions.setCapability("LT:Options", ltOptions);

        try {
            URI lambdaTestUri = new URI(JsonUtils.get(ConfigProperties.LAMBDAURL));
            URL lambdaTestUrl = lambdaTestUri.toURL();
            return new RemoteWebDriver(lambdaTestUrl, browserOptions);
        } catch (Exception e) {
            throw new DriverCreationException("Failed to create LambdaTest WebDriver", e);
        }
    }

    private static void setCommonOptions(MutableCapabilities options) {
        options.setCapability("acceptInsecureCerts", true);
        options.setCapability("pageLoadStrategy", PageLoadStrategy.EAGER);

        if (options instanceof ChromeOptions || options instanceof FirefoxOptions) {
            ((ChromeOptions) options).addArguments("--incognito", "--start-maximized");
        } else if (options instanceof EdgeOptions) {
            ((EdgeOptions) options).addArguments("--start-maximized");
        }
    }

    private static void setBrowserDetails(WebDriver driver) {
        if (driver instanceof RemoteWebDriver) {
            browserVersion = ((RemoteWebDriver) driver).getCapabilities().getBrowserVersion();
            browserName = ((RemoteWebDriver) driver).getCapabilities().getBrowserName();
            System.out.println("------ Browser Name: " + browserName + ", Version: " + browserVersion);
        }
    }
}
