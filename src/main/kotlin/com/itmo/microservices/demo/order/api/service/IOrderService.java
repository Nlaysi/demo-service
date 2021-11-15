package com.itmo.microservices.demo.order.api.service;

import com.itmo.microservices.demo.order.api.dto.BookingDto;
import com.itmo.microservices.demo.order.api.dto.OrderDto;

import java.io.IOException;
import java.util.UUID;

public interface IOrderService {
    OrderDto createOrder();
    OrderDto getOrderById(UUID orderId);
    OrderDto putItemToOrder(UUID orderId, UUID itemId, int amount);
    BookingDto book(UUID orderId) throws IOException;
    BookingDto selectDeliveryTime(UUID orderId, int seconds) throws IOException;
    OrderDto pay(UUID orderId);
}
