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
	public static final Map<String, String> businessPartnerDetails = new HashMap<>();

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
		String gstIn = generateGstin();
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
		businessPartnerDetails.put("companyLogo", companyLogo);
		businessPartnerDetails.put("companyName", companyName);
		businessPartnerDetails.put("companyType", companyType);
		businessPartnerDetails.put("parentCompany", parentCompany);
		businessPartnerDetails.put("cinIip", cinIip);
		businessPartnerDetails.put("gstIn", gstIn);
		businessPartnerDetails.put("panNumber", panNumber);
		businessPartnerDetails.put("phoneNumber", phoneNumber);
		businessPartnerDetails.put("faxNumber", faxNumber);
		businessPartnerDetails.put("email", email);
		businessPartnerDetails.put("website", website);
		businessPartnerDetails.put("gstType", gstType);
		businessPartnerDetails.put("openingBalance", openingBalance);
		businessPartnerDetails.put("accountOwner", accountOwner);
		businessPartnerDetails.put("industry", industry);
		businessPartnerDetails.put("priority", priority);
		businessPartnerDetails.put("source", source);
		businessPartnerDetails.put("corporationDate", corporationDate);
		businessPartnerDetails.put("corporationCountry", corporationCountry);
		businessPartnerDetails.put("location", location);
		businessPartnerDetails.put("description", description);

		businessPartnerDetails.put("invAddressline1", invAddressline1);
		businessPartnerDetails.put("invAddressline2", invAddressline2);
		businessPartnerDetails.put("invState", invState);
		businessPartnerDetails.put("invContactName", invContactName);
		businessPartnerDetails.put("invPhone", invPhone);
		businessPartnerDetails.put("invEmail", invEmail);

		businessPartnerDetails.put("delAddressline1", delAddressline1);
		businessPartnerDetails.put("delAddressline2", delAddressline2);
		businessPartnerDetails.put("delState", delState);
		businessPartnerDetails.put("delContactName", delContactName);
		businessPartnerDetails.put("delPhone", delPhone);
		businessPartnerDetails.put("delEmail", delEmail);

		businessPartnerDetails.put("otherAddressline1", otherAddressline1);
		businessPartnerDetails.put("otherAddressline2", otherAddressline2);
		businessPartnerDetails.put("otherState", otherState);
		businessPartnerDetails.put("otherContactName", otherContactName);
		businessPartnerDetails.put("otherPhone", otherPhone);
		businessPartnerDetails.put("otherEmail", otherEmail);

		businessPartnerDetails.put("contactInfoTitle", contactInfoTitle);
		businessPartnerDetails.put("contactInfoName", contactInfoName);
		businessPartnerDetails.put("contactInfoDepartment", contactInfoDepartment);
		businessPartnerDetails.put("contactInfoDesignation", contactInfoDesignation);
		businessPartnerDetails.put("contactInfoEmail", contactInfoEmail);
		businessPartnerDetails.put("contactInfoPhoneNumber", contactInfoPhoneNumber);
		businessPartnerDetails.put("contactInfoAlternativePhoneNumber", contactInfoAlternativePhoneNumber);
		businessPartnerDetails.put("contactInfoDescription", contactInfoDescription);
	}

	public static String generatePanNumber() {
		final Faker FAKER = new Faker();
		StringBuilder panNumber = new StringBuilder();

		// Generate first 3 alphabetic characters (A-Z)
		for (int i = 0; i < 3; i++) {
			panNumber.append((char) ('A' + FAKER.random().nextInt(26)));
		}

		// The fourth character based on the type of assesses
		char[] assesseeTypes = { 'C', 'P', 'H', 'F', 'A', 'T', 'B', 'L', 'J', 'G' };
		panNumber.append(assesseeTypes[FAKER.random().nextInt(assesseeTypes.length)]);

		// The fifth character, randomly chosen (A-Z)
		panNumber.append((char) ('A' + FAKER.random().nextInt(26)));

		// Generate next 4 numeric characters (0-9)
		for (int i = 0; i < 4; i++) {
			panNumber.append(FAKER.random().nextInt(10));
		}

		// Generate last alphabetic character (A-Z)
		panNumber.append((char) ('A' + FAKER.random().nextInt(26)));

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

	public static String generateGstin() {
		final Faker FAKER = new Faker();
		String[] stateCodes = {
				"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
				"31", "32", "33", "34", "35", "36", "37"
		};

		// Select a random state code
		String stateCode = stateCodes[FAKER.random().nextInt(stateCodes.length)];

		String pan = generatePanNumber(); // Generate a 10-character PAN number
		String entityCode = "1"; // Hardcoded entity code
		String blankChar = "Z";  // Default blank character for GSTIN
		String checkDigit = FAKER.regexify("[A-Z0-9]"); // Generate a check digit (alphanumeric)

		// Construct GSTIN: State Code (2) + PAN (10) + Entity Code (1) + Blank (1) + Check Digit (1) = 15 characters
		return stateCode + pan + entityCode + blankChar + checkDigit;
	}
}
