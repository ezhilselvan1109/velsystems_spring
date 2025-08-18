package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.order.OrderRequest;
import com.velsystems.ecommerce.dto.response.order.OrderResponse;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order management APIs")
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Place a new order",
            description = "Creates a new order with items and customer details"
    )
    @PostMapping
    public ResponseEntity<ApiResponse> placeOrder(
            @RequestBody OrderRequest request) {
        OrderResponse response = orderService.placeOrder(request);
        return ResponseEntity.ok(new ApiResponse("Order placed successfully", response));
    }

    @Operation(
            summary = "Get order by order number",
            description = "Fetch an order with all items using its unique order number"
    )
    @GetMapping("/{orderNumber}")
    public ResponseEntity<ApiResponse> getOrder(
            @Parameter(description = "Unique order number", example = "ORD-20230818123456")
            @PathVariable String orderNumber) {
        OrderResponse response = orderService.getOrder(orderNumber);
        return ResponseEntity.ok(new ApiResponse("Order fetched", response));
    }

    @Operation(
            summary = "Cancel an order",
            description = "Cancel an existing order if not shipped/delivered"
    )
    @PostMapping("/{orderNumber}/cancel")
    public ResponseEntity<ApiResponse> cancelOrder(
            @Parameter(description = "Unique order number", example = "ORD-20230818123456")
            @PathVariable String orderNumber) {
        orderService.cancelOrder(orderNumber);
        return ResponseEntity.ok(new ApiResponse("Order cancelled", null));
    }
}
