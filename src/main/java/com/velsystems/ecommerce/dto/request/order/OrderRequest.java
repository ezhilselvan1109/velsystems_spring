package com.velsystems.ecommerce.dto.request.order;

import com.velsystems.ecommerce.dto.request.order.OrderItemRequest;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderRequest {
    private UUID userId;
    private UUID billingAddressId;
    private UUID shippingAddressId;
    private UUID couponId;              // optional
    private List<OrderItemRequest> items;
}
