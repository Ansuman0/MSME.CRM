package BusinessPartnerTest;

import org.testng.annotations.Test;
import dataGenrator.BusinessPartnerGenerator;
import pageobjects.BusinessPartnersPage;
import pageobjects.HomePage;
import utilities.BaseClass;

import java.text.ParseException;

public class BusinessPartnerWithCustomerTest extends BaseClass {

	@Test
	public void addBusinessPartnerWithCustomer() throws ParseException {
		HomePage hmPage = new HomePage(driver);
		hmPage.clickOnbusinessPartnersLink();

		BusinessPartnersPage bpPage = new BusinessPartnersPage(driver);
		bpPage.clickOnaddIcon();
		bpPage.addBusinessPartnerWithCustomer(driver, BusinessPartnerGenerator.businessPartnerDetails);
	}

}
