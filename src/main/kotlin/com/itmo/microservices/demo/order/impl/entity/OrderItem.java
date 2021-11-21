package com.itmo.microservices.demo.order.impl.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class OrderItem {
    public OrderItem(UUID uuid, String title, int price) {
        this.uuid = uuid;
        this.title = title;
        this.price = price;
    }

    @Id
    @GeneratedValue
    private UUID uuid;

    private String title;
    private int price;

    public UUID getUUID() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }
}