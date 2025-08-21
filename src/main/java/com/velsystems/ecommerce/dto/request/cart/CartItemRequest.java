package com.velsystems.ecommerce.dto.request.cart;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CartItemRequest {
    private UUID variantId;
    private Integer quantity;
}
