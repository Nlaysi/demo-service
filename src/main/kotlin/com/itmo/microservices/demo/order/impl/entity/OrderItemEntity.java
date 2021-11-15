package com.itmo.microservices.demo.order.impl.entity;

import com.itmo.microservices.demo.order.api.dto.OrderItemDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class OrderItemEntity {
    @Id
    private UUID uuid;
    private UUID catalogItemId;
    private Integer amount;

    public OrderItemEntity(UUID uuid, UUID catalogItem, Integer amount) {
        this.uuid = uuid;
        this.catalogItemId = catalogItem;
        this.amount = amount;
    }

    public OrderItemDto toModel() {
        return new OrderItemDto(this.uuid, this.catalogItemId, this.amount);
    }
}