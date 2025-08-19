package com.velsystems.ecommerce.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionValueResponse {
    private UUID id;
    private String value;
}
