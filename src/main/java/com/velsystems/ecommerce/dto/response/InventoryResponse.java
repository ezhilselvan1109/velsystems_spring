package com.velsystems.ecommerce.dto.response;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InventoryResponse {
    private UUID variantId;
    private Integer inStock;
    private Integer reserved;
    private Integer sold;
    private Integer lowStockThreshold;
    private boolean lowStock;  // derived
}
