package com.itmo.microservices.demo.order.impl.entity;

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
public class CatalogItem {
    @Id
    @GeneratedValue
    private UUID uuid;

    private String title;
    private String description;
    private int price;
    private int amount;

    @ManyToOne
    private Order order;

    public CatalogItem(UUID randomUUID, String title, String description, int price, int amount) {
        this.uuid = randomUUID;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CatalogItem that = (CatalogItem) o;
        return uuid != null && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
