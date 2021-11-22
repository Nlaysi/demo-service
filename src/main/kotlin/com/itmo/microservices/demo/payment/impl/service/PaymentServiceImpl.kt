package com.itmo.microservices.demo.payment.impl.service

import com.itmo.microservices.demo.order.api.dto.OrderDto
import com.itmo.microservices.demo.order.impl.entity.OrderEntity
import com.itmo.microservices.demo.payment.api.model.PaymentModel
import com.itmo.microservices.demo.payment.api.model.PaymentStatus
import com.itmo.microservices.demo.payment.api.model.PaymentSubmissionDto
import com.itmo.microservices.demo.payment.api.service.PaymentService
import com.itmo.microservices.demo.payment.impl.entity.Payment
import com.itmo.microservices.demo.payment.impl.repository.PaymentRepository
import com.itmo.microservices.demo.warehouse.impl.repository.CatalogItemRepository
import com.itmo.microservices.demo.warehouse.impl.repository.WarehouseItemRepository
import org.springframework.stereotype.Service

@Service
class PaymentServiceImpl(private val paymentRepository: PaymentRepository, private val catalogItemRepository: CatalogItemRepository): PaymentService {
    override fun executePayment(order: OrderDto): PaymentSubmissionDto {
        val payment = Payment(PaymentStatus.SUCCESS, order.uuid)
        val transactionId = paymentRepository.save(payment).id

        var sum = 0.0
        order.orderItems.forEach { sum += catalogItemRepository.findCatalogItemById(it.uuid)!!.price }

        return PaymentSubmissionDto(System.currentTimeMillis(), transactionId, sum)
    }
}