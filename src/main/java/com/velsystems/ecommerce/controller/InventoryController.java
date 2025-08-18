package com.velsystems.ecommerce.controller;

import com.velsystems.ecommerce.dto.request.InventoryRequest;
import com.velsystems.ecommerce.dto.response.InventoryResponse;
import com.velsystems.ecommerce.response.ApiResponse;
import com.velsystems.ecommerce.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{variantId}")
    public ResponseEntity<ApiResponse> getInventory(@PathVariable UUID variantId) {
        InventoryResponse response = inventoryService.getByVariant(variantId);
        return ResponseEntity.ok(new ApiResponse("inventory fetched successfully", response));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateInventory(@RequestBody InventoryRequest request) {
        InventoryResponse response = inventoryService.updateStock(request);
        return ResponseEntity.ok(new ApiResponse("All inventories fetched successfully", response));
    }

    @PostMapping("/{variantId}/adjust")
    public ResponseEntity<ApiResponse> adjustStock(
            @PathVariable UUID variantId,
            @RequestParam int quantity
    ) {
        InventoryResponse response = inventoryService.adjustStock(variantId, quantity);
        return ResponseEntity.ok(new ApiResponse("All inventories fetched successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllInventories() {
        return ResponseEntity.ok(new ApiResponse("All inventories fetched successfully", inventoryService.getAllInventories()));
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse> getInventoriesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(new ApiResponse("Inventories (paged) fetched successfully", inventoryService.getInventoriesPage(page, size)));
    }
}
