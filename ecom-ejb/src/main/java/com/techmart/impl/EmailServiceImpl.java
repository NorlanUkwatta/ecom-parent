package com.techmart.impl;

import com.techmart.service.EmailService;
import jakarta.ejb.Stateless;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

@Stateless
public class EmailServiceImpl implements EmailService {

    private final String SMTP_EMAIL = "norlanukwatta363@gmail.com";
    private final String SMTP_PASSWORD = "lurvzppubkncemwy";

    @Override
    public void sendOrderConfirmation(String toEmail, String customerName, Long orderId) {
        System.out.println(">>> Preparing to send confirmation email to: " + toEmail);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Required for Gmail security

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_EMAIL, SMTP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_EMAIL, "TechMart Official"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Order Confirmation - TechMart (Order #" + orderId + ")");

            String htmlContent = "<h3>Hi " + customerName + ",</h3>"
                    + "<p>Thank you for shopping with TechMart!</p>"
                    + "<p>Your payment for <b>Order #" + orderId + "</b> was successful and we are currently processing it.</p>"
                    + "<br><p>Best regards,<br>The TechMart Team</p>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            // Send the Email
            Transport.send(message);
            System.out.println(">>> Email successfully sent to " + toEmail);

        } catch (Exception e) {
            System.err.println(">>> Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
