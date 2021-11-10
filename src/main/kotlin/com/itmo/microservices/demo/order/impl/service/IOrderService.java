package com.itmo.microservices.demo.order.impl.service;

import com.itmo.microservices.demo.order.api.dto.Booking;
import com.itmo.microservices.demo.order.api.dto.OrderDto;

import java.io.IOException;
import java.util.UUID;

public interface IOrderService {
    OrderDto createOrder();
    OrderDto getOrderById(UUID orderId);
    void putItemToOrder(UUID orderId, UUID itemId, int amount);
    Booking book(UUID orderId) throws IOException;
    void selectDeliveryTime(UUID orderId, int seconds) throws IOException;
    OrderDto pay(UUID orderId);
}
