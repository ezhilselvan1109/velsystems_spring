package com.velsystems.ecommerce.repository.product;

import com.velsystems.ecommerce.enums.Status;
import com.velsystems.ecommerce.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findAll(Pageable pageable);

    // Filter: by brand, category, status, keyword
    Page<Product> findByCategoryIdAndStatusAndNameContainingIgnoreCase(
            UUID categoryId, Status status, String keyword, Pageable pageable
    );

    Page<Product> findByBrandIdAndStatusAndNameContainingIgnoreCase(
            UUID brandId, Status status, String keyword, Pageable pageable
    );
}
