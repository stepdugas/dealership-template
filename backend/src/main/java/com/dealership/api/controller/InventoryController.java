package com.dealership.api.controller;

import com.dealership.api.dto.CarFilterRequest;
import com.dealership.api.model.Car;
import com.dealership.api.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for vehicle inventory.
 *
 * Public:  GET /api/inventory, GET /api/inventory/{id}
 * Admin:   POST, PUT, DELETE (require JWT)
 */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final CarService carService;

    // ── Public endpoints ─────────────────────────────────────────────

    /**
     * GET /api/inventory
     * Supports query params: make, model, year, minPrice, maxPrice,
     *                        maxMileage, condition, search, page, size, sort
     */
    @GetMapping
    public ResponseEntity<Page<Car>> getInventory(CarFilterRequest filter) {
        return ResponseEntity.ok(carService.findAll(filter));
    }

    /**
     * GET /api/inventory/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable Long id) {
        return carService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // ── Admin-only endpoints ─────────────────────────────────────────

    /**
     * POST /api/inventory  — create a new vehicle
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Car> createCar(@Valid @RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.create(car));
    }

    /**
     * PUT /api/inventory/{id}  — update an existing vehicle
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @Valid @RequestBody Car car) {
        return ResponseEntity.ok(carService.update(id, car));
    }

    /**
     * DELETE /api/inventory/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
