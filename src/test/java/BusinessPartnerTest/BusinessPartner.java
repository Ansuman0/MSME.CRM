package BusinessPartnerTest;

import dataGenrator.BusinessPartnerGenerator;
import org.testng.annotations.Test;
import pageobjects.BusinessPartnersPage;
import pageobjects.HomePage;
import utilities.BaseClass;

import java.text.ParseException;

public class BusinessPartner extends BaseClass {

    @Test
    public void addBusinessPartner() throws ParseException {
        HomePage hmPage = new HomePage(driver);
        hmPage.clickOnbusinessPartnersLink();

        BusinessPartnersPage bpPage = new BusinessPartnersPage(driver);
        bpPage.clickOnaddIcon();
        bpPage.addBusinessPartner(driver, BusinessPartnerGenerator.businessPartnerDetails);
    }
}
