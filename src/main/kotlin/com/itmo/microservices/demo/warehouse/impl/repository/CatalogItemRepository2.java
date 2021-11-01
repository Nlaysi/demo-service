package com.itmo.microservices.demo.warehouse.impl.repository;

import com.itmo.microservices.demo.warehouse.impl.entity.CatalogItem2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CatalogItemRepository2 extends JpaRepository<CatalogItem2, UUID> {
    List<CatalogItem2> findAll();
    boolean existsById(UUID id);
    CatalogItem2 findCatalogItemById(UUID id);
}
