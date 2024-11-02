package driverFactories;

import exceptions.DriverCreationException; //custom exception
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.Parameters;

import enums.ConfigProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.JsonUtils;
import utilities.PropertyUtils;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import org.openqa.selenium.MutableCapabilities;

public final class DriverFactory {

    private DriverFactory() {
    }

    private static String browserVersion = "";
    private static String browserName = "";
    static WebDriverManager driverManager;

    @Parameters("browser")
    public static WebDriver getDriver(final String browser, final String version) throws DriverCreationException {
        WebDriver driver;
        final String runMode = PropertyUtils.get(ConfigProperties.RUNMODE);

        try {
            switch (runMode.toLowerCase()) {
                case "local" -> driver = createLocalDriver(browser);
                case "remote" -> driver = createRemoteDriver(getBrowserOptions(browser));
                case "lambdatest" -> driver = createLambdaTestDriver(browser);
                case "browserstack" -> driver = createBrowserStackDriver(browser);
                default -> throw new IllegalArgumentException(String.format("Unsupported run mode: %s", runMode));
            }
        } catch (FileNotFoundException e) {
            throw new DriverCreationException("Configuration file not found for driver setup", e);
        } catch (IllegalArgumentException e) {
            throw new DriverCreationException("Invalid browser or run mode specified", e);
        } catch (Exception e) {
            throw new DriverCreationException("Unexpected error during WebDriver creation", e);
        }

        setBrowserDetails(driver);
        return driver;
    }

    private static WebDriver createLocalDriver(final String browser) {

        switch (browser.toLowerCase()) {
            case "chrome" -> {
                driverManager = WebDriverManager.chromedriver();
                ChromeOptions chromeOptions = new ChromeOptions();
                setCommonOptions(chromeOptions);
                return new ChromeDriver(chromeOptions);
            }
            case "firefox" -> {
                driverManager = WebDriverManager.firefoxdriver();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                setCommonOptions(firefoxOptions);
                return new FirefoxDriver(firefoxOptions);
            }
            case "edge" -> {
                driverManager = WebDriverManager.edgedriver();
                EdgeOptions edgeOptions = new EdgeOptions();
                setCommonOptions(edgeOptions);
                return new EdgeDriver(edgeOptions);
            }
            case "safari" -> {
                driverManager = WebDriverManager.safaridriver();
                SafariOptions safariOptions = new SafariOptions();
                setCommonOptions(safariOptions);
                return new SafariDriver(safariOptions);
            }
            default -> throw new IllegalArgumentException(String.format("Unsupported browser: %s", browser));
        }
    }

    private static MutableCapabilities getBrowserOptions(final String browser) {
        return switch (browser.toLowerCase()) {
            case "chrome" -> new ChromeOptions();
            case "firefox" -> new FirefoxOptions();
            case "edge" -> new EdgeOptions();
            case "safari" -> new SafariOptions();
            default -> throw new IllegalArgumentException(String.format("Unsupported browser: %s", browser));
        };
    }

    private static WebDriver createRemoteDriver(final MutableCapabilities options) throws DriverCreationException {
        try {
            setCommonOptions(options);
            URI seleniumGridUri = new URI(JsonUtils.get(ConfigProperties.SELENIUMGRIDURL));
            URL seleniumGridUrl = seleniumGridUri.toURL();
            return new RemoteWebDriver(seleniumGridUrl, options);
        } catch (Exception e) {
            throw new DriverCreationException("Failed to create Remote WebDriver", e);
        }
    }

    private static WebDriver createLambdaTestDriver(final String browser) throws FileNotFoundException, DriverCreationException {
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

    private static WebDriver createBrowserStackDriver(final String browser) throws DriverCreationException, FileNotFoundException {
        MutableCapabilities browserOptions = getBrowserOptions(browser);

        // Add BrowserStack specific capabilities
        HashMap<String, Object> bsOptions = new HashMap<>();
        bsOptions.put("userName", JsonUtils.get(ConfigProperties.USERNAME));
        bsOptions.put("accessKey", JsonUtils.get(ConfigProperties.ACCESSKEY));
        bsOptions.put("projectName", JsonUtils.get(ConfigProperties.PROJECT));
        browserOptions.setCapability("bstack:options", bsOptions);

        try {
            URI browserStackUri = new URI(JsonUtils.get(ConfigProperties.BROWSERSTACKURL));
            URL browserStackUrl = browserStackUri.toURL();
            return new RemoteWebDriver(browserStackUrl, browserOptions);
        } catch (Exception e) {
            throw new DriverCreationException("Failed to create BrowserStack WebDriver", e);
        }
    }

    private static void setCommonOptions(final MutableCapabilities options) {
        options.setCapability("acceptInsecureCerts", true);
        options.setCapability("pageLoadStrategy", PageLoadStrategy.EAGER);

        switch (options) {
            case ChromeOptions chromeOptions -> chromeOptions.addArguments("--incognito", "--start-maximized");
            case FirefoxOptions firefoxOptions -> firefoxOptions.addArguments("--incognito", "--start-maximized");
            case EdgeOptions edgeOptions -> edgeOptions.addArguments("--start-maximized");
            case SafariOptions safariOptions -> {
                safariOptions.setCapability("safari.cleanSession", true);
                safariOptions.setCapability("safari.defaultWindowFeatures", true);
            }
            default -> throw new IllegalArgumentException(
                    String.format("Unsupported options type: %s", options.getClass().getSimpleName())
            );
        }
    }

    private static void setBrowserDetails(final WebDriver driver) {
        if (driver instanceof RemoteWebDriver remoteDriver) {
            var capabilities = remoteDriver.getCapabilities();
            browserVersion = (capabilities.getBrowserVersion() != null) ? capabilities.getBrowserVersion() : "Unknown Version";
            browserName = (capabilities.getBrowserName() != null) ? capabilities.getBrowserName() : "Unknown Browser";
            System.out.printf("------ Browser Name: %s, Version: %s%n", browserName, browserVersion);
        }
    }

    // Getter methods for browser details
    public static String getBrowserVersion() {
        return browserVersion;
    }

    public static String getBrowserName() {
        return browserName;
    }
}
