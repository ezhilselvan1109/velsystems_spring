package com.velsystems.ecommerce.service.inventory;

import com.velsystems.ecommerce.dto.request.InventoryRequest;
import com.velsystems.ecommerce.dto.response.InventoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    InventoryResponse getByVariant(UUID variantId);

    InventoryResponse updateStock(InventoryRequest request);

    InventoryResponse adjustStock(UUID variantId, int quantity);

    List<InventoryResponse> getAllInventories();

    Page<InventoryResponse> getInventoriesPage(int page, int size);
}
