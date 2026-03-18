package com.dealership.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * JPA entity for the {@code contact_submissions} table.
 */
@Entity
@Table(name = "contact_submissions")
@Data
@NoArgsConstructor
public class ContactSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String message;

    /** Nullable — set when the inquiry is about a specific car. */
    private Long carId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
