package BusinessPartnerTest;

import annotation.FrameworkAnnotation;
import dataGenrator.BusinessPartnerGenerator;
import enums.CategoryType;
import listeners.ListenerClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.BusinessPartnersPage;
import pageobjects.HomePage;
import utilities.BaseClass;

import java.text.ParseException;

@Listeners({ListenerClass.class})
public class BusinessPartner extends BaseClass{

   //@FrameworkAnnotation(author = {"Ansuman"}, category = {CategoryType.SMOKE, CategoryType.SANITY})
    @Test(groups = {"SMOKE", "SANITY"},retryAnalyzer = listeners.RetryAnalyzer.class)
    public void addBusinessPartner() throws ParseException {
        HomePage hmPage = new HomePage(driver);
        hmPage.clickOnbusinessPartnersLink();

        BusinessPartnersPage bpPage = new BusinessPartnersPage(driver);
        bpPage.clickOnaddIcon();
        bpPage.addBusinessPartner(driver, BusinessPartnerGenerator.businessPartnerDetails);
    }
}
