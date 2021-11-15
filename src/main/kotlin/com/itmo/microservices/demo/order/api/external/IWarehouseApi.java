package com.itmo.microservices.demo.order.api.external;

import com.itmo.microservices.demo.order.api.dto.OrderDto;
import org.springframework.http.ResponseEntity;

import java.util.Set;


public interface IWarehouseApi {
    ResponseEntity<Set> book(OrderDto order);
    ResponseEntity<String> unbook(OrderDto order);
}
