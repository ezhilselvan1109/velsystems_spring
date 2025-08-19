package com.velsystems.ecommerce.service.product;

import com.velsystems.ecommerce.dto.ProductCreateRequest;
import com.velsystems.ecommerce.dto.ProductResponse;
import com.velsystems.ecommerce.dto.ProductVariantCreateRequest;
import com.velsystems.ecommerce.model.Brand;
import com.velsystems.ecommerce.model.Category;
import com.velsystems.ecommerce.model.product.*;
import com.velsystems.ecommerce.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        // Build product
        Product product = Product.builder()
                .brand(Brand.builder().id(request.getBrandId()).build())
                .category(Category.builder().id(request.getCategoryId()).build())
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .build();

        // Add options + values safely
        if (request.getOptions() != null) {
            request.getOptions().forEach(optionRequest -> {
                ProductOption option = ProductOption.builder()
                        .name(optionRequest.getName())
                        .product(product)
                        .values(new HashSet<>()) // initialize set
                        .build();

                if (optionRequest.getValues() != null) {
                    optionRequest.getValues().forEach(valueRequest -> {
                        ProductOptionValue value = ProductOptionValue.builder()
                                .value(valueRequest.getValue())
                                .option(option)
                                .build();
                        option.getValues().add(value);
                    });
                }
                product.getOptions().add(option);
            });
        }

        if (request.getSpecificationGroups() != null) {
            request.getSpecificationGroups().forEach(groupReq -> {
                ProductSpecificationGroup group = ProductSpecificationGroup.builder()
                        .name(groupReq.getName())
                        .product(product)
                        .specifications(new HashSet<>())
                        .build();

                if (groupReq.getSpecifications() != null) {
                    groupReq.getSpecifications().forEach(specReq -> {
                        ProductSpecification spec = ProductSpecification.builder()
                                .attributeName(specReq.getAttributeName())
                                .attributeValue(specReq.getAttributeValue())
                                .group(group)
                                .build();
                        group.getSpecifications().add(spec);
                    });
                }

                product.getSpecificationGroups().add(group);
            });
        }

        // Save product
        Product savedProduct = productRepository.save(product);

        // Map entity to response DTO using ModelMapper
        return modelMapper.map(savedProduct, ProductResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    @Transactional
    public ProductResponse createVariant(UUID productId, ProductVariantCreateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductVariant variant = ProductVariant.builder()
                .sku(request.getSku())
                .price(request.getPrice())
                .product(product)
                .build();

        if (request.getOptions() != null) {
            request.getOptions().forEach(optReq -> {
                ProductOptionValue optionValue = product.getOptions().stream()
                        .flatMap(o -> o.getValues().stream())
                        .filter(v -> v.getId().equals(optReq.getOptionValueId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Option value not found"));

                ProductVariantOption variantOption = ProductVariantOption.builder()
                        .variant(variant)
                        .optionValue(optionValue)
                        .build();

                variant.getVariantOptions().add(variantOption);
            });
        }

        if (request.getImages() != null) {
            request.getImages().forEach(imgReq -> {
                ProductImage variantImage = ProductImage.builder()
                        .imageUrl(imgReq.getImageUrl())
                        .isPrimary(imgReq.getIsPrimary())
                        .sortOrder(imgReq.getSortOrder())
                        .variant(variant)
                        .build();
                variant.getImages().add(variantImage);
            });
        }


        product.getVariants().add(variant);
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductResponse.class);
    }
}