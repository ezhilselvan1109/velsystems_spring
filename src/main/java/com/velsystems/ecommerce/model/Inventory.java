package com.velsystems.ecommerce.model;

import com.velsystems.ecommerce.model.product.ProductVariant;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inventory")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Inventory {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false, unique = true)
    private ProductVariant variant;

    @Column(nullable = false)
    private Integer inStock = 0;

    @Column(nullable = false)
    private Integer reserved = 0;

    @Column(nullable = false)
    private Integer sold = 0;

    @Column(nullable = false)
    private Integer lowStockThreshold = 5;


    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
