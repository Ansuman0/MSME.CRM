package dataGenrator;

import com.github.javafaker.Faker;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import java.awt.*;

public class BusinessPartnerGenerator {
	public static final Map<String, String> contactDetails = new HashMap<>();

	static {
		Faker faker = new Faker();

		String companyLogo = null;
		try {
			companyLogo = generateImageAsBase64Jpg();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String companyName = faker.company().name();
		String companyType = faker.company().industry();
		String parentCompany = faker.company().name();
		String cinIip = faker.idNumber().valid();
		String gstIn = faker.finance().iban(); // Note: Adjust this as needed
		String panNumber = generatePanNumber(); // Note: Adjust this as needed
		String phoneNumber = faker.phoneNumber().phoneNumber();
		String faxNumber = faker.phoneNumber().phoneNumber();
		String email = faker.internet().emailAddress();
		String website = faker.internet().url();
		String gstType = "Regular"; // Faker doesn't have GST types, so set a default value
		String openingBalance = faker.number().digits(5);
		String accountOwner = faker.name().fullName();
		String industry = faker.company().industry();
		String priority = "High"; // Faker doesn't have priorities, so set a default value
		String source = faker.company().name();
		String corporationDate = faker.date().past(10000, TimeUnit.DAYS).toString();
		String corporationCountry = faker.address().country();
		String location = faker.address().fullAddress();
		String description = faker.lorem().sentence();

		String invAddressline1 = faker.address().streetAddress();
		String invAddressline2 = faker.address().secondaryAddress();
		String invState = faker.address().state();
		String invContactName = faker.name().fullName();
		String invPhone = faker.phoneNumber().phoneNumber();
		String invEmail = faker.internet().emailAddress();

		String delAddressline1 = faker.address().streetAddress();
		String delAddressline2 = faker.address().secondaryAddress();
		String delState = faker.address().state();
		String delContactName = faker.name().fullName();
		String delPhone = faker.phoneNumber().phoneNumber();
		String delEmail = faker.internet().emailAddress();

		String otherAddressline1 = faker.address().streetAddress();
		String otherAddressline2 = faker.address().secondaryAddress();
		String otherState = faker.address().state();
		String otherContactName = faker.name().fullName();
		String otherPhone = faker.phoneNumber().phoneNumber();
		String otherEmail = faker.internet().emailAddress();;

		String contactInfoTitle = faker.name().title();
		String contactInfoName = faker.name().fullName();
		String contactInfoDepartment = faker.company().profession();
		String contactInfoDesignation = faker.job().title();
		String contactInfoEmail = faker.internet().emailAddress();
		String contactInfoPhoneNumber = faker.phoneNumber().phoneNumber();
		String contactInfoAlternativePhoneNumber = faker.phoneNumber().cellPhone();
		String contactInfoDescription = faker.lorem().sentence();

		// Fill the map with generated values
		contactDetails.put("companyLogo", companyLogo);
		contactDetails.put("companyName", companyName);
		contactDetails.put("companyType", companyType);
		contactDetails.put("parentCompany", parentCompany);
		contactDetails.put("cinIip", cinIip);
		contactDetails.put("gstIn", gstIn);
		contactDetails.put("panNumber", panNumber);
		contactDetails.put("phoneNumber", phoneNumber);
		contactDetails.put("faxNumber", faxNumber);
		contactDetails.put("email", email);
		contactDetails.put("website", website);
		contactDetails.put("gstType", gstType);
		contactDetails.put("openingBalance", openingBalance);
		contactDetails.put("accountOwner", accountOwner);
		contactDetails.put("industry", industry);
		contactDetails.put("priority", priority);
		contactDetails.put("source", source);
		contactDetails.put("corporationDate", corporationDate);
		contactDetails.put("corporationCountry", corporationCountry);
		contactDetails.put("location", location);
		contactDetails.put("description", description);

		contactDetails.put("invAddressline1", invAddressline1);
		contactDetails.put("invAddressline2", invAddressline2);
		contactDetails.put("invState", invState);
		contactDetails.put("invContactName", invContactName);
		contactDetails.put("invPhone", invPhone);
		contactDetails.put("invEmail", invEmail);

		contactDetails.put("delAddressline1", delAddressline1);
		contactDetails.put("delAddressline2", delAddressline2);
		contactDetails.put("delState", delState);
		contactDetails.put("delContactName", delContactName);
		contactDetails.put("delPhone", delPhone);
		contactDetails.put("delEmail", delEmail);

		contactDetails.put("otherAddressline1", otherAddressline1);
		contactDetails.put("otherAddressline2", otherAddressline2);
		contactDetails.put("otherState", otherState);
		contactDetails.put("otherContactName", otherContactName);
		contactDetails.put("otherPhone", otherPhone);
		contactDetails.put("otherEmail", otherEmail);

		contactDetails.put("contactInfoTitle", contactInfoTitle);
		contactDetails.put("contactInfoName", contactInfoName);
		contactDetails.put("contactInfoDepartment", contactInfoDepartment);
		contactDetails.put("contactInfoDesignation", contactInfoDesignation);
		contactDetails.put("contactInfoEmail", contactInfoEmail);
		contactDetails.put("contactInfoPhoneNumber", contactInfoPhoneNumber);
		contactDetails.put("contactInfoAlternativePhoneNumber", contactInfoAlternativePhoneNumber);
		contactDetails.put("contactInfoDescription", contactInfoDescription);
	}

	public static String generatePanNumber() {
		final Faker FAKER = new Faker();
		StringBuilder panNumber = new StringBuilder();

		// Generate first 3 alphabetic characters
		for (int i = 0; i < 3; i++) {
			panNumber.append((char) (FAKER.random().nextInt(26) + 'A'));
		}

		// The fourth character based on the type of assesses
		char[] assesseeTypes = { 'C', 'P', 'H', 'F', 'A', 'T', 'B', 'L', 'J', 'G' };
		char fourthCharacter = assesseeTypes[FAKER.random().nextInt(assesseeTypes.length)];
		panNumber.append(fourthCharacter);

		// The fifth character is the first character of the surname / last name
		// Here, for simplicity, we randomly choose a character
		char fifthCharacter = (char) (FAKER.random().nextInt(26) + 'A');
		panNumber.append(fifthCharacter);

		// Generate next 4 numeric characters
		for (int i = 0; i < 4; i++) {
			panNumber.append(FAKER.random().nextInt(10));
		}

		// Generate last alphabetic character
		panNumber.append((char) (FAKER.random().nextInt(26) + 'A'));

		return panNumber.toString();

	}

	public static String generateImageAsBase64Jpg() throws IOException {
		final Faker FAKER = new Faker();
		int width = 200;
		int height = 200;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics = image.createGraphics();
		graphics.setPaint(Color.WHITE);
		graphics.fillRect(0, 0, width, height);

		graphics.setPaint(Color.BLACK);
		graphics.setFont(new Font("Arial", Font.BOLD, 20));
		graphics.drawString(FAKER.company().name(), 10, height / 2);

		graphics.dispose();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		byte[] imageBytes = baos.toByteArray();

		return Base64.getEncoder().encodeToString(imageBytes);
	}

}
