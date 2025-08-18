
package com.velsystems.ecommerce.model.product;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product_variants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVariant {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String sku;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductVariantOption> options = new HashSet<>();

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> images = new HashSet<>(); // 👈 Variant-specific images
}