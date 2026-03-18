package com.dealership.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity for the {@code car_images} table.
 * Each row is one photo for a car (Cloudinary URL).
 */
@Entity
@Table(name = "car_images")
@Data
@NoArgsConstructor
public class CarImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Back-reference to the parent car — excluded from JSON to avoid cycles. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    @JsonIgnore
    private Car car;

    @Column(nullable = false)
    private String imageUrl;

    private boolean isPrimary = false;

    private int sortOrder = 0;
}
