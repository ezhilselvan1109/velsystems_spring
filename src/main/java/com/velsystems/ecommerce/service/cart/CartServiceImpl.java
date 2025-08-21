package com.velsystems.ecommerce.service.cart;

import com.velsystems.ecommerce.dto.request.cart.CartItemRequest;
import com.velsystems.ecommerce.dto.response.cart.CartItemResponse;
import com.velsystems.ecommerce.dto.response.cart.CartResponse;
import com.velsystems.ecommerce.model.User;
import com.velsystems.ecommerce.model.cart.Cart;
import com.velsystems.ecommerce.model.cart.CartItem;
import com.velsystems.ecommerce.model.product.ProductVariant;
import com.velsystems.ecommerce.repository.cart.CartItemRepository;
import com.velsystems.ecommerce.repository.cart.CartRepository;
import com.velsystems.ecommerce.repository.product.ProductVariantRepository;
import com.velsystems.ecommerce.security.Util;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository variantRepository;
    private final Util util;
    private final ModelMapper modelMapper;

    @Override
    public CartResponse getCart() {
        Cart cart = getOrCreateCart(util.getAuthenticatedUserId());
        return convertToResponse(cart);
    }

    @Override
    public CartResponse addItem(CartItemRequest request) {
        UUID userId = util.getAuthenticatedUserId();
        Cart cart = getOrCreateCart(userId);

        ProductVariant variant = variantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new EntityNotFoundException("Variant not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getVariant().getId().equals(request.getVariantId()))
                .findFirst()
                .orElse(CartItem.builder()
                        .cart(cart)
                        .variant(variant)
                        .quantity(0)
                        .price(BigDecimal.ZERO)
                        .build());

        item.setQuantity(item.getQuantity() + request.getQuantity());
        item.setPrice(variant.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        cart.getItems().add(item);

        cartRepository.save(cart);
        return convertToResponse(cart);
    }

    @Override
    public CartResponse updateItem(UUID itemId, Integer quantity) {
        Cart cart = getOrCreateCart(util.getAuthenticatedUserId());

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Item does not belong to user cart");
        }

        item.setQuantity(quantity);
        item.setPrice(item.getVariant().getPrice().multiply(BigDecimal.valueOf(quantity)));
        cartItemRepository.save(item);

        return convertToResponse(cart);
    }

    @Override
    public CartResponse removeItem(UUID itemId) {
        Cart cart = getOrCreateCart(util.getAuthenticatedUserId());

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Item does not belong to user cart");
        }

        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        return convertToResponse(cart);
    }

    @Override
    public void clearCart() {
        Cart cart = getOrCreateCart(util.getAuthenticatedUserId());
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private Cart getOrCreateCart(UUID userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .user(User.builder().id(userId).build())
                        .build()));
    }

    private CartResponse convertToResponse(Cart cart) {
        CartResponse response = modelMapper.map(cart, CartResponse.class);

        // map items manually since we want only specific fields
        List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> CartItemResponse.builder()
                        .id(item.getId())
                        .variantId(item.getVariant().getId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        response.setUserId(cart.getUser() != null ? cart.getUser().getId() : null);
        response.setItems(items);

        return response;
    }
}
