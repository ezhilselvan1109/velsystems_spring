package com.velsystems.ecommerce.repository.product;

import com.velsystems.ecommerce.model.product.ProductSpecificationGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductSpecificationGroupRepository extends JpaRepository<ProductSpecificationGroup, UUID> {
}
