package com.itmo.microservices.demo.warehouse.api.model

import java.util.*

data class WarehouseItemDTO(
    var id: UUID,
    val amount: Int,
    val booked: Int
)
