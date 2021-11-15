package com.itmo.microservices.demo.order.util.mapping;

import com.itmo.microservices.demo.order.api.dto.OrderDto;
import com.itmo.microservices.demo.order.impl.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper extends AbstractMapper<OrderEntity, OrderDto> {
    @Autowired
    public OrderMapper() {
        super(OrderEntity.class, OrderDto.class);
    }
}
