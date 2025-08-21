package com.velsystems.ecommerce.repository.cart;

import com.velsystems.ecommerce.model.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
}
