package com.itmo.microservices.demo.order.api.external;

import com.itmo.microservices.demo.order.api.dto.OrderDto;
import org.springframework.http.ResponseEntity;


public interface IWarehouseApi {
    ResponseEntity<String> book(OrderDto orderDto);
}
