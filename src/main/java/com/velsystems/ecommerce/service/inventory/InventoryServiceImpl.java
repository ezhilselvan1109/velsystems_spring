package com.velsystems.ecommerce.service.inventory;

import com.velsystems.ecommerce.dto.request.inventory.InventoryCreateRequestDto;
import com.velsystems.ecommerce.dto.request.inventory.InventoryUpdateRequestDto;
import com.velsystems.ecommerce.dto.response.InventoryResponseDto;
import com.velsystems.ecommerce.model.Inventory;
import com.velsystems.ecommerce.model.product.ProductVariant;
import com.velsystems.ecommerce.repository.InventoryRepository;
import com.velsystems.ecommerce.repository.product.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductVariantRepository variantRepository;
    private final ModelMapper mapper;

    private InventoryResponseDto mapToDto(Inventory inventory) {
        InventoryResponseDto dto = mapper.map(inventory, InventoryResponseDto.class);
        dto.setVariantId(inventory.getVariant().getId());
        String status = inventory.getInStock() <= 0 ? "OUT_OF_STOCK" :
                (inventory.getInStock() <= inventory.getLowStockThreshold() ? "LOW_STOCK" : "NORMAL");
        dto.setStatus(status);
        return dto;
    }

    public InventoryResponseDto create(InventoryCreateRequestDto requestDto) {
        Inventory inventory = mapper.map(requestDto, Inventory.class);
        inventory.setId(UUID.randomUUID()); // only for create
        return mapper.map(inventoryRepository.save(inventory), InventoryResponseDto.class);
    }

    // ✅ Update
    public InventoryResponseDto update(UUID id, InventoryUpdateRequestDto requestDto) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        // update only mutable fields
        inventory.setReserved(requestDto.getReserved());
        inventory.setInStock(requestDto.getInStock());
        inventory.setLowStockThreshold(requestDto.getLowStockThreshold());
        inventory.setInStock(requestDto.getInStock());

        return mapper.map(inventoryRepository.save(inventory), InventoryResponseDto.class);
    }

    @Override
    public InventoryResponseDto getByVariantId(UUID variantId) {
        return inventoryRepository.findByVariantId(variantId)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    @Override
    public List<InventoryResponseDto> getAll() {
        return inventoryRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public Page<InventoryResponseDto> getAllPaged(Pageable pageable) {
        return inventoryRepository.findAll(pageable).map(this::mapToDto);
    }

    @Override
    public void delete(UUID inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }

    @Override
    public InventoryResponseDto increaseStock(UUID variantId, int qty) {
        Inventory inventory = inventoryRepository.findByVariantId(variantId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventory.setInStock(inventory.getInStock() + qty);
        return mapToDto(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponseDto decreaseStock(UUID variantId, int qty) {
        Inventory inventory = inventoryRepository.findByVariantId(variantId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        if (inventory.getInStock() < qty) {
            throw new RuntimeException("Not enough stock available");
        }
        inventory.setInStock(inventory.getInStock() - qty);
        inventory.setSold(inventory.getSold() + qty);
        return mapToDto(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponseDto reserveStock(UUID variantId, int qty) {
        Inventory inventory = inventoryRepository.findByVariantId(variantId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        if (inventory.getInStock() < qty) {
            throw new RuntimeException("Not enough stock to reserve");
        }
        inventory.setInStock(inventory.getInStock() - qty);
        inventory.setReserved(inventory.getReserved() + qty);
        return mapToDto(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponseDto releaseReserved(UUID variantId, int qty) {
        Inventory inventory = inventoryRepository.findByVariantId(variantId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        if (inventory.getReserved() < qty) {
            throw new RuntimeException("Not enough reserved stock");
        }
        inventory.setReserved(inventory.getReserved() - qty);
        inventory.setInStock(inventory.getInStock() + qty);
        return mapToDto(inventoryRepository.save(inventory));
    }
}
