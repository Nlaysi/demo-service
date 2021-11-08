package com.itmo.microservices.demo.order.api.dto;

import com.itmo.microservices.demo.order.impl.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@Lazy
public class OrderDto {
    private UUID uuid;
    private LocalDateTime timeCreated;
    private List<OrderItemDto> itemList;
    private OrderStatus status;
    private Timestamp deliveryInfo;

    public OrderDto() {
        this.uuid = UUID.randomUUID();
        this.timeCreated = LocalDateTime.now();
        this.itemList = new ArrayList<>();
        this.status = OrderStatus.COLLECTING;
        this.deliveryInfo = new Timestamp(0);
    }

    public OrderEntity toEntity() {
        return new OrderEntity(this.uuid, this.timeCreated, this.status, this.deliveryInfo, this.itemList.stream().map(OrderItemDto::toEntity).collect(Collectors.toList()));
    }
}


