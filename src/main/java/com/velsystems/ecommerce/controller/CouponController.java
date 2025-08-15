package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.CouponRequestDto;
import com.velsystems.ecommerce.dto.response.CouponResponseDto;
import com.velsystems.ecommerce.dto.response.CouponStatsDto;
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
    public ResponseEntity<ApiResponse> addCoupon(@Valid @RequestBody CouponRequestDto dto) {
        CouponResponseDto coupon = couponService.addCoupon(dto);
        return ResponseEntity.ok(new ApiResponse("success", coupon));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCoupon(@PathVariable UUID id,
                                                    @Valid @RequestBody CouponRequestDto dto) {
        CouponResponseDto coupon = couponService.updateCoupon(id, dto);
        return ResponseEntity.ok(new ApiResponse("success", coupon));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCoupon(@PathVariable UUID id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok(new ApiResponse("success", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCouponById(@PathVariable UUID id) {
        Optional<CouponResponseDto> coupon = couponService.getCouponById(id);
        return coupon.map(c -> ResponseEntity.ok(new ApiResponse("success", c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse> getCouponByCode(@PathVariable String code) {
        Optional<CouponResponseDto> coupon = couponService.getCouponByCode(code);
        return coupon.map(c -> ResponseEntity.ok(new ApiResponse("success", c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CouponResponseDto> coupons = couponService.getAllCoupons(PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse("success", coupons));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse> getCouponStats() {
        CouponStatsDto stats = couponService.getCouponStats();
        return ResponseEntity.ok(new ApiResponse("success", stats));
    }
}