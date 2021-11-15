package com.itmo.microservices.demo.order.api.external;

import com.itmo.microservices.demo.order.api.dto.OrderDto;
import com.itmo.microservices.demo.order.impl.entity.OrderEntity;
import org.springframework.http.ResponseEntity;


public interface IWarehouseApi {
    ResponseEntity<String> book(OrderEntity order);
    ResponseEntity<String> unbook(OrderEntity order);
}
