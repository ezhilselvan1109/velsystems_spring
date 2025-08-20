package com.velsystems.ecommerce.service.inventory;

import com.velsystems.ecommerce.dto.request.inventory.InventoryCreateRequestDto;
import com.velsystems.ecommerce.dto.request.inventory.InventoryUpdateRequestDto;
import com.velsystems.ecommerce.dto.response.InventoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    InventoryResponseDto create(InventoryCreateRequestDto requestDto);
    InventoryResponseDto update(UUID id, InventoryUpdateRequestDto requestDto);
    InventoryResponseDto getByVariantId(UUID variantId);
    List<InventoryResponseDto> getAll();
    Page<InventoryResponseDto> getAllPaged(Pageable pageable); // 👈 Pagination
    void delete(UUID inventoryId);

    // Business operations
    InventoryResponseDto increaseStock(UUID variantId, int qty);
    InventoryResponseDto decreaseStock(UUID variantId, int qty);
    InventoryResponseDto reserveStock(UUID variantId, int qty);
    InventoryResponseDto releaseReserved(UUID variantId, int qty);
}
