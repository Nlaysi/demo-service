package com.itmo.microservices.demo.order.impl.service;

import com.itmo.microservices.demo.order.impl.dao.CatalogItemRepository;
import com.itmo.microservices.demo.order.impl.entity.CatalogItem;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CatalogItemService implements ICatalogItemService {
    private final CatalogItemRepository catalogItemRepository;

    public CatalogItemService(CatalogItemRepository catalogItemRepository) {
        this.catalogItemRepository = catalogItemRepository;
    }

    @Override
    public void addItem(String title, String description, int price, int amount) {
        catalogItemRepository.save(new CatalogItem(UUID.randomUUID(), title, description, price, amount));
    }
}
