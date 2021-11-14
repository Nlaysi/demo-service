package com.itmo.microservices.demo.delivery.impl.entity

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Slots {
    @Id
    var slotsDate: String? = null
    @ElementCollection
    var deliveryMen: MutableList<Int>? = null
    @ElementCollection
    var timeSlots: MutableList<String>? = null

    constructor()

    constructor(slotsDate: String?,
                deliveryMen: MutableList<Int>?,
                timeSlots: MutableList<String>?
    ) {
        this.slotsDate = slotsDate
        this.deliveryMen = deliveryMen
        this.timeSlots = timeSlots
    }
}