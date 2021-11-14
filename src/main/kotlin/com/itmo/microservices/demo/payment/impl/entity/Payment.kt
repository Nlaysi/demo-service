package com.itmo.microservices.demo.payment.impl.entity

import com.itmo.microservices.demo.payment.api.model.PaymentStatus
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "payments")
class Payment {
    @Id
    @Column(name = "payment_id")
    var id: Int? = null

    @Column(name = "payment_status")
    var status: PaymentStatus? = null
    @Column(name = "order_id")
    var orderId: Int? = null

    constructor()

    constructor(id: Int?, status: PaymentStatus?, orderId: Int?) {
        this.id = id
        this.status = status
        this.orderId = orderId
    }
}