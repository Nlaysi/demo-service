package com.itmo.microservices.demo.order.api.external;

import com.itmo.microservices.demo.order.impl.entity.OrderEntity;
import org.springframework.http.ResponseEntity;

import java.util.Set;


public interface IWarehouseApi {
    ResponseEntity<Set> book(OrderEntity order);
    ResponseEntity<String> unbook(OrderEntity order);
}
