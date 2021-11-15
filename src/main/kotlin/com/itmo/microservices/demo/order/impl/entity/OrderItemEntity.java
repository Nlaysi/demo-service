package com.itmo.microservices.demo.order.impl.entity;

import lombok.*;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderItemEntity extends AbstractEntity {
    private UUID catalogItemId;
    private Integer amount;

    public OrderItemEntity(UUID uuid, UUID catalogItemId, Integer amount) {
        super(uuid);
        this.catalogItemId = catalogItemId;
        this.amount = amount;
    }
}