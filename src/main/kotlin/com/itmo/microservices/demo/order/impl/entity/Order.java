package com.itmo.microservices.demo.order.impl.entity;

import com.itmo.microservices.demo.order.api.dto.OrderStatus;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    private UUID uuid;

    private LocalDateTime timeCreated;
    private OrderStatus status;

    private Timestamp deliveryInfo;
    @OneToMany
    @ToString.Exclude
    private List<OrderItem> catalogItems;

    public Order(
            UUID uuid,
            LocalDateTime timeCreated,
            OrderStatus status,
            Timestamp deliveryInfo,
            List<OrderItem> catalogItems
    ) {
        this.uuid = uuid;
        this.timeCreated = timeCreated;
        this.status = status;
        this.deliveryInfo = deliveryInfo;
        this.catalogItems = catalogItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return uuid != null && Objects.equals(uuid, order.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<OrderItem> getCatalogItems() {
        return catalogItems;
    }
}
