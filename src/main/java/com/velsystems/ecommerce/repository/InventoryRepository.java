package com.velsystems.ecommerce.repository;

import com.velsystems.ecommerce.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Optional<Inventory> findByVariantId(UUID variantId);
}
