package com.dealership.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/** Request body for POST /api/admin/login. */
@Data
public class LoginRequest {
    @NotBlank private String username;
    @NotBlank private String password;
}
