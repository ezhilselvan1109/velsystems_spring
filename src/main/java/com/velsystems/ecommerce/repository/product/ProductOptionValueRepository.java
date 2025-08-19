package com.velsystems.ecommerce.repository.product;

import com.velsystems.ecommerce.model.product.ProductOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductOptionValueRepository extends JpaRepository<ProductOptionValue, UUID> {
}
