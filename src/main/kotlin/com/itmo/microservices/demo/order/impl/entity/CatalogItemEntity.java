package com.itmo.microservices.demo.order.impl.entity;

import com.itmo.microservices.demo.order.api.dto.CatalogItemDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CatalogItemEntity {
    @Id
    private UUID uuid;

    private String title;
    private String description;
    private int price;
    private int amount;

    @ManyToOne
    private OrderEntity order;

    public CatalogItemEntity(UUID randomUUID, String title, String description, int price, int amount) {
        this.uuid = randomUUID;
        this.title = title;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CatalogItemEntity that = (CatalogItemEntity) o;
        return uuid != null && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public CatalogItemDto toModel() {
        return new CatalogItemDto(this.uuid, this.title, this.description, this.price, this.amount);
    }
}
