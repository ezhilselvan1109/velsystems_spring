package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.BrandRequest;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody BrandRequest dto) {
        return ResponseEntity.ok(new ApiResponse(
                "Brand created successfully",
                brandService.createBrand(dto)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable UUID id, @RequestBody BrandRequest dto) {
        return ResponseEntity.ok(new ApiResponse(
                "Brand updated successfully",
                brandService.updateBrand(id, dto)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok(new ApiResponse(
                "Brand deleted successfully",
                null
        ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        return ResponseEntity.ok(new ApiResponse(
                "Fetched all brands",
                brandService.getAllBrands()
        ));
    }
}
