package com.velsystems.ecommerce.repository.product;

import com.velsystems.ecommerce.model.product.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {

    Page<ProductVariant> findByProductId(UUID productId, Pageable pageable);
}
