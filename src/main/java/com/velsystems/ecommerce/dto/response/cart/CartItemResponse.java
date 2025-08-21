package com.velsystems.ecommerce.dto.response.cart;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CartItemResponse {
    private UUID id;
    private UUID variantId;
    private Integer quantity;
    private BigDecimal price;
}
