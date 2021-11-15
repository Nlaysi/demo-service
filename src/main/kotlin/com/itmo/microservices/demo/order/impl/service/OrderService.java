package com.itmo.microservices.demo.order.impl.service;

import com.itmo.microservices.demo.order.api.dto.*;
import com.itmo.microservices.demo.order.api.service.IOrderService;
import com.itmo.microservices.demo.order.impl.dao.OrderItemRepository;
import com.itmo.microservices.demo.order.impl.dao.OrderRepository;
import com.itmo.microservices.demo.order.impl.entity.OrderEntity;
import com.itmo.microservices.demo.order.impl.entity.OrderItemEntity;
import com.itmo.microservices.demo.order.impl.external.PaymentApi;
import com.itmo.microservices.demo.order.impl.external.WarehouseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderDto createOrder() {
        OrderEntity newOrder = new OrderEntity();
        orderRepository.save(newOrder);
        return newOrder.toModel();
    }

    @Override
    public OrderDto getOrderById(UUID uuid) {
        try {
            return orderRepository.getById(uuid).toModel();
        } catch (javax.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public OrderDto putItemToOrder(UUID orderId, UUID itemId, int amount) {
        try {
            var order = orderRepository.getById(orderId);
            var orderItem = new OrderItemEntity(orderId, itemId, amount);
            order.getOrderItems().add(orderItem);

            orderRepository.save(order);
            orderItemRepository.save(orderItem);
            return order.toModel();
        } catch (javax.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public BookingDto book(UUID orderId) {
        OrderDto orderDto = getOrderById(orderId);
        // TODO: provide auth token
        WarehouseApi warehouseApi = new WarehouseApi();
        //return response I do no how we will use it
        Set failed = warehouseApi.book(orderDto).getBody();

        assert failed != null;
        if (failed.isEmpty()) {
            return null;
        }
        return new BookingDto(orderId, failed);
    }

    public void unbook(UUID orderId) {
        // TODO: implement
    }

    @Override
    public OrderDto pay(UUID orderId) {
        OrderDto orderDto = getOrderById(orderId);

        PaymentApi paymentApi = new PaymentApi();
        paymentApi.pay(orderDto);
        //TODO: change status

        return orderDto;
    }

    @Override
    public BookingDto selectDeliveryTime(UUID orderId, int seconds) {
        try {
            var order = orderRepository.getById(orderId);
            if (order.getStatus() == OrderStatus.BOOKED) {
                order.setDeliveryInfo(new Timestamp(TimeUnit.SECONDS.toMillis(seconds)));
                orderRepository.save(order);
            }
        } catch (javax.persistence.EntityNotFoundException e) {
            return null;
        }
        return new BookingDto(orderId, new HashSet<>());
    }
}
