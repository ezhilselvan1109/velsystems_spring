package com.velsystems.ecommerce.repository;

import com.velsystems.ecommerce.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByProductId(UUID productId);
    List<Review> findByUserId(UUID userId);
    Optional<Review> findByProductIdAndUserId(UUID productId, UUID userId);
}
