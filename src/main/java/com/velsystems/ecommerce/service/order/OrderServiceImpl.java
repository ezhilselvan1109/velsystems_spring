package com.velsystems.ecommerce.service.order;

import com.velsystems.ecommerce.dto.request.order.OrderItemRequest;
import com.velsystems.ecommerce.dto.request.order.OrderRequest;
import com.velsystems.ecommerce.dto.response.order.OrderResponse;
import com.velsystems.ecommerce.enums.CouponType;
import com.velsystems.ecommerce.enums.OrderStatus;
import com.velsystems.ecommerce.model.*;
import com.velsystems.ecommerce.model.product.Product;
import com.velsystems.ecommerce.model.product.ProductVariant;
import com.velsystems.ecommerce.repository.*;
import com.velsystems.ecommerce.repository.order.OrderItemRepository;
import com.velsystems.ecommerce.repository.order.OrderRepository;
import com.velsystems.ecommerce.repository.product.ProductRepository;
import com.velsystems.ecommerce.repository.product.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
    private final CouponRepository couponRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper mapper;

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address billing = addressRepository.findById(request.getBillingAddressId())
                .orElseThrow(() -> new RuntimeException("Billing address not found"));

        Address shipping = addressRepository.findById(request.getShippingAddressId())
                .orElseThrow(() -> new RuntimeException("Shipping address not found"));

        // Build order
        Order order = new Order();
        order.setUser(user);
        order.setBillingAddress(billing);
        order.setShippingAddress(shipping);
        order.setStatus(OrderStatus.PENDING);

        // Generate order number
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        order.setOrderNumber("ORD-" + date + "-" + random);

        BigDecimal subtotal = BigDecimal.ZERO;

        Set<OrderItem> items = new HashSet<>();
        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            ProductVariant variant = variantRepository.findById(itemReq.getVariantId())
                    .orElseThrow(() -> new RuntimeException("Variant not found"));

            BigDecimal unitPrice = variant.getPrice();
            BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            subtotal = subtotal.add(totalPrice);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setVariant(variant);
            orderItem.setTitle(product.getName());
            /*orderItem.setVariantTitle(String.join(",",
                    variant.getOptions().stream().map(o -> o.getValue()).toList()));*/
            orderItem.setSku(variant.getSku());
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setUnitPrice(unitPrice);
            orderItem.setTotalPrice(totalPrice);

            items.add(orderItem);
        }

        order.setItems(items);
        order.setSubtotal(subtotal);

        // Coupon logic
        BigDecimal discount = BigDecimal.ZERO;
        if (request.getCouponId() != null) {
            Coupon coupon = couponRepository.findByIdAndActiveTrue(request.getCouponId())
                    .orElseThrow(() -> new RuntimeException("Invalid coupon"));

            if (coupon.getStartsAt() != null && coupon.getStartsAt().isAfter(java.time.LocalDateTime.now()))
                throw new RuntimeException("Coupon not started");
            if (coupon.getEndsAt() != null && coupon.getEndsAt().isBefore(java.time.LocalDateTime.now()))
                throw new RuntimeException("Coupon expired");
            if (coupon.getMaxUses() != null && coupon.getUsedCount() >= coupon.getMaxUses())
                throw new RuntimeException("Coupon usage limit reached");

            if (coupon.getType() == CouponType.PERCENTAGE) {
                discount = subtotal.multiply(coupon.getValue().divide(BigDecimal.valueOf(100)));
            } else {
                discount = coupon.getValue();
            }

            coupon.setUsedCount(coupon.getUsedCount() + 1);
            order.setCouponCode(coupon.getCode());
            order.setDiscountTotal(discount);
        }

        order.setTaxTotal(BigDecimal.ZERO); // later add GST/VAT calculation
        order.setShippingTotal(BigDecimal.ZERO); // integrate shipping rates
        order.setGrandTotal(subtotal.subtract(discount));

        orderRepository.save(order);
        return mapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrder(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapper.map(order, OrderResponse.class);
    }

    @Override
    public void cancelOrder(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.CANCELLED);
    }
}
