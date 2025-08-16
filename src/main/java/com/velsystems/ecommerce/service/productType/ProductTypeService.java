package com.velsystems.ecommerce.service.productType;

import com.velsystems.ecommerce.dto.request.ProductTypeRequestDto;
import com.velsystems.ecommerce.dto.response.ProductTypeResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductTypeService {

    ProductTypeResponseDto create(ProductTypeRequestDto dto);

    ProductTypeResponseDto update(UUID id, ProductTypeRequestDto dto);

    void delete(UUID id);

    ProductTypeResponseDto getById(UUID id);

    List<ProductTypeResponseDto> getAll();
}
