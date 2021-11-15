package com.itmo.microservices.demo.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@Lazy
public class OrderDto extends AbstractDto {
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
}


