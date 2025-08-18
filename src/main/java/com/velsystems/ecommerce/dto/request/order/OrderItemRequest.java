package com.velsystems.ecommerce.dto.request.order;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItemRequest {
    private UUID productId;
    private UUID variantId;
    private Integer quantity;
}
