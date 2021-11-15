package com.itmo.microservices.demo.order.impl.service;

import com.itmo.microservices.demo.order.api.dto.*;
import com.itmo.microservices.demo.order.api.service.IOrderService;
import com.itmo.microservices.demo.order.impl.dao.CatalogItemRepository;
import com.itmo.microservices.demo.order.impl.dao.OrderItemRepository;
import com.itmo.microservices.demo.order.impl.dao.OrderRepository;
import com.itmo.microservices.demo.order.impl.entity.CatalogItemEntity;
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
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CatalogItemRepository catalogItemRepository;

    private static final String API_URL = "http://77.234.215.138:30019/api/";

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CatalogItemRepository catalogItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.catalogItemRepository = catalogItemRepository;
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
            for (var orderItem: order.getOrderItems()) {
                if (orderItem.getCatalogItem().getUuid() == itemId) {
                    orderItem.setAmount(amount);
                    orderItemRepository.save(orderItem);
                    return order.toModel();
                }
            }

            CatalogItemEntity catalogItem = catalogItemRepository.getById(itemId);
            OrderItemEntity newOrderItem = new OrderItemEntity(UUID.randomUUID(), catalogItem, amount);
            order.getOrderItems().add(newOrderItem);
            orderRepository.save(order);
            orderItemRepository.save(newOrderItem);
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
        warehouseApi.book(orderDto);

        return null;
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
