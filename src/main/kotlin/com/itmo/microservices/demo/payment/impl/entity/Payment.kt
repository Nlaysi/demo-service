package com.itmo.microservices.demo.payment.impl.entity

import com.itmo.microservices.demo.payment.api.model.PaymentStatus
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Payment {
    @Id
    var id: Int = 0

    lateinit var status: PaymentStatus
    lateinit var orderId: UUID

    constructor()

    constructor(status: PaymentStatus, orderId: UUID) {
        this.status = status
        this.orderId = orderId
    }
}