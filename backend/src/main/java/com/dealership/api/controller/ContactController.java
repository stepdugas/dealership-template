package com.dealership.api.controller;

import com.dealership.api.dto.ClientIntakeRequest;
import com.dealership.api.dto.ContactRequest;
import com.dealership.api.model.ContactSubmission;
import com.dealership.api.service.ContactService;
import com.dealership.api.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for contact form submissions and client intake.
 *
 * Public:  POST /api/contact                    (vehicle inquiry)
 * Public:  POST /api/client-intake              (client intake form)
 * Admin:   GET  /api/admin/contacts             (view submissions)
 */
@RestController
public class ContactController {

    private final ContactService contactService;
    private final EmailService emailService;

    // Constructor for dependency injection
    public ContactController(ContactService contactService, EmailService emailService) {
        this.contactService = contactService;
        this.emailService = emailService;
    }

    /**
     * POST /api/contact — submit a contact form (public, vehicle inquiry)
     */
    @PostMapping("/api/contact")
    public ResponseEntity<ContactSubmission> submit(@Valid @RequestBody ContactRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.save(req));
    }

    /**
     * POST /api/client-intake — submit client intake form (public)
     * Captures dealership info and emails it to admin
     */
    @PostMapping("/api/client-intake")
    public ResponseEntity<Map<String, String>> submitClientIntake(@Valid @RequestBody ClientIntakeRequest req) {
        try {
            // Send email to admin
            emailService.sendClientIntakeSubmission(req);

            // Return success response
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Thank you! We've received your information and will be in touch soon.");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to submit form. Please try again or contact us directly.");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/admin/contacts — list all submissions (admin only)
     */
    @GetMapping("/api/admin/contacts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactSubmission>> getAll() {
        return ResponseEntity.ok(contactService.findAll());
    }
}
