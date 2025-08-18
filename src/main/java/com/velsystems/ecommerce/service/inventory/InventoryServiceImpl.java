package com.velsystems.ecommerce.service.inventory;

import com.velsystems.ecommerce.dto.request.InventoryRequest;
import com.velsystems.ecommerce.dto.response.InventoryResponse;
import com.velsystems.ecommerce.model.Inventory;
import com.velsystems.ecommerce.repository.InventoryRepository;
import com.velsystems.ecommerce.repository.product.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductVariantRepository variantRepository;

    public InventoryResponse getByVariant(UUID variantId) {
        Inventory inventory = inventoryRepository.findByVariantId(variantId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for variant " + variantId));
        return mapToResponse(inventory);
    }

    @Transactional
    public InventoryResponse updateStock(InventoryRequest request) {
        Inventory inventory = inventoryRepository.findByVariantId(request.getVariantId())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if (request.getInStock() != null) inventory.setInStock(request.getInStock());
        if (request.getReserved() != null) inventory.setReserved(request.getReserved());
        if (request.getSold() != null) inventory.setSold(request.getSold());
        if (request.getLowStockThreshold() != null) inventory.setLowStockThreshold(request.getLowStockThreshold());

        inventoryRepository.save(inventory);
        return mapToResponse(inventory);
    }

    @Transactional
    public InventoryResponse adjustStock(UUID variantId, int quantity) {
        Inventory inventory = inventoryRepository.findByVariantId(variantId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        inventory.setInStock(inventory.getInStock() + quantity);
        inventoryRepository.save(inventory);

        return mapToResponse(inventory);
    }

    @Override
    public List<InventoryResponse> getAllInventories() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<InventoryResponse> getInventoriesPage(int page, int size) {
        return inventoryRepository.findAll(PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .variantId(inventory.getVariant().getId())
                .inStock(inventory.getInStock())
                .reserved(inventory.getReserved())
                .sold(inventory.getSold())
                .lowStockThreshold(inventory.getLowStockThreshold())
                .lowStock(inventory.getInStock() <= inventory.getLowStockThreshold())
                .build();
    }
}
