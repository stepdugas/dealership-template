package com.dealership.api.controller;

import com.dealership.api.dto.LoginRequest;
import com.dealership.api.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * POST /api/admin/login — returns a JWT on valid credentials.
 *
 * Credentials are set via environment variables:
 *   ADMIN_USERNAME  (default: admin)
 *   ADMIN_PASSWORD  (default: changeme123)
 *
 * IMPORTANT: Change the default password before deploying.
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${dealership.admin.username}")
    private String adminUsername;

    @Value("${dealership.admin.password}")
    private String adminPassword;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        if (!adminUsername.equals(req.getUsername()) || !adminPassword.equals(req.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid credentials"));
        }
        String token = jwtTokenProvider.generateToken(req.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
