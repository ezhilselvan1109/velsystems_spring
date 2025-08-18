package com.velsystems.ecommerce.service.category;

import com.velsystems.ecommerce.dto.request.CategoryRequest;
import com.velsystems.ecommerce.dto.response.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest dto);
    CategoryResponse updateCategory(UUID id, CategoryRequest dto);
    void deleteCategory(UUID id);
    List<CategoryResponse> getCategoryHierarchy();
    List<CategoryResponse> getAllCategories();
}
