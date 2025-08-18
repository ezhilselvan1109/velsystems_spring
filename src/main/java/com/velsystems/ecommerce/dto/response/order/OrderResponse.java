package com.velsystems.ecommerce.dto.response.order;

import com.velsystems.ecommerce.dto.response.order.OrderItemResponse;
import com.velsystems.ecommerce.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderResponse {
    private UUID id;
    private String orderNumber;
    private OrderStatus status;
    private BigDecimal subtotal;
    private BigDecimal discountTotal;
    private BigDecimal taxTotal;
    private BigDecimal shippingTotal;
    private BigDecimal grandTotal;
    private String couponCode;
    private LocalDateTime placedAt;
    private List<OrderItemResponse> items;
}
