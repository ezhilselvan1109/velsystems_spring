package com.velsystems.ecommerce.service.product;

import com.velsystems.ecommerce.dto.request.product.ProductRequest;
import com.velsystems.ecommerce.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(UUID id, ProductRequest request);
    void deleteProduct(UUID id);
    ProductResponse getProductById(UUID id);
    List<ProductResponse> getAllProducts();
    Page<ProductResponse> getAllProductsPaged(int page, int size);
}
