package com.dealership.api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utility for generating and validating JWT tokens.
 *
 * The secret is configured via {@code dealership.jwt.secret} in
 * application.properties (override with JWT_SECRET env var).
 */
@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long expirationMs;

    public JwtTokenProvider(
        @Value("${dealership.jwt.secret}") String secret,
        @Value("${dealership.jwt.expiration-ms}") long expirationMs
    ) {
        // Ensure the key is at least 256 bits for HS256
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /** Generate a signed JWT for the given username. */
    public String generateToken(String username) {
        Date now = new Date();
        return Jwts.builder()
            .subject(username)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + expirationMs))
            .signWith(key)
            .compact();
    }

    /** Extract the username (subject) from a token. */
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token).getPayload().getSubject();
    }

    /** Return true if the token is well-formed, signed, and not expired. */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
