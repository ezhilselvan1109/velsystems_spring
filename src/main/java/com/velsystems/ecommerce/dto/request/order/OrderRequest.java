package com.velsystems.ecommerce.dto.request.order;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderRequest {
    private UUID accountId;
    private UUID billingAddressId;
    private UUID shippingAddressId;
    private UUID couponId;
    private List<OrderItemRequest> items;
}
