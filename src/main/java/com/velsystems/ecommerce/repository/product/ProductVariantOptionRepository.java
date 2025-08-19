package com.velsystems.ecommerce.repository.product;

import com.velsystems.ecommerce.model.product.ProductVariantOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductVariantOptionRepository extends JpaRepository<ProductVariantOption, UUID> {
}
