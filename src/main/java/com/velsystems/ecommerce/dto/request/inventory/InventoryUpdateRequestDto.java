package com.velsystems.ecommerce.dto.request.inventory;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InventoryUpdateRequestDto {
    private Integer inStock;
    private Integer reserved;
    private Integer lowStockThreshold;
}