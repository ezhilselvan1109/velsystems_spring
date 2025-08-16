package com.velsystems.ecommerce.service.category;

import com.velsystems.ecommerce.dto.request.CategoryRequestDto;
import com.velsystems.ecommerce.dto.response.CategoryResponseDto;
import com.velsystems.ecommerce.enums.CategoryStatus;
import com.velsystems.ecommerce.model.Category;
import com.velsystems.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto dto) {
        Category category = Category.builder()
                .name(dto.getName())
                .slug(dto.getSlug())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .status(dto.getStatus() != null ? dto.getStatus() : CategoryStatus.ACTIVE)
                .sortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0)
                .build();

        if (dto.getParentId() != null) {
            categoryRepository.findById(dto.getParentId()).ifPresent(category::setParent);
        }

        Category saved = categoryRepository.save(category);
        return mapToDtoWithChildren(saved);
    }

    @Override
    public CategoryResponseDto updateCategory(UUID id, CategoryRequestDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());
        category.setStatus(dto.getStatus());
        category.setSortOrder(dto.getSortOrder());

        if (dto.getParentId() != null) {
            categoryRepository.findById(dto.getParentId()).ifPresent(category::setParent);
        } else {
            category.setParent(null);
        }

        Category updated = categoryRepository.save(category);
        return mapToDtoWithChildren(updated);
    }

    @Override
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryResponseDto> getCategoryHierarchy() {
        return categoryRepository.findByParentIsNullOrderBySortOrderAscNameAsc()
                .stream()
                .map(this::mapToDtoWithChildren)
                .collect(Collectors.toList());
    }

    private CategoryResponseDto mapToDtoWithChildren(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .status(category.getStatus())
                .sortOrder(category.getSortOrder())
                .children(
                        category.getChildren() == null ? List.of() :
                                category.getChildren().stream()
                                        .map(this::mapToDtoWithChildren)
                                        .collect(Collectors.toList())
                )
                .build();
    }
}
