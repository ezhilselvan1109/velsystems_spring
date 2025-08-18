package com.velsystems.ecommerce.service.coupon;

import com.velsystems.ecommerce.dto.request.CouponRequest;
import com.velsystems.ecommerce.dto.response.CouponResponse;
import com.velsystems.ecommerce.dto.response.CouponStats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CouponService {
    CouponResponse addCoupon(CouponRequest dto);
    CouponResponse updateCoupon(UUID id, CouponRequest dto);
    void deleteCoupon(UUID id);
    Optional<CouponResponse> getCouponById(UUID id);
    Optional<CouponResponse> getCouponByCode(String code);
    Page<CouponResponse> getAllCoupons(Pageable pageable);
    CouponStats getCouponStats();
}
