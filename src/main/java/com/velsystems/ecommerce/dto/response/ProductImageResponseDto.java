package com.velsystems.ecommerce.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageResponseDto {
    private UUID id;
    private String imageUrl;
    private Boolean isPrimary;
}