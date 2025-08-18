package com.velsystems.ecommerce.service.coupon;

import com.velsystems.ecommerce.dto.request.CouponRequest;
import com.velsystems.ecommerce.dto.response.CouponResponse;
import com.velsystems.ecommerce.dto.response.CouponStats;
import com.velsystems.ecommerce.model.Coupon;
import com.velsystems.ecommerce.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final ModelMapper modelMapper;

    @Override
    public CouponResponse addCoupon(CouponRequest dto) {
        Coupon coupon = modelMapper.map(dto, Coupon.class);
        coupon = couponRepository.save(coupon);
        return modelMapper.map(coupon, CouponResponse.class);
    }

    @Override
    public CouponResponse updateCoupon(UUID id, CouponRequest dto) {
        Coupon coupon = couponRepository.findById(id).map(existing -> {
            modelMapper.map(dto, existing);
            return couponRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Coupon not found with id " + id));
        return modelMapper.map(coupon, CouponResponse.class);
    }

    @Override
    public void deleteCoupon(UUID id) {
        couponRepository.deleteById(id);
    }

    @Override
    public Optional<CouponResponse> getCouponById(UUID id) {
        return couponRepository.findById(id)
                .map(c -> modelMapper.map(c, CouponResponse.class));
    }

    @Override
    public Optional<CouponResponse> getCouponByCode(String code) {
        return couponRepository.findByCode(code)
                .map(c -> modelMapper.map(c, CouponResponse.class));
    }

    @Override
    public Page<CouponResponse> getAllCoupons(Pageable pageable) {
        return couponRepository.findAll(pageable)
                .map(c -> modelMapper.map(c, CouponResponse.class));
    }

    @Override
    public CouponStats getCouponStats() {
        Long active = couponRepository.countActiveCoupons();
        Long totalUsed = couponRepository.totalUsedCount();
        Long expired = couponRepository.countExpiredCoupons();

        return CouponStats.builder()
                .activeCount(active != null ? active : 0L)
                .totalUsedCount(totalUsed != null ? totalUsed : 0L)
                .expiredCount(expired != null ? expired : 0L)
                .build();
    }
}
