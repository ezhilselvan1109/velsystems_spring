package com.velsystems.ecommerce.service.order;

import com.velsystems.ecommerce.dto.request.order.OrderRequest;
import com.velsystems.ecommerce.dto.response.order.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request);
    OrderResponse getOrder(String orderNumber);
    void cancelOrder(String orderNumber);
}
