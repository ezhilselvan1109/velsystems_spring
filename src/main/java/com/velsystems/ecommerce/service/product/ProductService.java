package com.velsystems.ecommerce.service.product;

import com.velsystems.ecommerce.dto.request.product.create.ProductCreateRequest;
import com.velsystems.ecommerce.dto.response.product.ProductResponse;
import com.velsystems.ecommerce.dto.request.product.create.variant.ProductVariantCreateRequest;
import com.velsystems.ecommerce.dto.response.product.variant.ProductVariantResponse;
import com.velsystems.ecommerce.enums.Status;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(ProductCreateRequest request);
    ProductResponse getProductById(UUID productId);
    ProductResponse createVariant(UUID productId, ProductVariantCreateRequest request);
    void deleteProduct(UUID productId);
    void deleteVariant(UUID variantId);
    Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String direction);
    Page<ProductResponse> filterProducts(UUID brandId, UUID categoryId, String keyword, Status status,
                                         int page, int size, String sortBy, String direction);
    List<ProductVariantResponse> getVariantsByProduct(UUID productId);
    List<ProductResponse> getAllProducts();
}

