package com.itmo.microservices.demo.warehouse.api.model

import java.util.UUID
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class ItemQuantityRequestDTO(
    @field:NotNull(message = "ID must be not null")
    var id: UUID,

    @field:NotNull(message = "Amount must be not null")
    @field:Min(value = 1, message = "Value must be greater than 0")
    var amount: Int
)
