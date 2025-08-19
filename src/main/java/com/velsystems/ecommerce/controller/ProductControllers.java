package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.ProductCreateRequest;
import com.velsystems.ecommerce.dto.ProductResponse;
import com.velsystems.ecommerce.dto.ProductVariantCreateRequest;
import com.velsystems.ecommerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductControllers {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductCreateRequest request) {
        ProductResponse createdProduct = productService.createProduct(request);
        return ResponseEntity.ok(createdProduct);
    }

    // Fetch product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable UUID id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // Create a variant for existing product
    @PostMapping("/{productId}/variants")
    public ResponseEntity<ProductResponse> createVariant(@PathVariable UUID productId,
                                                         @RequestBody ProductVariantCreateRequest request) {
        ProductResponse updatedProduct = productService.createVariant(productId, request);
        return ResponseEntity.ok(updatedProduct);
    }
}
