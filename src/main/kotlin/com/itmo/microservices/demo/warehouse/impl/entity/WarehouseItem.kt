package com.itmo.microservices.demo.warehouse.impl.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "warehouse_items")
class WarehouseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonBackReference
    var id: UUID? = null

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    var item: CatalogItem? = null
    var amount: Int? = null
    var booked: Int? = null

    constructor(item: CatalogItem?, amount: Int?, booked: Int?) {
        this.item = item
        this.amount = amount
        this.booked = booked
    }

    constructor() {}
}