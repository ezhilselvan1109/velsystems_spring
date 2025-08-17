package com.velsystems.ecommerce.service.product;

import com.velsystems.ecommerce.dto.request.*;
import com.velsystems.ecommerce.dto.response.*;
import com.velsystems.ecommerce.model.Brand;
import com.velsystems.ecommerce.model.product.*;
import com.velsystems.ecommerce.repository.*;
import com.velsystems.ecommerce.repository.product.ProductImageRepository;
import com.velsystems.ecommerce.repository.product.ProductRepository;
import com.velsystems.ecommerce.repository.product.ProductSpecificationGroupRepository;
import com.velsystems.ecommerce.repository.product.ProductTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductSpecificationGroupRepository productSpecificationGroupRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("Brand not found"));

        ProductType productType = productTypeRepository.findById(request.getProductTypeId())
                .orElseThrow(() -> new EntityNotFoundException("ProductType not found"));

        Product product = Product.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .brand(brand)
                .productType(productType)
                .price(request.getPrice())
                .stock(request.getStock())
                .description(request.getDescription())
                .build();

        // Handle images
        if (request.getImageUrls() != null) {
            Set<ProductImage> images = request.getImageUrls().stream()
                    .map(imgDto -> ProductImage.builder()
                            .imageUrl(imgDto.getImageUrl())
                            .isPrimary(Boolean.TRUE.equals(imgDto.getIsPrimary()))
                            .product(product)
                            .build())
                    .collect(Collectors.toSet());
            product.setImages(images);
        }

        // Handle specification groups
        if (request.getSpecificationGroups() != null) {
            Set<ProductSpecificationGroup> groups = request.getSpecificationGroups().stream()
                    .map(groupDto -> {
                        ProductSpecificationGroup group = ProductSpecificationGroup.builder()
                                .name(groupDto.getName())
                                .product(product)
                                .build();

                        Set<ProductSpecification> specs = groupDto.getSpecifications().stream()
                                .map(specDto -> ProductSpecification.builder()
                                        .attributeName(specDto.getAttributeName())
                                        .attributeValue(specDto.getAttributeValue())
                                        .group(group)
                                        .build())
                                .collect(Collectors.toSet());
                        group.setSpecifications(specs);
                        return group;
                    })
                    .collect(Collectors.toSet());
            product.setSpecificationGroups(groups);
        }

        productRepository.save(product);
        return mapToResponse(product);
    }

    @Override
    public ProductResponse getProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(UUID productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("Brand not found"));

        ProductType productType = productTypeRepository.findById(request.getProductTypeId())
                .orElseThrow(() -> new EntityNotFoundException("ProductType not found"));

        // Update basic fields
        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setBrand(brand);
        product.setProductType(productType);
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setDescription(request.getDescription());

        // Remove images
        if (request.getImageUrlIds() != null && !request.getImageUrlIds().isEmpty()) {
            request.getImageUrlIds().forEach(id -> {
                ProductImage img = productImageRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Image not found"));
                product.getImages().remove(img);
                productImageRepository.delete(img);
            });
        }

        // Add new images
        if (request.getImageUrls() != null) {
            for (ProductImageRequestDto imgDto : request.getImageUrls()) {
                ProductImage image = ProductImage.builder()
                        .imageUrl(imgDto.getImageUrl())
                        .isPrimary(Boolean.TRUE.equals(imgDto.getIsPrimary()))
                        .product(product)
                        .build();
                product.getImages().add(image);
            }
        }

        // Remove specification groups
        if (request.getSpecificationGroupIds() != null && !request.getSpecificationGroupIds().isEmpty()) {
            request.getSpecificationGroupIds().forEach(id -> {
                ProductSpecificationGroup group = productSpecificationGroupRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Specification group not found"));
                product.getSpecificationGroups().remove(group);
                productSpecificationGroupRepository.delete(group);
            });
        }

        // Add new specification groups
        if (request.getSpecificationGroups() != null) {
            for (ProductSpecificationGroupRequest groupDto : request.getSpecificationGroups()) {
                ProductSpecificationGroup group = ProductSpecificationGroup.builder()
                        .name(groupDto.getName())
                        .product(product)
                        .build();

                Set<ProductSpecification> specs = groupDto.getSpecifications().stream()
                        .map(specDto -> ProductSpecification.builder()
                                .attributeName(specDto.getAttributeName())
                                .attributeValue(specDto.getAttributeValue())
                                .group(group)
                                .build())
                        .collect(Collectors.toSet());
                group.setSpecifications(specs);

                product.getSpecificationGroups().add(group);
            }
        }

        productRepository.save(product);
        return mapToResponse(product);
    }

    @Override
    public void deleteProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found");
        }
        productRepository.deleteById(productId);
    }

    @Override
    public Page<ProductResponse> getProductsPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(this::mapToResponse);
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = modelMapper.map(product, ProductResponse.class);

        // Custom mapping for nested fields
        response.setBrand(modelMapper.map(product.getBrand(), BrandResponseDto.class));
        response.setProductType(modelMapper.map(product.getProductType(), ProductTypeResponse.class));

        response.setImage(product.getImages().stream()
                .map(img -> modelMapper.map(img, ProductImageResponseDto.class))
                .collect(Collectors.toList()));

        response.setSpecificationGroup(product.getSpecificationGroups().stream()
                .map(group -> ProductSpecificationGroupResponse.builder()
                        .id(group.getId())
                        .name(group.getName())
                        .specifications(group.getSpecifications().stream()
                                .map(spec -> modelMapper.map(spec, ProductSpecificationResponse.class))
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList()));

        return response;
    }
}
