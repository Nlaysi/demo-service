package com.itmo.microservices.demo.warehouse.impl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class WarehouseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonBackReference
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private CatalogItem2 item;

    private Integer amount;
    private Integer booked;

    public WarehouseItem(CatalogItem2 item, Integer amount, Integer booked) {
        this.item = item;
        this.amount = amount;
        this.booked = booked;
    }

    public WarehouseItem() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CatalogItem2 getItem() {
        return item;
    }

    public void setItem(CatalogItem2 item) {
        this.item = item;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getBooked() {
        return booked;
    }

    public void setBooked(Integer booked) {
        this.booked = booked;
    }
}
