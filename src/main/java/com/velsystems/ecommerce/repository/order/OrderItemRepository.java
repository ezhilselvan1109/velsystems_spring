package com.velsystems.ecommerce.repository.order;

import com.velsystems.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    // Get all items by Order
    List<OrderItem> findByOrderId(UUID orderId);
}
