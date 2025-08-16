package com.velsystems.ecommerce.service;

import com.velsystems.ecommerce.dto.request.BrandRequestDto;
import com.velsystems.ecommerce.dto.response.BrandResponseDto;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    BrandResponseDto createBrand(BrandRequestDto dto);
    BrandResponseDto updateBrand(UUID id, BrandRequestDto dto);
    void deleteBrand(UUID id);
    List<BrandResponseDto> getAllBrands();
}
