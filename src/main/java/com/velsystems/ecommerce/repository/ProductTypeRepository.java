package com.velsystems.ecommerce.repository;

import com.velsystems.ecommerce.model.product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductTypeRepository extends JpaRepository<ProductType, UUID> {
    boolean existsByName(String name);
}
