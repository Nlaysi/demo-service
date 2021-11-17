package com.itmo.microservices.demo.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends AbstractDto {
    private UUID uuid;
    private LocalDateTime timeCreated;
    private List<OrderItemDto> orderItems;
    private OrderStatus status;
    private Timestamp deliveryInfo;
}


