package pageobjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
	private WebDriverWait wait;
	private JavascriptExecutor js;

	@FindBy(xpath = "//*[contains(@id,'AbpTenantSwitchLink')]")
	private WebElement tenantSwitchBtn;

	@FindBy(xpath = "//*[contains(@aria-describedby,'Input_NameInfoText')]")
	private WebElement tenantSwitchTxt;

	@FindBy(xpath = "//*[contains(@data-busy-text,'Saving...')]")
	private WebElement tenantSaveBtn;

	@FindBy(xpath = "//*[contains(@name,'LoginInput.UserNameOrEmailAddress')]")
	private WebElement usernameTxt;

	@FindBy(xpath = "//*[contains(@name,'LoginInput.Password')]")
	private WebElement passwordTxt;

	@FindBy(name = "LoginInput.RememberMe")
	private WebElement rememberMeCheckBox;

	@FindBy(xpath = "//*[contains(@value,'Login')]")
	private WebElement loginBtn;

	public LoginPage(WebDriver driver) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set the wait timeout to 10 seconds
		js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	public void loginToApp(String tenantname, String username, String password) {
		try {
			// Switch tenant
			wait.until(ExpectedConditions.elementToBeClickable(tenantSwitchBtn)).click();
			wait.until(ExpectedConditions.visibilityOf(tenantSwitchTxt)).sendKeys(tenantname);
			wait.until(ExpectedConditions.elementToBeClickable(tenantSaveBtn)).click();

			// Wait for tenant switch to complete
			wait.until(ExpectedConditions.invisibilityOf(tenantSaveBtn));

			// Log in
			wait.until(ExpectedConditions.visibilityOf(usernameTxt));
			js.executeScript("arguments[0].scrollIntoView(true);", usernameTxt);
			js.executeScript("arguments[0].click();", usernameTxt);
			usernameTxt.sendKeys(username);

			wait.until(ExpectedConditions.visibilityOf(passwordTxt));
			js.executeScript("arguments[0].scrollIntoView(true);", passwordTxt);
			js.executeScript("arguments[0].click();", passwordTxt);
			passwordTxt.sendKeys(password);

			rememberMeCheckBox.click();
			wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
