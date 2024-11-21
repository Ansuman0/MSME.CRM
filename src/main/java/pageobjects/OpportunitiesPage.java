package pageobjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OpportunitiesPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @FindBy(css = ".k-loading-image")
    private WebElement kenduGridLoader;

    @FindBy(xpath = "//span[contains(@class,'relative inline-block icon add-permission c-pointer pulse')]")
    private WebElement addIcon;

    @FindBy(xpath = "//span[contains(@class,'icon edit-permission c-pointer pulse')]")
    private WebElement editIcon;

    @FindBy(xpath = "//span[contains(@class,'icon edit-permission c-pointer pulse')]")
    private WebElement activeInactiveIcon;

    @FindBy(xpath = "//span[contains(@class,'icon excel-permission c-pointer pulse')]")
    private WebElement exportToExcelIcon;

    /** Customer Info **/
    @FindBy(xpath = "//input[@name='customerName']")
    private WebElement customerNameTxt;

    @FindBy(xpath = "//select[@name='contactName' and @label='Contact Name']")
    private WebElement contactNameDrp;

    /**Opportunity Details**/
    @FindBy(xpath ="//input[@name='name' and @placeholder='Name']")
    private WebElement nameTxt;

    @FindBy(xpath ="//input[@name='referenceNumber' and @placeholder='Reference Number']")
    private WebElement referenceNumberTxt;

    @FindBy(xpath = "//select[@name='type' and @label='Select Type']")
    private WebElement typeDrp;

    //*All the user Present in the system can to in-charge.
    @FindBy(xpath = "//input[@type='text' and @placeholder='Select Incharge']")
    private WebElement inchargeTxt;

    @FindBy(xpath = "//select[@name='nature' and @label='Select Nature']")
    private WebElement natureDrp;

    @FindBy(xpath = "//select[@name='source' and @label='Select Nature']")
    private WebElement SourceDrp;

    @FindBy(xpath = "//input[@name='bidclosingdate' and @type='date']")
    private WebElement bidClosingDate;

    @FindBy(xpath = "//select[@name='penalty' and @label='Select Penalty']")
    private WebElement penaltyIfAny;

    @FindBy(xpath = "//input[@name='amount']")
    private WebElement amountTxt;


}
