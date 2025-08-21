package com.velsystems.ecommerce.service.cart;

import com.velsystems.ecommerce.dto.request.cart.CartItemRequest;
import com.velsystems.ecommerce.dto.response.cart.CartResponse;

import java.util.UUID;

public interface CartService {
    CartResponse getCart();
    CartResponse addItem(CartItemRequest request);
    CartResponse updateItem(UUID itemId, Integer quantity);
    CartResponse removeItem(UUID itemId);
    void clearCart();
}
