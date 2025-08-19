package com.velsystems.ecommerce.service.product;

import com.velsystems.ecommerce.dto.request.product.create.ProductCreateRequest;
import com.velsystems.ecommerce.dto.request.product.update.ProductUpdateRequest;
import com.velsystems.ecommerce.dto.request.product.update.variant.ProductVariantUpdateRequest;
import com.velsystems.ecommerce.dto.response.product.ProductResponse;
import com.velsystems.ecommerce.dto.request.product.create.variant.ProductVariantCreateRequest;
import com.velsystems.ecommerce.dto.response.product.variant.ProductVariantResponse;
import com.velsystems.ecommerce.enums.Status;
import com.velsystems.ecommerce.model.Brand;
import com.velsystems.ecommerce.model.Category;
import com.velsystems.ecommerce.model.product.*;
import com.velsystems.ecommerce.repository.product.ProductRepository;
import com.velsystems.ecommerce.repository.product.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
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

    @Override
    @Transactional
    public void deleteProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(productId);
    }

    // ✅ Delete variant
    @Override
    public void deleteVariant(UUID variantId) {
        if (!variantRepository.existsById(variantId)) {
            throw new RuntimeException("Variant not found");
        }
        variantRepository.deleteById(variantId);
    }

    @Override
    public Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable)
                .map(p -> modelMapper.map(p, ProductResponse.class));
    }

    @Override
    public Page<ProductResponse> filterProducts(UUID brandId, UUID categoryId, String keyword, Status status,
                                                int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        keyword = (keyword == null) ? "" : keyword;

        if (brandId != null) {
            return productRepository.findByBrandIdAndStatusAndNameContainingIgnoreCase(brandId, status, keyword, pageable)
                    .map(p -> modelMapper.map(p, ProductResponse.class));
        } else if (categoryId != null) {
            return productRepository.findByCategoryIdAndStatusAndNameContainingIgnoreCase(categoryId, status, keyword, pageable)
                    .map(p -> modelMapper.map(p, ProductResponse.class));
        } else {
            return productRepository.findAll(pageable)
                    .map(p -> modelMapper.map(p, ProductResponse.class));
        }
    }

    @Override
    public List<ProductVariantResponse> getVariantsByProduct(UUID productId) {
        return variantRepository.findByProductId(productId, Pageable.unpaged())
                .getContent()
                .stream()
                .map(v -> modelMapper.map(v, ProductVariantResponse.class))
                .toList();
    }
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(p -> modelMapper.map(p, ProductResponse.class))
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // basic fields
        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setBrand(Brand.builder().id(request.getBrandId()).build());
        product.setCategory(Category.builder().id(request.getCategoryId()).build());

        // ✅ handle options (create/update/delete)
        Map<UUID, ProductOption> existingOptions = product.getOptions()
                .stream().collect(Collectors.toMap(ProductOption::getId, o -> o));

        if (request.getOptions() != null) {
            List<ProductOption> updatedOptions = request.getOptions().stream().map(optReq -> {
                ProductOption option = (optReq.getId() != null && existingOptions.containsKey(optReq.getId()))
                        ? existingOptions.get(optReq.getId())
                        : new ProductOption();

                option.setName(optReq.getName());
                option.setProduct(product);

                // values (same logic create/update/delete)
                Map<UUID, ProductOptionValue> existingValues = option.getValues()
                        .stream().collect(Collectors.toMap(ProductOptionValue::getId, v -> v));

                if (optReq.getValues() != null) {
                    List<ProductOptionValue> updatedValues = optReq.getValues().stream().map(valReq -> {
                        ProductOptionValue value = (valReq.getId() != null && existingValues.containsKey(valReq.getId()))
                                ? existingValues.get(valReq.getId())
                                : new ProductOptionValue();
                        value.setValue(valReq.getValue());
                        value.setOption(option);
                        return value;
                    }).toList();
                    option.setValues(new HashSet<>(updatedValues));
                }
                return option;
            }).toList();

            product.setOptions(new HashSet<>(updatedOptions));
        }

        // ✅ handle specification groups
        Map<UUID, ProductSpecificationGroup> existingGroups = product.getSpecificationGroups()
                .stream().collect(Collectors.toMap(ProductSpecificationGroup::getId, g -> g));

        if (request.getSpecificationGroups() != null) {
            List<ProductSpecificationGroup> updatedGroups = request.getSpecificationGroups().stream().map(groupReq -> {
                ProductSpecificationGroup group = (groupReq.getId() != null && existingGroups.containsKey(groupReq.getId()))
                        ? existingGroups.get(groupReq.getId())
                        : new ProductSpecificationGroup();

                group.setName(groupReq.getName());
                group.setProduct(product);

                Map<UUID, ProductSpecification> existingSpecs = group.getSpecifications()
                        .stream().collect(Collectors.toMap(ProductSpecification::getId, s -> s));

                if (groupReq.getSpecifications() != null) {
                    List<ProductSpecification> updatedSpecs = groupReq.getSpecifications().stream().map(specReq -> {
                        ProductSpecification spec = (specReq.getId() != null && existingSpecs.containsKey(specReq.getId()))
                                ? existingSpecs.get(specReq.getId())
                                : new ProductSpecification();
                        spec.setAttributeName(specReq.getAttributeName());
                        spec.setAttributeValue(specReq.getAttributeValue());
                        spec.setGroup(group);
                        return spec;
                    }).toList();
                    group.setSpecifications(new HashSet<>(updatedSpecs));
                }

                return group;
            }).toList();

            product.setSpecificationGroups(new HashSet<>(updatedGroups));
        }

        Product saved = productRepository.save(product);
        return modelMapper.map(saved, ProductResponse.class);
    }

    @Override
    @Transactional
    public ProductVariantResponse updateVariant(UUID variantId, ProductVariantUpdateRequest request) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        variant.setSku(request.getSku());
        variant.setPrice(request.getPrice());

        // ✅ options
        if (request.getOptions() != null) {
            List<ProductVariantOption> updatedOptions = request.getOptions().stream().map(optReq -> {
                ProductOptionValue optionValue = variant.getProduct().getOptions().stream()
                        .flatMap(o -> o.getValues().stream())
                        .filter(v -> v.getId().equals(optReq.getOptionValueId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Option value not found"));

                ProductVariantOption variantOption = (optReq.getId() != null)
                        ? variant.getVariantOptions().stream()
                        .filter(vo -> vo.getId().equals(optReq.getId()))
                        .findFirst()
                        .orElse(new ProductVariantOption())
                        : new ProductVariantOption();

                variantOption.setOptionValue(optionValue);
                variantOption.setVariant(variant);
                return variantOption;
            }).toList();

            variant.setVariantOptions(new HashSet<>(updatedOptions));
        }

        // ✅ images
        if (request.getImages() != null) {
            List<ProductImage> updatedImages = request.getImages().stream().map(imgReq -> {
                ProductImage image = (imgReq.getId() != null)
                        ? variant.getImages().stream().filter(i -> i.getId().equals(imgReq.getId())).findFirst().orElse(new ProductImage())
                        : new ProductImage();
                image.setImageUrl(imgReq.getImageUrl());
                image.setIsPrimary(imgReq.getIsPrimary());
                image.setSortOrder(imgReq.getSortOrder());
                image.setVariant(variant);
                return image;
            }).toList();

            variant.setImages(new HashSet<>(updatedImages));
        }

        ProductVariant saved = variantRepository.save(variant);
        return modelMapper.map(saved, ProductVariantResponse.class);
    }
}