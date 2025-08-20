package com.velsystems.ecommerce.dto.request.inventory;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InventoryCreateRequestDto {
    private UUID variantId;
    private Integer inStock;
    private Integer reserved;
    private Integer lowStockThreshold;
}