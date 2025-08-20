package com.velsystems.ecommerce.dto.response;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InventoryResponseDto {
    private UUID id;
    private UUID variantId;
    private Integer inStock;
    private Integer reserved;
    private Integer sold;
    private Integer lowStockThreshold;
    private String status; // Normal / LowStock / OutOfStock
}
