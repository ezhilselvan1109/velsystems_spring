package com.velsystems.ecommerce.service.product;

import com.velsystems.ecommerce.dto.ProductCreateRequest;
import com.velsystems.ecommerce.dto.ProductResponse;
import com.velsystems.ecommerce.dto.ProductVariantCreateRequest;

import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(ProductCreateRequest request);
    ProductResponse getProductById(UUID productId);
    ProductResponse createVariant(UUID productId, ProductVariantCreateRequest request);
}

