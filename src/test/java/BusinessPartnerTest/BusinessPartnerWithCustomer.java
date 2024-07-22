package BusinessPartnerTest;

import org.testng.annotations.Test;

import dataGenrator.BusinessPartnerGenerator;
import pageObjects.BusinessPartners;
import pageObjects.HomePage;
import utilities.BaseClass;

import java.text.ParseException;

public class BusinessPartnerWithCustomer extends BaseClass {

	@Test
	public void addBusinessPartnerWithCustomer() throws ParseException {
		HomePage hmPage = new HomePage(driver);
		hmPage.clickOnbusinessPartnersLink();

		BusinessPartners bpPage = new BusinessPartners(driver);
		bpPage.clickOnaddIcon();
		bpPage.AddBusinessPartnerWithCustomer(driver, BusinessPartnerGenerator.contactDetails);
	}

}
