package com.velsystems.ecommerce.dto.request;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InventoryRequest {
    private UUID variantId;
    private Integer inStock;  // new stock value (if updating directly)
    private Integer reserved; // optional
    private Integer sold;     // optional
    private Integer lowStockThreshold;
}
