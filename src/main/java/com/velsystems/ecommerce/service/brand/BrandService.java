package com.velsystems.ecommerce.service;

import com.velsystems.ecommerce.dto.request.BrandRequest;
import com.velsystems.ecommerce.dto.response.BrandResponseDto;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    BrandResponseDto createBrand(BrandRequest dto);
    BrandResponseDto updateBrand(UUID id, BrandRequest dto);
    void deleteBrand(UUID id);
    List<BrandResponseDto> getAllBrands();
}
