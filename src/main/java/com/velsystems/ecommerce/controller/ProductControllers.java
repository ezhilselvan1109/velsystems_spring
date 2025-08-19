package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.product.create.ProductCreateRequest;
import com.velsystems.ecommerce.dto.request.product.update.ProductUpdateRequest;
import com.velsystems.ecommerce.dto.request.product.update.variant.ProductVariantUpdateRequest;
import com.velsystems.ecommerce.dto.response.product.ProductResponse;
import com.velsystems.ecommerce.dto.request.product.create.variant.ProductVariantCreateRequest;
import com.velsystems.ecommerce.dto.response.product.variant.ProductVariantResponse;
import com.velsystems.ecommerce.enums.Status;
import com.velsystems.ecommerce.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product & Product Variant Management APIs")
public class ProductControllers {

    private final ProductService productService;

    @Operation(
            summary = "Create a new Product",
            description = "Creates a new product with brand, category and details."
    )
    @ApiResponse(responseCode = "200", description = "Product created successfully",
            content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductCreateRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @Operation(
            summary = "Get Product by ID",
            description = "Fetch a single product using its unique identifier."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(
            summary = "Create Variant for a Product",
            description = "Adds a new variant to an existing product."
    )
    @PostMapping("/{productId}/variants")
    public ResponseEntity<ProductResponse> createVariant(
            @PathVariable UUID productId,
            @RequestBody ProductVariantCreateRequest request) {
        return ResponseEntity.ok(productService.createVariant(productId, request));
    }

    @Operation(
            summary = "Delete Product",
            description = "Deletes a product permanently using its ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete Variant",
            description = "Deletes a specific product variant by its ID."
    )
    @DeleteMapping("/variants/{variantId}")
    public ResponseEntity<Void> deleteVariant(@PathVariable UUID variantId) {
        productService.deleteVariant(variantId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get All Products (Paginated + Sorted)",
            description = "Returns all products with pagination and sorting support."
    )
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @Parameter(description = "Page number (default: 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size (default: 10)") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field (default: createdAt)") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction: asc or desc (default: desc)") @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, direction));
    }

    @Operation(
            summary = "Filter Products",
            description = "Filter products by brand, category, keyword, and status with pagination & sorting."
    )
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductResponse>> filterProducts(
            @RequestParam(required = false) UUID brandId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "ACTIVE") Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(productService.filterProducts(brandId, categoryId, keyword, status, page, size, sortBy, direction));
    }

    @Operation(
            summary = "Get All Products (Non-paginated)",
            description = "Fetch all products without pagination (use with caution)."
    )
    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(
            summary = "Get Variants of a Product",
            description = "Returns all variants of a given product."
    )
    @GetMapping("/{productId}/variants")
    public ResponseEntity<List<ProductVariantResponse>> getVariants(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getVariantsByProduct(productId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable UUID id,
            @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @PutMapping("/variants/{variantId}")
    public ResponseEntity<ProductVariantResponse> updateVariant(
            @PathVariable UUID variantId,
            @RequestBody ProductVariantUpdateRequest request) {
        return ResponseEntity.ok(productService.updateVariant(variantId, request));
    }

}
