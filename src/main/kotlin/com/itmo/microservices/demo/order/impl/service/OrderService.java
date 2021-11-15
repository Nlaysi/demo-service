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
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private static final String API_URL = "http://77.234.215.138:30019/api/";
    private static final String LOCAL_API_URL = "http://localhost:8080/api/";

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
            if (order.getStatus() == OrderStatus.BOOKED) {
                WarehouseApi warehouseApi = new WarehouseApi();
                warehouseApi.unbook(order);
                order.setStatus(OrderStatus.COLLECTING);
            }
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
        try {
            var order = orderRepository.getById(orderId);
            if (order.getStatus() != OrderStatus.COLLECTING) {
                return null;
            }
            // TODO: provide auth token
            WarehouseApi warehouseApi = new WarehouseApi();
            //return response I do no how we will use it
            warehouseApi.book(order);
            return new BookingDto();
        } catch (javax.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public boolean startPayment(UUID orderId) {
        OrderDto orderDto = getOrderById(orderId);

        PaymentApi paymentApi = new PaymentApi();
        paymentApi.pay(orderDto);
        //TODO: change status

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

    @Override
    public boolean finalizePayment(UUID orderId) {
        try {
            var order = orderRepository.getById(orderId);
            if (order.getStatus() != OrderStatus.BOOKED) {
                return false;
            }
            order.setStatus(OrderStatus.PAID);
        } catch (javax.persistence.EntityNotFoundException e) {
            return false;
        }
        return true;
    }


    private ResponseEntity<String> sendRequest(OrderDto orderDto, String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, OrderDto> parameters = new HashMap<>();
        parameters.put("order", orderDto);

        HttpEntity<Map<String, OrderDto>> request = new HttpEntity<>(parameters, headers);
        return restTemplate.postForEntity(url, request, String.class);
    }
}
