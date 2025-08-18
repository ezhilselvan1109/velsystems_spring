package com.velsystems.ecommerce.service.product;

import com.velsystems.ecommerce.dto.request.*;
import com.velsystems.ecommerce.dto.response.*;
import com.velsystems.ecommerce.model.*;
import com.velsystems.ecommerce.model.product.*;
import com.velsystems.ecommerce.repository.*;
import com.velsystems.ecommerce.repository.product.ProductOptionRepository;
import com.velsystems.ecommerce.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductOptionRepository optionRepository;
    private final ModelMapper modelMapper;

    // ✅ Create
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = buildProductFromRequest(request, null);
        productRepository.save(product);
        return mapToResponse(product);
    }

    // ✅ Update (rebuild nested children)
    @Override
    public ProductResponse updateProduct(UUID id, ProductRequest request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // clear children before re-adding
        existing.getVariants().clear();
        existing.getImages().clear();
        existing.getSpecificationGroups().clear();

        Product updated = buildProductFromRequest(request, existing);
        productRepository.save(updated);

        return mapToResponse(updated);
    }

    // ✅ Delete
    @Override
    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    // ✅ Get One
    @Override
    public ProductResponse getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToResponse(product);
    }

    // ✅ Get All (no pagination)
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Get All (pagination)
    @Override
    public Page<ProductResponse> getAllProductsPaged(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse> responses = productPage.getContent().stream()
                .map(this::mapToResponse)
                .toList();

        return new PageImpl<>(responses, pageable, productPage.getTotalElements());
    }

    // ------------------- Helper: Build Product with Nested -------------------
    private Product buildProductFromRequest(ProductRequest request, Product base) {
        Product product = (base == null) ? new Product() : base;

        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setBrand(brand);
        product.setCategory(category);

        // ---- Images ----
        if (request.getImages() != null) {
            request.getImages().forEach(imgReq -> {
                ProductImage img = ProductImage.builder()
                        .imageUrl(imgReq.getImageUrl())
                        .isPrimary(imgReq.getIsPrimary())
                        .sortOrder(imgReq.getSortOrder())
                        .product(product)
                        .build();
                product.getImages().add(img);
            });
        }

        // ---- Variants ----
        if (request.getVariants() != null) {
            request.getVariants().forEach(vReq -> {
                ProductVariant variant = ProductVariant.builder()
                        .sku(vReq.getSku())
                        .price(vReq.getPrice())
                        .product(product)
                        .build();

                // Variant Options
                if (vReq.getOptions() != null) {
                    vReq.getOptions().forEach(optReq -> {
                        ProductOption option = optionRepository.findById(optReq.getOptionId())
                                .orElseThrow(() -> new RuntimeException("Option not found"));
                        ProductVariantOption variantOpt = ProductVariantOption.builder()
                                .option(option)
                                .value(optReq.getValue())
                                .variant(variant)
                                .build();
                        variant.getOptions().add(variantOpt);
                    });
                }

                // Variant Images
                if (vReq.getImages() != null) {
                    vReq.getImages().forEach(imgReq -> {
                        ProductImage vImg = ProductImage.builder()
                                .imageUrl(imgReq.getImageUrl())
                                .isPrimary(imgReq.getIsPrimary())
                                .sortOrder(imgReq.getSortOrder())
                                .variant(variant)
                                .build();
                        variant.getImages().add(vImg);
                    });
                }

                product.getVariants().add(variant);
            });
        }

        // ---- Specifications ----
        if (request.getSpecificationGroups() != null) {
            request.getSpecificationGroups().forEach(groupReq -> {
                ProductSpecificationGroup group = ProductSpecificationGroup.builder()
                        .name(groupReq.getName())
                        .product(product)
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

        return product;
    }

    // ------------------- Helper: Map to Response -------------------
    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = modelMapper.map(product, ProductResponse.class);
        response.setBrandName(product.getBrand() != null ? product.getBrand().getName() : null);
        response.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);

        // Images
        response.setImages(product.getImages().stream().map(img -> {
            ProductImageResponse r = new ProductImageResponse();
            r.setId(img.getId());
            r.setImageUrl(img.getImageUrl());
            r.setIsPrimary(img.getIsPrimary());
            r.setSortOrder(img.getSortOrder());
            return r;
        }).toList());

        // Variants
        response.setVariants(product.getVariants().stream().map(v -> {
            ProductVariantResponse vr = new ProductVariantResponse();
            vr.setId(v.getId());
            vr.setSku(v.getSku());
            vr.setPrice(v.getPrice());

            vr.setOptions(v.getOptions().stream().map(o -> {
                ProductVariantOptionResponse or = new ProductVariantOptionResponse();
                or.setId(o.getId());
                or.setValue(o.getValue());
                or.setOptionName(o.getOption().getName());
                return or;
            }).toList());

            vr.setImages(v.getImages().stream().map(img -> {
                ProductImageResponse ir = new ProductImageResponse();
                ir.setId(img.getId());
                ir.setImageUrl(img.getImageUrl());
                ir.setIsPrimary(img.getIsPrimary());
                ir.setSortOrder(img.getSortOrder());
                return ir;
            }).toList());

            return vr;
        }).toList());

        // Specs
        response.setSpecificationGroups(product.getSpecificationGroups().stream().map(g -> {
            ProductSpecificationGroupResponse gr = new ProductSpecificationGroupResponse();
            gr.setId(g.getId());
            gr.setName(g.getName());

            gr.setSpecifications(g.getSpecifications().stream().map(s -> {
                ProductSpecificationResponse sr = new ProductSpecificationResponse();
                sr.setId(s.getId());
                sr.setAttributeName(s.getAttributeName());
                sr.setAttributeValue(s.getAttributeValue());
                return sr;
            }).toList());

            return gr;
        }).toList());

        return response;
    }
}
