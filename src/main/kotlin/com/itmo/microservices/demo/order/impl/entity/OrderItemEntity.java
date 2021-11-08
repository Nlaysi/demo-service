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
    @OneToOne
    private CatalogItemEntity catalogItem;
    private Integer amount;

    public OrderItemEntity(UUID uuid, CatalogItemEntity catalogItem, Integer amount) {
        this.uuid = uuid;
        this.catalogItem = catalogItem;
        this.amount = amount;
    }

    public OrderItemDto toModel() {
        return new OrderItemDto(this.uuid, this.catalogItem.toModel(), this.amount);
    }
}