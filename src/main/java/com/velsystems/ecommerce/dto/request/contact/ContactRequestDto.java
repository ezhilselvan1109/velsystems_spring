package com.velsystems.ecommerce.dto.request.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactRequestDto {
    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String message;
}
