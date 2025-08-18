package com.velsystems.ecommerce.model;

import com.velsystems.ecommerce.model.product.Product;
import com.velsystems.ecommerce.model.product.ProductVariant;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Links (for reference)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;

    // Snapshots
    private String title;        // "iPhone 15"
    private String variantTitle; // "128GB Black"
    private String sku;

    // Pricing
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
