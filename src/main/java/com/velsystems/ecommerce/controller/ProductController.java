package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.product.ProductRequest;
import com.velsystems.ecommerce.dto.response.product.ProductResponse;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse product = productService.createProduct(request);
        return ResponseEntity.ok(new ApiResponse("Product created successfully", product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable UUID id,
                                                     @Valid @RequestBody ProductRequest request) {
        ProductResponse product = productService.updateProduct(id, request);
        return ResponseEntity.ok(new ApiResponse("Product updated successfully", product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse("Product deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable UUID id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("Product fetched successfully", product));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts(@RequestParam(required = false) Integer page,
                                                      @RequestParam(required = false) Integer size) {
        if (page != null && size != null) {
            Page<ProductResponse> products = productService.getAllProductsPaged(page, size);
            return ResponseEntity.ok(new ApiResponse("Paged products fetched successfully", products));
        } else {
            List<ProductResponse> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("All products fetched successfully", products));
        }
    }
}
