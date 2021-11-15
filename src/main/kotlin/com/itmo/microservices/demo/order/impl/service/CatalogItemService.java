package com.itmo.microservices.demo.order.impl.service;

import com.itmo.microservices.demo.order.api.service.ICatalogItemService;
import com.itmo.microservices.demo.order.impl.dao.CatalogItemRepository;
import com.itmo.microservices.demo.order.api.dto.CatalogItemDto;
import com.itmo.microservices.demo.order.impl.entity.CatalogItemEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CatalogItemService implements ICatalogItemService {
    private final CatalogItemRepository catalogItemRepository;

    public CatalogItemService(CatalogItemRepository catalogItemRepository) {
        this.catalogItemRepository = catalogItemRepository;
    }

    @Override
    public CatalogItemDto addItem(String title, String description, int price, int amount) {
        CatalogItemEntity newCatalogItem = new CatalogItemEntity(UUID.randomUUID(), title, description, price, amount);
        catalogItemRepository.saveAndFlush(newCatalogItem);
        return newCatalogItem.toModel();
    }

    @Override
    public List<CatalogItemDto> getAllItems() {
        List<CatalogItemDto> result = new ArrayList<>();
        List<CatalogItemEntity> resultFromDB = catalogItemRepository.findAll();
        for (CatalogItemEntity item: resultFromDB) {
            result.add(new CatalogItemDto(item.getUuid(), item.getTitle(), item.getDescription(), item.getPrice(), item.getAmount()));
        }
        return result;
    }
}
