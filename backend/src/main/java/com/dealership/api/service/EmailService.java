package com.dealership.api.service;

import com.dealership.api.dto.ClientIntakeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Email service for sending transactional emails.
 * Currently supports Gmail SMTP.
 */
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${dealership.email.from}")
    private String fromEmail;

    @Value("${dealership.email.admin-to}")
    private String adminEmail;

    @Value("${dealership.email.reply-to}")
    private String replyToEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send client intake form submission to admin email.
     * Formats all the customer info into a readable email.
     */
    public void sendClientIntakeSubmission(ClientIntakeRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(adminEmail);
            message.setReplyTo(replyToEmail);
            message.setSubject("🎯 NEW CLIENT INTAKE: " + request.getBusinessName());

            // Build the email body
            String emailBody = buildClientIntakeEmailBody(request);
            message.setText(emailBody);

            mailSender.send(message);
            logger.info("Client intake email sent to {} for business: {}", adminEmail, request.getBusinessName());
        } catch (Exception e) {
            logger.error("Failed to send client intake email", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * Format the client intake request as a readable email body.
     */
    private String buildClientIntakeEmailBody(ClientIntakeRequest request) {
        StringBuilder body = new StringBuilder();

        body.append("========================================\n");
        body.append("NEW CLIENT INTAKE FORM SUBMISSION\n");
        body.append("========================================\n\n");

        body.append("BUSINESS INFORMATION:\n");
        body.append("---------------------\n");
        body.append("Business Name:  ").append(request.getBusinessName()).append("\n");
        body.append("Tagline:        ").append(request.getTagline() != null ? request.getTagline() : "(not provided)").append("\n");
        body.append("Phone:          ").append(request.getPhone()).append("\n");
        body.append("Email:          ").append(request.getEmail()).append("\n\n");

        body.append("LOCATION:\n");
        body.append("--------\n");
        body.append("Address:        ").append(request.getAddress()).append("\n");
        body.append("City:           ").append(request.getCity()).append("\n");
        body.append("State:          ").append(request.getState()).append("\n");
        body.append("ZIP:            ").append(request.getZip()).append("\n\n");

        body.append("BUSINESS HOURS:\n");
        body.append("---------------\n");
        if (request.getHours() != null && !request.getHours().isEmpty()) {
            for (String day : new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}) {
                String hours = request.getHours().get(day);
                if (hours != null && !hours.isEmpty()) {
                    body.append(String.format("%-12s %s\n", day + ":", hours));
                } else {
                    body.append(String.format("%-12s Closed\n", day + ":"));
                }
            }
        }
        body.append("\n");

        body.append("BRANDING:\n");
        body.append("---------\n");
        body.append("Primary Color:  ").append(request.getPrimaryColor() != null ? request.getPrimaryColor() : "(not specified)").append("\n\n");

        body.append("SOCIAL MEDIA (Optional):\n");
        body.append("------------------------\n");
        body.append("Facebook:       ").append(request.getFacebook() != null && !request.getFacebook().isEmpty() ? request.getFacebook() : "(not provided)").append("\n");
        body.append("Instagram:      ").append(request.getInstagram() != null && !request.getInstagram().isEmpty() ? request.getInstagram() : "(not provided)").append("\n\n");

        body.append("ADDITIONAL NOTES:\n");
        body.append("-----------------\n");
        body.append(request.getNotes() != null && !request.getNotes().isEmpty() ? request.getNotes() : "(none)").append("\n\n");

        body.append("========================================\n");
        body.append("Next Steps:\n");
        body.append("1. Review this information\n");
        body.append("2. Reach out to customer if clarifications needed\n");
        body.append("3. Begin customization process\n");
        body.append("4. Deploy to production\n");
        body.append("========================================\n");

        return body.toString();
    }

    /**
     * Send a simple contact form email (for vehicle inquiries).
     */
    public void sendContactFormEmail(String name, String email, String phone, String message, String vehicleInfo) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(adminEmail);
            mailMessage.setReplyTo(email);
            mailMessage.setSubject("📧 New Vehicle Inquiry: " + vehicleInfo);

            String body = String.format(
                    "Name: %s\nEmail: %s\nPhone: %s\n\nVehicle: %s\n\nMessage:\n%s",
                    name, email, phone, vehicleInfo, message
            );
            mailMessage.setText(body);

            mailSender.send(mailMessage);
            logger.info("Contact form email sent for inquiry from {}", email);
        } catch (Exception e) {
            logger.error("Failed to send contact form email", e);
            // Don't throw — log and continue
        }
    }

    /**
     * Send a test email (for OpenClaw configuration testing).
     * Simple email with custom to/subject/body.
     */
    public void sendTestEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setReplyTo(replyToEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            logger.info("Test email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send test email to {}", to, e);
            throw new RuntimeException("Failed to send test email: " + e.getMessage(), e);
        }
    }
}
