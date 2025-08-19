package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.ProductCreateRequest;
import com.velsystems.ecommerce.dto.ProductResponse;
import com.velsystems.ecommerce.dto.ProductVariantCreateRequest;
import com.velsystems.ecommerce.enums.Status;
import com.velsystems.ecommerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    // ✅ Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Delete variant
    @DeleteMapping("/variants/{variantId}")
    public ResponseEntity<Void> deleteVariant(@PathVariable UUID variantId) {
        productService.deleteVariant(variantId);
        return ResponseEntity.noContent().build();
    }

    // ✅ Get all products with sorting
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, direction));
    }

    // ✅ Filter products with sorting
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductResponse>> filterProducts(
            @RequestParam(required = false) UUID brandId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "ACTIVE") Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        return ResponseEntity.ok(productService.filterProducts(brandId, categoryId, keyword, status, page, size, sortBy, direction));
    }


}
