package com.itmo.microservices.demo.order.impl.service;

import com.itmo.microservices.demo.order.api.BookingException;
import com.itmo.microservices.demo.order.api.dto.BookingDto;
import com.itmo.microservices.demo.order.api.dto.OrderDto;
import com.itmo.microservices.demo.order.api.dto.OrderStatus;
import com.itmo.microservices.demo.order.api.service.IOrderService;
import com.itmo.microservices.demo.order.impl.dao.OrderItemRepository;
import com.itmo.microservices.demo.order.impl.dao.OrderRepository;
import com.itmo.microservices.demo.order.impl.entity.BookingAttemptStatus;
import com.itmo.microservices.demo.order.impl.entity.BookingResponse;
import com.itmo.microservices.demo.order.impl.entity.OrderEntity;
import com.itmo.microservices.demo.order.impl.entity.OrderItemEntity;
import com.itmo.microservices.demo.order.impl.external.PaymentApi;
import com.itmo.microservices.demo.order.impl.external.WarehouseApi;
import com.itmo.microservices.demo.order.util.mapping.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDto createOrder() {
        OrderEntity newOrder = new OrderEntity();
        orderRepository.save(newOrder);
        return orderMapper.toDto(newOrder);
    }

    @Override
    public OrderDto getOrderById(UUID uuid) {
        try {
            return orderMapper.toDto(orderRepository.getById(uuid));
        } catch (javax.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public OrderDto putItemToOrder(UUID orderId, UUID itemId, int amount) {
        try {
            var order = orderRepository.getById(orderId);

            if (order.getStatus() == OrderStatus.BOOKED) {
                WarehouseApi warehouseApi = new WarehouseApi();
                warehouseApi.unbookOrder(orderMapper.toDto(order));
                order.setStatus(OrderStatus.COLLECTING);
            }
            var orderItem = new OrderItemEntity(itemId, amount);

            order.getOrderItems().add(orderItem);

            orderItemRepository.save(orderItem);
            orderRepository.save(order);
            return orderMapper.toDto(order);
        } catch (javax.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public BookingDto bookOrder(UUID orderId) throws BookingException {
        try {
            var order = orderRepository.getById(orderId);
            if (order.getStatus() != OrderStatus.COLLECTING) {
                return null;
            }
            WarehouseApi warehouseApi = new WarehouseApi();
            BookingResponse bookingResponse = warehouseApi.bookOrder(orderMapper.toDto(order));

            if (bookingResponse.getStatus() == BookingAttemptStatus.SUCCESS) {
                order.setStatus(OrderStatus.BOOKED);
                orderRepository.save(order);
                return new BookingDto(orderId);
            }

            if (bookingResponse.getStatus() == BookingAttemptStatus.NO_RESPONSE) {
                throw new BookingException("Failed to communicate with warehouse service");
            }

            return new BookingDto(orderId, order.getOrderItems().stream().map(OrderItemEntity::getCatalogItemId).collect(Collectors.toSet()));
        } catch (javax.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public boolean startPayment(UUID orderId) {
        var order = orderRepository.getById(orderId);
        if (order.getStatus() != OrderStatus.BOOKED) {
            return false;
        }

        PaymentApi paymentApi = new PaymentApi();
        paymentApi.pay(orderMapper.toDto(order));

        return true;
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
