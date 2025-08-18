package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.CouponRequest;
import com.velsystems.ecommerce.dto.response.CouponResponse;
import com.velsystems.ecommerce.dto.response.CouponStats;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.service.coupon.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<ApiResponse> addCoupon(@Valid @RequestBody CouponRequest dto) {
        CouponResponse coupon = couponService.addCoupon(dto);
        return ResponseEntity.ok(new ApiResponse("success", coupon));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCoupon(@PathVariable UUID id,
                                                    @Valid @RequestBody CouponRequest dto) {
        CouponResponse coupon = couponService.updateCoupon(id, dto);
        return ResponseEntity.ok(new ApiResponse("success", coupon));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCoupon(@PathVariable UUID id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok(new ApiResponse("success", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCouponById(@PathVariable UUID id) {
        Optional<CouponResponse> coupon = couponService.getCouponById(id);
        return coupon.map(c -> ResponseEntity.ok(new ApiResponse("success", c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse> getCouponByCode(@PathVariable String code) {
        Optional<CouponResponse> coupon = couponService.getCouponByCode(code);
        return coupon.map(c -> ResponseEntity.ok(new ApiResponse("success", c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CouponResponse> coupons = couponService.getAllCoupons(PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse("success", coupons));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse> getCouponStats() {
        CouponStats stats = couponService.getCouponStats();
        return ResponseEntity.ok(new ApiResponse("success", stats));
    }
}