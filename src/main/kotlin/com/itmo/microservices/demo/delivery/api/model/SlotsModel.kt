package com.itmo.microservices.demo.delivery.api.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SlotsModel(
    var slotsDate: String?,
    var deliveryMen: MutableList<Int>?,
    var timeSlots: MutableList<String>?
)