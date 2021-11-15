package com.itmo.microservices.demo.delivery.api.messaging

import com.itmo.microservices.demo.delivery.api.model.DeliveryModel
import com.itmo.microservices.demo.delivery.api.model.SlotsModel
import java.util.*

data class DeliveryCreatedEvent(val user: DeliveryModel)

data class DeliveryDeletedEvent(val id: UUID)

data class SlotsCreatedEvent(val slots: SlotsModel)