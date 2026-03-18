package com.dealership.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request body for POST /api/contact.
 */
@Data
public class ContactRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    private String email;

    private String phone;

    @NotBlank(message = "Message is required")
    private String message;

    /** Optional — if the inquiry is about a specific car. */
    private Long carId;
}
