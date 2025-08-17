package com.velsystems.ecommerce.service.product;

import com.velsystems.ecommerce.dto.request.ProductCreateRequest;
import com.velsystems.ecommerce.dto.request.ProductUpdateRequest;
import com.velsystems.ecommerce.dto.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(ProductCreateRequest request);
    ProductResponse getProduct(UUID productId);
    List<ProductResponse> getAllProducts();
    ProductResponse updateProduct(UUID productId, ProductUpdateRequest request);
    void deleteProduct(UUID productId);
}
