package com.dealership.api.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Query parameters for GET /api/inventory.
 * All fields are optional — null means "no filter".
 */
@Data
public class CarFilterRequest {
    private String  make;
    private String  model;
    private Integer year;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer maxMileage;
    private String  condition;
    private String  status;
    private String  search;      // free-text search on make/model/description
    private int     page = 0;
    private int     size = 12;
    private String  sort = "createdAt,desc";
}
