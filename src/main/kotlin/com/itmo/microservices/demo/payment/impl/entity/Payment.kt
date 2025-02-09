package com.itmo.microservices.demo.payment.impl.entity

import com.itmo.microservices.demo.payment.api.model.PaymentStatus
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Payment {
    @Id
    var id: Int? = null

    var status: PaymentStatus? = null
    var orderId: Int? = null
}