package com.itmo.microservices.demo.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OrderDto {
    private UUID uuid;
    private LocalDateTime timeCreated;
    private Map<OrderItemDto, Integer> itemList;
    private OrderStatus status;
    private Timestamp deliveryInfo;

    public OrderDto() {
        this.uuid = UUID.randomUUID();
        this.timeCreated = LocalDateTime.now();
        this.itemList = new HashMap<>();
        this.status = OrderStatus.COLLECTING;
        this.deliveryInfo = new Timestamp(0);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Map<OrderItemDto, Integer> getItemList() {
        return itemList;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Timestamp getDeliveryInfo() {
        return deliveryInfo;
    }
}


