package com.dealership.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity that maps to the {@code cars} table.
 */
@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer year;

    private String trim;

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    private Integer mileage;

    @Column(unique = true, length = 17)
    private String vin;

    @Column(columnDefinition = "TEXT")
    private String description;

    /** JSON array of feature strings, e.g. ["Backup Camera","Sunroof"] */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> features = new ArrayList<>();

    /** "New", "Used", "Certified" */
    private String condition;

    /** "available", "pending", "sold" */
    @Column(nullable = false)
    private String status = "available";

    private String transmission;
    private String engine;
    private String driveTrain;
    private String exteriorColor;
    private String interiorColor;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("sortOrder ASC")
    private List<CarImage> images = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
