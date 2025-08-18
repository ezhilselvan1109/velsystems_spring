package com.velsystems.ecommerce.service.category;

import com.velsystems.ecommerce.dto.request.CategoryRequest;
import com.velsystems.ecommerce.dto.response.CategoryResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequest dto);
    CategoryResponseDto updateCategory(UUID id, CategoryRequest dto);
    void deleteCategory(UUID id);
    List<CategoryResponseDto> getCategoryHierarchy();
    List<CategoryResponseDto> getAllCategories();
}
