package com.itmo.microservices.demo.order.impl.service;

import java.util.UUID;

public interface ICatalogItemService {
    void addItem(String title,
                 String description,
                 int price,
                 int amount);
}
