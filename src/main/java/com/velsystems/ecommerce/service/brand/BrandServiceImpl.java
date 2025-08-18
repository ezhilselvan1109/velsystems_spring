package com.velsystems.ecommerce.service.impl;

import com.velsystems.ecommerce.dto.request.BrandRequest;
import com.velsystems.ecommerce.dto.response.BrandResponseDto;
import com.velsystems.ecommerce.model.Brand;
import com.velsystems.ecommerce.repository.BrandRepository;
import com.velsystems.ecommerce.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    @Override
    public BrandResponseDto createBrand(BrandRequest dto) {
        Brand brand = modelMapper.map(dto, Brand.class);
        Brand saved = brandRepository.save(brand);
        return modelMapper.map(saved, BrandResponseDto.class);
    }

    @Override
    public BrandResponseDto updateBrand(UUID id, BrandRequest dto) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        modelMapper.map(dto, brand); // update fields
        Brand updated = brandRepository.save(brand);
        return modelMapper.map(updated, BrandResponseDto.class);
    }

    @Override
    public void deleteBrand(UUID id) {
        brandRepository.deleteById(id);
    }

    @Override
    public List<BrandResponseDto> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(brand -> modelMapper.map(brand, BrandResponseDto.class))
                .toList();
    }
}
