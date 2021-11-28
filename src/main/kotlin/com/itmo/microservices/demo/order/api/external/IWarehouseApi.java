package com.itmo.microservices.demo.order.api.external;

import com.itmo.microservices.demo.order.api.dto.OrderDto;
import com.itmo.microservices.demo.order.impl.entity.BookingResponse;


public interface IWarehouseApi {
    BookingResponse bookOrder(OrderDto order);

    void unbookOrder(OrderDto order);
}
