package com.velsystems.ecommerce.service;

import com.velsystems.ecommerce.dto.request.BrandRequest;
import com.velsystems.ecommerce.dto.response.BrandResponse;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    BrandResponse createBrand(BrandRequest dto);
    BrandResponse updateBrand(UUID id, BrandRequest dto);
    void deleteBrand(UUID id);
    List<BrandResponse> getAllBrands();
}
