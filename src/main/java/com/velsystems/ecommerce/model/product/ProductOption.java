package com.velsystems.ecommerce.model.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product_options")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductOption {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name; // Example: "Color", "Storage"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ProductOptionValue> values = new HashSet<>();
}