package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.ProductTypeRequestDto;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.service.productType.ProductTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/product-types")
public class ProductTypeController {

    @Autowired
    private ProductTypeService service;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(new ApiResponse("ProductType fetched successfully", service.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(){
        return ResponseEntity.ok(new ApiResponse("All ProductTypes fetched successfully", service.getAll()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody ProductTypeRequestDto dto){
        return ResponseEntity.ok(new ApiResponse("ProductType created successfully", service.create(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable UUID id, @Valid @RequestBody ProductTypeRequestDto dto){
        return ResponseEntity.ok(new ApiResponse("ProductType updated successfully", service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse("ProductType deleted successfully", null));
    }
}
