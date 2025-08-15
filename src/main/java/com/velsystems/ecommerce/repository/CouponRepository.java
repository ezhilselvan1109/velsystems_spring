package com.velsystems.ecommerce.repository;

import com.velsystems.ecommerce.model.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    Optional<Coupon> findByCode(String code);
    Page<Coupon> findAll(Pageable pageable);
    @Query("SELECT COUNT(c) FROM Coupon c WHERE c.active = true AND (c.endsAt IS NULL OR c.endsAt > CURRENT_TIMESTAMP)")
    Long countActiveCoupons();

    @Query("SELECT SUM(c.usedCount) FROM Coupon c")
    Long totalUsedCount();

    @Query("SELECT COUNT(c) FROM Coupon c WHERE c.active = false OR (c.endsAt IS NOT NULL AND c.endsAt <= CURRENT_TIMESTAMP)")
    Long countExpiredCoupons();
}
