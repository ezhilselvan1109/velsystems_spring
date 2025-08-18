
package com.velsystems.ecommerce.model.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "product_variant_options")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVariantOption {
    @Id
    @GeneratedValue
    private UUID id;

    private String value; // Example: M, Red

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ProductOption option;
}
