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

        if (request.getAboutBlurb() != null && !request.getAboutBlurb().isBlank()
         || request.getAboutMission() != null && !request.getAboutMission().isBlank()
         || request.getAboutYearFounded() != null && !request.getAboutYearFounded().isBlank()) {
            body.append("ABOUT US CONTENT:\n");
            body.append("-----------------\n");
            if (request.getAboutYearFounded() != null && !request.getAboutYearFounded().isBlank())
                body.append("Year Founded:   ").append(request.getAboutYearFounded()).append("\n");
            if (request.getAboutBlurb() != null && !request.getAboutBlurb().isBlank())
                body.append("Story:\n").append(request.getAboutBlurb()).append("\n");
            if (request.getAboutMission() != null && !request.getAboutMission().isBlank())
                body.append("Mission:        ").append(request.getAboutMission()).append("\n");
            // Stats
            boolean hasStats = (request.getAboutStatYears() != null && !request.getAboutStatYears().isBlank())
                             || (request.getAboutStatVehicles() != null && !request.getAboutStatVehicles().isBlank())
                             || (request.getAboutStatReviews() != null && !request.getAboutStatReviews().isBlank())
                             || (request.getAboutStatTeam() != null && !request.getAboutStatTeam().isBlank());
            if (hasStats) {
                body.append("Stats:          ");
                if (request.getAboutStatYears() != null && !request.getAboutStatYears().isBlank())
                    body.append("Years=").append(request.getAboutStatYears()).append("  ");
                if (request.getAboutStatVehicles() != null && !request.getAboutStatVehicles().isBlank())
                    body.append("Sold=").append(request.getAboutStatVehicles()).append("  ");
                if (request.getAboutStatReviews() != null && !request.getAboutStatReviews().isBlank())
                    body.append("Reviews=").append(request.getAboutStatReviews()).append("  ");
                if (request.getAboutStatTeam() != null && !request.getAboutStatTeam().isBlank())
                    body.append("Team=").append(request.getAboutStatTeam());
                body.append("\n");
            }
            body.append("\n");
        }

        body.append("DOMAIN / WEBSITE:\n");
        body.append("-----------------\n");
        body.append(request.getDomain() != null && !request.getDomain().isBlank() ? request.getDomain() : "(not specified)").append("\n\n");

        if (request.getPages() != null && !request.getPages().isEmpty()) {
            body.append("OPTIONAL PAGES REQUESTED:\n");
            body.append("-------------------------\n");
            for (String page : request.getPages()) {
                body.append("  • ").append(page).append("\n");
            }
            body.append("\n");
        }

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
     * Send a financing application email to the dealer.
     */
    public void sendFinancingApplicationEmail(String toEmail, String name, String email,
                                              String phone, String employment,
                                              String monthlyIncome, String notes) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setReplyTo(email);
            message.setSubject("💰 New Financing Application: " + name);

            String body = String.format(
                "FINANCING APPLICATION\n\n" +
                "Name:              %s\n" +
                "Email:             %s\n" +
                "Phone:             %s\n" +
                "Employment Status: %s\n" +
                "Monthly Income:    %s\n\n" +
                "Notes:\n%s",
                name, email, phone != null ? phone : "(not provided)",
                employment != null ? employment : "(not provided)",
                monthlyIncome != null ? monthlyIncome : "(not provided)",
                notes != null && !notes.isBlank() ? notes : "(none)"
            );
            message.setText(body);

            mailSender.send(message);
            logger.info("Financing application email sent for {}", email);
        } catch (Exception e) {
            logger.error("Failed to send financing application email", e);
        }
    }

    /**
     * Send a service appointment request email to the dealer.
     */
    public void sendServiceAppointmentEmail(String toEmail, Map<String, String> fields) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            String replyTo = fields.get("email");
            if (replyTo != null && !replyTo.isBlank()) message.setReplyTo(replyTo);
            message.setSubject("🔧 New Service Appointment Request: " + fields.getOrDefault("name", "Unknown"));

            StringBuilder body = new StringBuilder("SERVICE APPOINTMENT REQUEST\n\n");
            body.append("CUSTOMER\n--------\n");
            body.append("Name:    ").append(fields.getOrDefault("name", "")).append("\n");
            body.append("Email:   ").append(fields.getOrDefault("email", "")).append("\n");
            body.append("Phone:   ").append(fields.getOrDefault("phone", "(not provided)")).append("\n\n");
            body.append("SERVICE\n-------\n");
            body.append("Type:    ").append(fields.getOrDefault("serviceType", "")).append("\n");
            if (fields.containsKey("description") && !fields.get("description").isBlank())
                body.append("Notes:   ").append(fields.get("description")).append("\n");
            body.append("\n");
            if (fields.containsKey("vehicleYear") || fields.containsKey("vehicleMake")) {
                body.append("VEHICLE\n-------\n");
                body.append("Year:    ").append(fields.getOrDefault("vehicleYear", "")).append("\n");
                body.append("Make:    ").append(fields.getOrDefault("vehicleMake", "")).append("\n");
                body.append("Model:   ").append(fields.getOrDefault("vehicleModel", "")).append("\n");
                if (fields.containsKey("vehicleMileage") && !fields.get("vehicleMileage").isBlank())
                    body.append("Mileage: ").append(fields.get("vehicleMileage")).append("\n");
                if (fields.containsKey("vehicleVin") && !fields.get("vehicleVin").isBlank())
                    body.append("VIN:     ").append(fields.get("vehicleVin")).append("\n");
                body.append("\n");
            }
            if (fields.containsKey("preferredDate") || fields.containsKey("preferredTime")) {
                body.append("APPOINTMENT PREFERENCE\n----------------------\n");
                if (fields.containsKey("preferredDate") && !fields.get("preferredDate").isBlank())
                    body.append("Date:    ").append(fields.get("preferredDate")).append("\n");
                if (fields.containsKey("preferredTime") && !fields.get("preferredTime").isBlank())
                    body.append("Time:    ").append(fields.get("preferredTime")).append("\n");
                body.append("\n");
            }
            if (fields.containsKey("referralSource") && !fields.get("referralSource").isBlank())
                body.append("How they heard about us: ").append(fields.get("referralSource")).append("\n");

            message.setText(body.toString());
            mailSender.send(message);
            logger.info("Service appointment email sent for {}", fields.get("email"));
        } catch (Exception e) {
            logger.error("Failed to send service appointment email", e);
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
