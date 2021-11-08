package com.itmo.microservices.demo.order.impl.dao;

import com.itmo.microservices.demo.order.impl.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {
}
