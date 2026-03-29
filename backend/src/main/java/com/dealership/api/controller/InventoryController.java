package com.dealership.api.controller;

import com.dealership.api.dto.CarFilterRequest;
import com.dealership.api.model.Car;
import com.dealership.api.service.CarService;
import com.dealership.api.service.FacebookFeedGenerator;
import com.dealership.api.service.ValuationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class InventoryController {

    private final CarService carService;
    private final ValuationService valuationService;
    private final FacebookFeedGenerator facebookFeedGenerator;

    // Constructor for dependency injection
    public InventoryController(CarService carService, ValuationService valuationService,
                               FacebookFeedGenerator facebookFeedGenerator) {
        this.carService = carService;
        this.valuationService = valuationService;
        this.facebookFeedGenerator = facebookFeedGenerator;
    }

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

    /**
     * POST /api/inventory/{id}/refresh-kbb
     * Admin-only endpoint to manually refresh KBB valuation for a specific car.
     *
     * @param id The car ID
     * @return Updated car with new KBB value
     */
    @PostMapping("/{id}/refresh-kbb")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Car> refreshKBBValuation(@PathVariable Long id) {
        Car updatedCar = valuationService.refreshKBBValuation(id);
        return ResponseEntity.ok(updatedCar);
    }

    // ── Feed endpoints (Public) ────────────────────────────────────────

    /**
     * GET /api/feeds/facebook-marketplace.xml
     * Public endpoint returning Facebook Marketplace catalog XML feed.
     * Can be consumed by Meta's Catalog Manager for dynamic ads.
     *
     * @return XML feed as application/xml content
     */
    @GetMapping(path = "/feeds/facebook-marketplace.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getFacebookMarketplaceFeed() {
        String xmlFeed = facebookFeedGenerator.generateFeed();
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_XML)
            .header("Cache-Control", "public, max-age=3600")  // Cache for 1 hour
            .body(xmlFeed);
    }
}
