package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.inventory.InventoryCreateRequestDto;
import com.velsystems.ecommerce.dto.request.inventory.InventoryUpdateRequestDto;
import com.velsystems.ecommerce.dto.response.InventoryResponseDto;
import com.velsystems.ecommerce.response.api.ApiResponse;
import com.velsystems.ecommerce.service.inventory.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory API", description = "Manage product variant inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(summary = "Create inventory", description = "Create new inventory for a variant")
    @PostMapping
    public ResponseEntity<ApiResponse<InventoryResponseDto>> create(@RequestBody InventoryCreateRequestDto requestDto) {
        InventoryResponseDto response = inventoryService.create(requestDto);
        return ResponseEntity.ok(ApiResponse.success("Inventory created successfully", response));
    }

    // ✅ Update existing inventory
    @Operation(summary = "update inventory", description = "update existing inventory for a variant")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> update(
            @PathVariable UUID id,
            @RequestBody InventoryUpdateRequestDto requestDto) {
        InventoryResponseDto response = inventoryService.update(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success("Inventory updated successfully", response));
    }

    @Operation(summary = "Get inventory by variant ID", description = "Fetch inventory details for a specific product variant")
    @GetMapping("/{variantId}")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> getByVariantId(
            @Parameter(description = "UUID of the product variant") @PathVariable UUID variantId) {
        return ResponseEntity.ok(ApiResponse.success("Inventory found",
                inventoryService.getByVariantId(variantId)));
    }

    @Operation(summary = "Get all inventories", description = "Fetch list of all inventories without pagination")
    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryResponseDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("All inventories", inventoryService.getAll()));
    }

    @Operation(summary = "Get paged inventories", description = "Fetch paginated list of inventories")
    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<Page<InventoryResponseDto>>> getAllPaged(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("Paged inventories",
                inventoryService.getAllPaged(pageable)));
    }

    @Operation(summary = "Delete inventory", description = "Delete inventory by its ID")
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "UUID of the inventory") @PathVariable UUID inventoryId) {
        inventoryService.delete(inventoryId);
        return ResponseEntity.ok(ApiResponse.success("Inventory deleted", null));
    }

    @Operation(summary = "Increase stock", description = "Increase stock for a variant")
    @PostMapping("/{variantId}/increase")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> increase(
            @PathVariable UUID variantId,
            @Parameter(description = "Quantity to increase") @RequestParam int qty) {
        return ResponseEntity.ok(ApiResponse.success("Stock increased",
                inventoryService.increaseStock(variantId, qty)));
    }

    @Operation(summary = "Decrease stock", description = "Decrease stock for a variant")
    @PostMapping("/{variantId}/decrease")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> decrease(
            @PathVariable UUID variantId,
            @Parameter(description = "Quantity to decrease") @RequestParam int qty) {
        return ResponseEntity.ok(ApiResponse.success("Stock decreased",
                inventoryService.decreaseStock(variantId, qty)));
    }

    @Operation(summary = "Reserve stock", description = "Reserve stock for pending order")
    @PostMapping("/{variantId}/reserve")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> reserve(
            @PathVariable UUID variantId,
            @Parameter(description = "Quantity to reserve") @RequestParam int qty) {
        return ResponseEntity.ok(ApiResponse.success("Stock reserved",
                inventoryService.reserveStock(variantId, qty)));
    }

    @Operation(summary = "Release reserved stock", description = "Release reserved stock back to available stock")
    @PostMapping("/{variantId}/release")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> release(
            @PathVariable UUID variantId,
            @Parameter(description = "Quantity to release") @RequestParam int qty) {
        return ResponseEntity.ok(ApiResponse.success("Reserved stock released",
                inventoryService.releaseReserved(variantId, qty)));
    }
}
