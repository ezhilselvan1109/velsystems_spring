package com.velsystems.ecommerce.repository;

import com.velsystems.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByParentIsNullOrderBySortOrderAscNameAsc();
}
