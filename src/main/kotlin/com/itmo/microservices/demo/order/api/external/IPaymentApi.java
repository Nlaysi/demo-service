package com.itmo.microservices.demo.order.api.external;

import com.itmo.microservices.demo.order.api.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface IPaymentApi {
    public ResponseEntity<String> pay (OrderDto orderDto);
}
