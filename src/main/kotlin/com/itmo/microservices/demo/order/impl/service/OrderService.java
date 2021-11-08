package com.itmo.microservices.demo.order.impl.service;

import com.itmo.microservices.demo.order.api.dto.Booking;
import com.itmo.microservices.demo.order.api.dto.OrderDto;
import com.itmo.microservices.demo.order.api.dto.OrderItemDto;
import com.itmo.microservices.demo.order.impl.dao.CatalogItemRepository;
import com.itmo.microservices.demo.order.impl.dao.OrderItemRepository;
import com.itmo.microservices.demo.order.impl.dao.OrderRepository;
import com.itmo.microservices.demo.order.impl.entity.CatalogItemEntity;
import com.itmo.microservices.demo.order.impl.entity.OrderEntity;
import com.itmo.microservices.demo.order.impl.entity.OrderItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {
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
        OrderDto newOrder = new OrderDto();
        orderRepository.saveAndFlush(newOrder.toEntity());
        return newOrder;
    }

    @Override
    public OrderDto getOrderById(UUID uuid) {
        return orderRepository.getById(uuid).toModel();
    }

    @Override
    public void putItemToOrder(UUID orderId, UUID itemId, int amount) {
        OrderDto orderDto = getOrderById(orderId);
        for (OrderItemDto orderItem: orderDto.getItemList()) {
            if (orderItem.getCatalogItem().getUuid() == itemId) {
                orderItem.setAmount(amount);
                orderItemRepository.saveAndFlush(orderItem.toEntity());
                return;
            }
        }

        CatalogItemEntity catalogItem = catalogItemRepository.getById(itemId);
        OrderItemEntity newOrderItem = new OrderItemEntity(UUID.randomUUID(), catalogItem, amount);
        orderItemRepository.saveAndFlush(newOrderItem);
        orderDto.getItemList().add(newOrderItem.toModel());
        orderRepository.saveAndFlush(orderDto.toEntity());
    }

    @Override
    public Booking book(UUID orderId) throws IOException {
        OrderDto orderDto = getOrderById(orderId);

        URL url = new URL(API_URL + "warehouse/book");
        sendRequest(orderDto, url);

        return null;
    }

    @Override
    public void selectDeliveryTime(UUID orderId, int seconds) throws IOException {
        OrderDto orderDto = getOrderById(orderId);
        orderDto.setDeliveryInfo(new Timestamp(seconds));
        URL url = new URL(API_URL + "delivery/doDelivery");
        sendRequest(orderDto, url);
    }

    private void sendRequest(OrderDto orderDto, URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("order", orderDto.toString());

        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();
    }
}
