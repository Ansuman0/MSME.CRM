package utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import constants.FrameworkConstants;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailUtility {

    private static EmailUtility config;

    public MailDetails mail;

    // Inner class to represent mail details
    public static class MailDetails {
        public boolean enabled;  // Added field to enable/disable email sending
        public Smtp smtp;
        public String from;
        public String[] to;  // Array for multiple recipients
        public String subject;
        public String body;

        // SMTP details class
        public static class Smtp {
            public String host;
            public int port;
            public String username;
            public String password;
        }
    }

    // Static block to load the email configuration using Jackson
    static {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            config = objectMapper.readValue(new File(FrameworkConstants.getEmailfigfilepath()), EmailUtility.class);
            System.out.println(STR."Email enabled: \{config.mail.enabled}");  // Debugging: log if enabled is loaded
        } catch (IOException e) {
            System.err.println(STR."Error loading configuration: \{e.getMessage()}");
        }
    }

    // Method to send an email with a report attachment
    public static void sendReportEmail(String reportPath) {
        if (config != null && config.mail != null) {
            try {
                // Setup mail session dynamically based on the availability of username/password
                Session session = setupMailSession();
                MimeMessage message = createEmailMessage(session, reportPath);

                // Send the email
                Transport.send(message);
                System.out.println("Email sent successfully.");
            } catch (MessagingException e) {
                System.err.println(STR."Error while sending email: \{e.getMessage()}");
            }
        } else {
            System.err.println("Email sending is disabled or email configuration is not loaded.");
        }
    }

    // Set-up the email session dynamically
    private static Session setupMailSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", config.mail.smtp.host);
        properties.put("mail.smtp.port", String.valueOf(config.mail.smtp.port));
        properties.put("mail.smtp.starttls.enable", "true");  // Enable TLS by default

        // Check if the SMTP configuration contains a username and password
        if (config.mail.smtp.username != null && !config.mail.smtp.username.isEmpty() &&
                config.mail.smtp.password != null && !config.mail.smtp.password.isEmpty()) {
            // Use authentication if username and password are provided
            properties.put("mail.smtp.auth", "true");

            // Create session with authentication
            return Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(config.mail.smtp.username, config.mail.smtp.password);
                }
            });
        } else {
            // No authentication needed
            properties.put("mail.smtp.auth", "false");
            return Session.getInstance(properties);
        }
    }

    // Create the email message with a report attachment
    private static MimeMessage createEmailMessage(Session session, String reportPath) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(config.mail.from));

        // Add multiple recipients
        for (String recipient : config.mail.to) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        }

        message.setSubject(config.mail.subject);

        // Create the body part
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(config.mail.body);

        // Create the attachment part
        MimeBodyPart attachmentPart = new MimeBodyPart();
        FileDataSource source = new FileDataSource(reportPath);
        attachmentPart.setDataHandler(new DataHandler(source));
        attachmentPart.setFileName(new File(reportPath).getName());

        // Combine body and attachment parts into a multipart message
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);
        multipart.addBodyPart(attachmentPart);

        // Set the content for the message
        message.setContent(multipart);

        return message;
    }
}
