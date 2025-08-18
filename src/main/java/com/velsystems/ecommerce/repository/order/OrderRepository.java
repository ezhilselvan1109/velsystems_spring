package com.velsystems.ecommerce.repository.order;

import com.velsystems.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    // Find by order number (customer support / tracking purpose)
    Optional<Order> findByOrderNumber(String orderNumber);

    // Check if order number already exists
    boolean existsByOrderNumber(String orderNumber);
}
