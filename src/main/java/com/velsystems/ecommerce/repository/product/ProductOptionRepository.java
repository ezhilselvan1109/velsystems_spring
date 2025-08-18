package com.velsystems.ecommerce.repository.product;

import com.velsystems.ecommerce.model.product.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductOptionRepository extends JpaRepository<ProductOption, UUID> {
}
