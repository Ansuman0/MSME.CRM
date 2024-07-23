package pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;

	@FindBy(xpath = "//*[contains(span[@class='right-pf-label'], 'Dashboard')]")
	private WebElement dashboardBreadcum;

	@FindBy(xpath = "//aside//li[contains(@id, 'customers')]")
	private WebElement businessPartnersIcon;

	@FindBy(xpath = "//aside//li[contains(@id, 'opportunities')]")
	private WebElement opportunitiesIcon;

	@FindBy(xpath = "//aside//li[contains(@id, 'quotations')]")
	private WebElement qutationsIcon;

	@FindBy(xpath = "//aside//li[contains(@id, 'myOpportunities')]")
	private WebElement myOpportunitiesIcon;

	@FindBy(xpath = "//aside//li[contains(@id, 'myQuotations')]")
	private WebElement myQuotationsIcon;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	public void verifySideMenuGetLoaded() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".menu-items #dashboard")));
	}

	public void verifyDashboardGetLoaded() {
		js.executeScript("return document.readyState").toString().equals("complete");
		dashboardBreadcum.getText();
	}

	public void clickOnbusinessPartnersLink() {
		verifySideMenuGetLoaded();
		js.executeScript("return document.readyState").toString().equals("complete");
		wait.until(ExpectedConditions.elementToBeClickable(businessPartnersIcon)).click();
	}

	public void clickOnOpportunitiesLink() {
		verifySideMenuGetLoaded();
		wait.until(ExpectedConditions.elementToBeClickable(opportunitiesIcon)).click();
	}

	public void clickOnQutationsLink() {
		verifySideMenuGetLoaded();
		wait.until(ExpectedConditions.elementToBeClickable(qutationsIcon)).click();
	}

	public void clickOnMyOpportunitiesLink() {
		verifySideMenuGetLoaded();
		wait.until(ExpectedConditions.elementToBeClickable(myOpportunitiesIcon)).click();
	}

	public void clickOnMyQuotationsIconLink() {
		verifySideMenuGetLoaded();
		wait.until(ExpectedConditions.elementToBeClickable(myQuotationsIcon)).click();
	}

}
