package com.dealership.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Maps to the existing {@code dealership_config} table.
 * Used by managers to update store hours, contact info, etc. at runtime.
 */
@Entity
@Table(name = "dealership_config")
@Getter
@Setter
@NoArgsConstructor
public class DealershipConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String key;

    @Column(columnDefinition = "TEXT")
    private String value;

    public DealershipConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
