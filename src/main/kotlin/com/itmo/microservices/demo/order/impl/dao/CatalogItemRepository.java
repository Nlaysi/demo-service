package com.itmo.microservices.demo.order.impl.dao;

import com.itmo.microservices.demo.order.impl.entity.CatalogItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CatalogItemRepository extends JpaRepository<CatalogItemEntity, UUID> {
}
