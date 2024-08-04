package BusinessPartnerTest;

import dataGenrator.BusinessPartnerGenerator;
import org.testng.annotations.Test;
import pageobjects.BusinessPartners;
import pageobjects.HomePage;
import utilities.BaseClass;

import java.text.ParseException;

public class BusinessPartnerWithVendor extends BaseClass {

    @Test
    public void addBusinessPartnerWithVendor() throws ParseException {
        HomePage hmPage = new HomePage(driver);
        hmPage.clickOnbusinessPartnersLink();

        BusinessPartners bpPage = new BusinessPartners(driver);
        bpPage.clickOnaddIcon();
        bpPage.addBusinessPartnerWithVendor(driver, BusinessPartnerGenerator.businessPartnerDetails);
    }
}
