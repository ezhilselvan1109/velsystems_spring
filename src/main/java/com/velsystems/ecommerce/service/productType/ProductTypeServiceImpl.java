package com.velsystems.ecommerce.service.productType;

import com.velsystems.ecommerce.dto.request.ProductTypeRequestDto;
import com.velsystems.ecommerce.dto.response.ProductTypeResponseDto;
import com.velsystems.ecommerce.model.Category;
import com.velsystems.ecommerce.model.product.ProductType;
import com.velsystems.ecommerce.repository.CategoryRepository;
import com.velsystems.ecommerce.repository.ProductTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductTypeResponseDto create(ProductTypeRequestDto dto) {
        if (productTypeRepository.existsByName(dto.getName())) {
            throw new RuntimeException("ProductType name already exists");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ProductType pt = ProductType.builder()
                .name(dto.getName())
                .category(category)
                .build();

        return mapToDto(productTypeRepository.save(pt));
    }

    @Override
    public ProductTypeResponseDto update(UUID id, ProductTypeRequestDto dto) {
        ProductType pt = productTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductType not found"));

        if (!pt.getName().equals(dto.getName()) && productTypeRepository.existsByName(dto.getName())) {
            throw new RuntimeException("ProductType name already exists");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        pt.setName(dto.getName());
        pt.setCategory(category);

        return mapToDto(productTypeRepository.save(pt));
    }

    @Override
    public void delete(UUID id) {
        ProductType pt = productTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductType not found"));
        productTypeRepository.delete(pt);
    }

    @Override
    public ProductTypeResponseDto getById(UUID id) {
        ProductType pt = productTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductType not found"));
        return mapToDto(pt);
    }

    @Override
    public List<ProductTypeResponseDto> getAll() {
        return productTypeRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ProductTypeResponseDto mapToDto(ProductType pt) {
        ProductTypeResponseDto dto = modelMapper.map(pt, ProductTypeResponseDto.class);
        dto.setCategoryId(pt.getCategory().getId());
        dto.setCategoryName(pt.getCategory().getName());
        return dto;
    }
}
