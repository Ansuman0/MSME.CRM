package WebdriverActionsUtilties;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class consists of generic methods related to all web driver actions
 * 
 * @author Ansuman
 *
 */
public class WebDriverUtility {

	private WebDriverUtility() {
	}

	/**
	 * This method will maximize the window
	 */
	public static void maximizeWindow(WebDriver driver) {
		driver.manage().window().maximize();
	}

	/**
	 * This method will minimize the window
	 * 
	 */
	public static void minimizeWindow(WebDriver driver) {
		driver.manage().window().minimize();
	}

	/**
	 * This method will wait for page to load for 20 seconds
	 * 
	 */
	public static void waitForPageLoad(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}

	/**
	 * This method will wait for 20 seconds for a element to be visible
	 * 
	 */
	public static void waitForElementToBeVisisble(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * This method will use Fluent wait to check the visibility for Elemet.
	 * 
	 * 
	 */
	public static void fluentWait(WebDriver driver, WebElement element) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * This is a custom wait which is used to wait for element and perform click
	 * action
	 * 
	 */
	public static void waitAndClickOnElement(WebElement element) throws InterruptedException {
		int count = 0;
		while (count < 10) {
			try {
				element.click();
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
				count++;
			}
		}
	}

	/**
	 * This method will handle drop down by index
	 * 
	 * 
	 */
	public static void handleDropDown(WebElement element, int index) {
		Select sel = new Select(element);
		sel.selectByIndex(index);
	}

	/**
	 * This method will handle drop down by value
	 * 
	 */
	public static void handleDropDown(WebElement element, String value) {
		Select sel = new Select(element);
		sel.selectByValue(value);
	}

	/**
	 * This method will handle drop down by value
	 * 
	 * 
	 */
	public static void handleDropDown(String visibleText, WebElement element) {
		Select sel = new Select(element);
		sel.selectByVisibleText(visibleText);
	}

	/**
	 * This method will perform mouse hover action
	 * 
	 */
	public static void mouseHoverAction(WebDriver driver, WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();
	}

	/**
	 * This method will right click anywhere on web page
	 * 
	 * @param driver
	 */
	public static void rightClickAction(WebDriver driver) {
		Actions act = new Actions(driver);
		act.contextClick().perform();
	}

	/**
	 * This method will right click on particular web element
	 * 
	 * @param driver
	 */
	public static void rightClickAction(WebDriver driver, WebElement element) {
		Actions act = new Actions(driver);
		act.contextClick(element).perform();
	}

	/**
	 * This method will double click anywhere on web page
	 * 
	 * @param driver
	 */
	public static void doubleClickAction(WebDriver driver) {
		Actions act = new Actions(driver);
		act.doubleClick().perform();
	}

	/**
	 * This method will double click on a web element
	 * 
	 * @param driver
	 */
	public static void doubleClickAction(WebDriver driver, WebElement element) {
		Actions act = new Actions(driver);
		act.doubleClick(element).perform();
	}

	/**
	 * This method will drag and drop from src element to target element
	 * 
	 * @param driver
	 * @param srcElement
	 * @param targetElement
	 */
	public static void dragAndDropAction(WebDriver driver, WebElement srcElement, WebElement targetElement) {
		Actions act = new Actions(driver);
		act.dragAndDrop(srcElement, targetElement).perform();
	}

	/**
	 * This method will drag and drop from src element to target element offsets
	 * 
	 * 
	 */
	public static void dragAndDropAction(WebDriver driver, WebElement src, int x, int y) {
		Actions act = new Actions(driver);
		act.dragAndDropBy(src, x, y).perform();
	}

	/**
	 * This method will handle frame By Index
	 * 
	 */
	public static void switchToFrame(WebDriver driver, int index) {
		driver.switchTo().frame(index);
	}

	/**
	 * This method will handle frame By name or ID
	 * 
	 */
	public static void switchToFrame(WebDriver driver, String nameOrId) {
		driver.switchTo().frame(nameOrId);
	}

	/**
	 * This method will handle frame By web element
	 */
	public static void switchToFrame(WebDriver driver, WebElement element) {
		driver.switchTo().frame(element);
	}

	/**
	 * This method will switch the control from child frame to immediate parent
	 * 
	 * @param driver
	 */
	public static void switchToParentFrame(WebDriver driver) {
		driver.switchTo().parentFrame();
	}

	/**
	 * This method will switch the control from child frame to default Frame
	 * 
	 * @param driver
	 */
	public static void switchToDefaultFrame(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	/**
	 * This method will accept the alert pop-up
	 * 
	 */
	public static void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}

	/**
	 * This method will dismiss the alert pop-up
	 * 
	 */
	public static void dismissAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}

	/**
	 * This method will capture the alert text and return it to user
	 * 
	 */
	public static String getAlertText(WebDriver driver) {
		return driver.switchTo().alert().getText();
	}

	/**
	 * This method will take screen shot and return the absolute path
	 * 
	 * 
	 */
	// @Attachment(value = "ScreenShots of {0}", type = "image/png")
	public static String takeScreenShot(WebDriver driver, String screenShotName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dst = new File(".\\ScreenShots\\" + screenShotName + ".png");
		FileUtils.copyFile(src, dst); // from commons io

		return dst.getAbsolutePath(); // used for extends reports

	}

	/**
	 * This method will scroll down randomly for 500 units
	 * 
	 * 
	 */
	public static void scrollAction(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)", "");

	}

	/**
	 * To Scroll Window Varioes Methos are Below .
	 */
	/**
	 * This method will scroll to an element by Webelemet Value.
	 * 
	 */
	public static void scrollActionToElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoViewIfNeeded()", element);

	}

	/**
	 * This method will press the enter key by usring robort Class
	 * 
	 * 
	 */
	public static void pressEnter() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
	}

	/**
	 * This method will switch the windows based on window title
	 * 
	 * 
	 */
	public static void switchToWindow(WebDriver driver, String partialWindowTitle) {
		// Step 1: Capture all the window IDs
		Set<String> winIDs = driver.getWindowHandles();

		// Step 2: Navigate to each window
		for (String winID : winIDs) {
			// Step 3: capture the title of title of each window
			String actTitle = driver.switchTo().window(winID).getTitle();

			// Step 4: compare the title
			if (actTitle.contains(partialWindowTitle)) {
				break;
			}
		}
	}
}
