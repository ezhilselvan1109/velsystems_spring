package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.cart.CartItemRequest;
import com.velsystems.ecommerce.dto.response.cart.CartResponse;
import com.velsystems.ecommerce.response.api.ApiResponse;
import com.velsystems.ecommerce.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart Management", description = "APIs for managing shopping carts")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Get cart", description = "Fetch the current user's cart")
    public ApiResponse<CartResponse> getCart() {
        return ApiResponse.success("Cart fetched successfully", cartService.getCart());
    }

    @PostMapping("/items")
    @Operation(summary = "Add item", description = "Add an item to the cart")
    public ApiResponse<CartResponse> addItem(@RequestBody CartItemRequest request) {
        return ApiResponse.success("Item added to cart", cartService.addItem(request));
    }

    @PutMapping("/items/{itemId}")
    @Operation(summary = "Update item quantity", description = "Update the quantity of an existing cart item")
    public ApiResponse<CartResponse> updateItem(@PathVariable UUID itemId, @RequestParam Integer quantity) {
        return ApiResponse.success("Cart item updated", cartService.updateItem(itemId, quantity));
    }

    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "Remove item", description = "Remove a specific item from the cart")
    public ApiResponse<CartResponse> removeItem(@PathVariable UUID itemId) {
        return ApiResponse.success("Cart item removed", cartService.removeItem(itemId));
    }

    @DeleteMapping("/items")
    @Operation(summary = "Clear cart", description = "Remove all items from the cart")
    public ApiResponse<Void> clearCart() {
        cartService.clearCart();
        return ApiResponse.success("Cart cleared", null);
    }
}
