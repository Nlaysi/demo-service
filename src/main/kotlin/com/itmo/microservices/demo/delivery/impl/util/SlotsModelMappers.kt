package com.itmo.microservices.demo.delivery.impl.util

import com.itmo.microservices.demo.delivery.api.model.SlotsModel
import com.itmo.microservices.demo.delivery.impl.entity.Slots


fun Slots.toModel(): SlotsModel = kotlin.runCatching {
    SlotsModel(
        slotsDate = this.slotsDate,
        deliveryMen = this.deliveryMen,
        timeSlots = this.timeSlots,
    )
}.getOrElse { exception -> throw IllegalStateException("Some of delivery slots fields are null", exception) }