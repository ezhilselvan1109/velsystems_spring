package com.velsystems.ecommerce.model.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "product_option_values")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductOptionValue {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String value; // Example: "Black", "256GB"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private ProductOption option;
}
