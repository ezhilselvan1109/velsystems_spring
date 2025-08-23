package com.velsystems.ecommerce.dto.response.contact;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ContactResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String message;
    private String status;
    private LocalDateTime createdAt;
}
