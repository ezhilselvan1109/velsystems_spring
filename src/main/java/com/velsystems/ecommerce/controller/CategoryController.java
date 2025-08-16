package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.CategoryRequestDto;
import com.velsystems.ecommerce.dto.response.CategoryResponseDto;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryRequestDto dto) {
        return ResponseEntity.ok(new ApiResponse("successful",categoryService.createCategory(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(
            @PathVariable UUID id,
            @RequestBody CategoryRequestDto dto) {
        return ResponseEntity.ok(new ApiResponse("successful",categoryService.updateCategory(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hierarchy")
    public ResponseEntity<ApiResponse> getHierarchy() {
        return ResponseEntity.ok(new ApiResponse("successful",categoryService.getCategoryHierarchy()));
    }
}
