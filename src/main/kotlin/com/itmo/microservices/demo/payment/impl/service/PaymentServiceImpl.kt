package com.itmo.microservices.demo.payment.impl.service

import com.itmo.microservices.demo.order.impl.entity.Order
import com.itmo.microservices.demo.payment.api.model.PaymentModel
import com.itmo.microservices.demo.payment.api.model.PaymentStatus
import com.itmo.microservices.demo.payment.api.model.PaymentSubmissionDto
import com.itmo.microservices.demo.payment.api.service.PaymentService
import com.itmo.microservices.demo.payment.impl.entity.Payment
import com.itmo.microservices.demo.payment.impl.repository.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentServiceImpl(private val paymentRepository: PaymentRepository): PaymentService {
    override fun executePayment(order: Order): PaymentSubmissionDto {
        val payment = Payment(PaymentStatus.SUCCESS, order.uuid)
        val transactionId = paymentRepository.save(payment).id

        var sum = 0.0
        order.catalogItems.forEach { sum += it.price }

        return PaymentSubmissionDto(System.currentTimeMillis(), transactionId, sum)
    }
}