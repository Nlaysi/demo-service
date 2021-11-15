package com.itmo.microservices.demo.order.impl.entity;

import com.itmo.microservices.demo.order.api.dto.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@Table(name = "orders")
public class OrderEntity extends AbstractEntity {
    private LocalDateTime timeCreated;
    private OrderStatus status;

    private Timestamp deliveryInfo;
    @OneToMany
    @ToString.Exclude
    private List<OrderItemEntity> orderItems = new ArrayList<>();
}
