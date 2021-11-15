package com.itmo.microservices.demo.order.api.service;

import com.itmo.microservices.demo.order.api.dto.CatalogItemDto;

import java.util.List;

public interface ICatalogItemService {
    CatalogItemDto addItem(String title,
                 String description,
                 int price,
                 int amount);

    List<CatalogItemDto>  getAllItems();
}
