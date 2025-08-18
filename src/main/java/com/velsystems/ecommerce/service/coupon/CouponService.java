package com.velsystems.ecommerce.service.coupon;

import com.velsystems.ecommerce.dto.request.CouponRequest;
import com.velsystems.ecommerce.dto.response.CouponResponseDto;
import com.velsystems.ecommerce.dto.response.CouponStatsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CouponService {
    CouponResponseDto addCoupon(CouponRequest dto);
    CouponResponseDto updateCoupon(UUID id, CouponRequest dto);
    void deleteCoupon(UUID id);
    Optional<CouponResponseDto> getCouponById(UUID id);
    Optional<CouponResponseDto> getCouponByCode(String code);
    Page<CouponResponseDto> getAllCoupons(Pageable pageable);
    CouponStatsDto getCouponStats();
}
