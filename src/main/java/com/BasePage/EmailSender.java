package com.BasePage; // Or a new package like com.utils

import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailSender {

    public static void sendEmailWithAttachment(String reportPath) {
        // --- IMPORTANT: DO NOT HARDCODE CREDENTIALS IN A REAL PROJECT ---
        // Use environment variables, a config file, or a secure vault.
        final String fromEmail = "tejasnarkhede@kiya.ai"; // The "from" email address (must be a real account)
        final String password = "kiya@1997";   // Your email password or an "App Password"
        final String toEmail = "tejasnarkhede@kiya.ai"; // The "to" email address

        System.out.println("Initializing email sending process");

        // Setup properties for the SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com"); // Outlook/Office 365 SMTP host
        props.put("mail.smtp.port", "587");                // TLS Port
        props.put("mail.smtp.auth", "true");               // Enable authentication
        props.put("mail.smtp.starttls.enable", "true");    // Enable STARTTLS

        // Create an Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        // Create a Session
        Session session = Session.getInstance(props, auth);

        try {
            // Create the email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Automation Test Execution Report");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Part 1: The email body
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Automation Team");

            // Part 2: The attachment
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(reportPath);

            // Add parts to the multipart
            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            // Set the complete message parts
            message.setContent(multipart);

            // Send the email
            System.out.println("Sending email...");
            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email. Error: " + e.getMessage());
        }
    }
}