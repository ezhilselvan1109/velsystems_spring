package com.velsystems.ecommerce.repository.product;

import com.velsystems.ecommerce.model.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
}
