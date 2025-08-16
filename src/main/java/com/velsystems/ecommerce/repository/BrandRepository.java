package com.velsystems.ecommerce.repository;

import com.velsystems.ecommerce.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
}
