package com.velsystems.ecommerce.repository.product;

import com.velsystems.ecommerce.model.product.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, UUID> {
}
