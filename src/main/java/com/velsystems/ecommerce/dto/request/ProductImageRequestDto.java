package com.velsystems.ecommerce.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageRequestDto {
    private String imageUrl;
    private Boolean isPrimary;
}