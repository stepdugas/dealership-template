package com.dealership.api.controller;

import com.dealership.api.dto.ContactRequest;
import com.dealership.api.model.ContactSubmission;
import com.dealership.api.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for contact form submissions.
 *
 * Public:  POST /api/contact
 * Admin:   GET  /api/admin/contacts
 */
@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    /**
     * POST /api/contact — submit a contact form (public)
     */
    @PostMapping("/api/contact")
    public ResponseEntity<ContactSubmission> submit(@Valid @RequestBody ContactRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.save(req));
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
