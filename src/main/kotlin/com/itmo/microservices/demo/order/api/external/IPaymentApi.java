package com.itmo.microservices.demo.order.api.external;

import com.itmo.microservices.demo.order.api.dto.OrderDto;

public interface IPaymentApi {
    void pay(OrderDto orderDto);
}
