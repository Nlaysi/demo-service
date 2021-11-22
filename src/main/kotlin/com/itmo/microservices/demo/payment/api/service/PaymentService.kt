package com.itmo.microservices.demo.payment.api.service

import com.itmo.microservices.demo.order.api.dto.OrderDto
import com.itmo.microservices.demo.payment.api.model.PaymentModel
import com.itmo.microservices.demo.payment.api.model.PaymentSubmissionDto
import org.springframework.stereotype.Service

@Service
interface PaymentService {
    fun executePayment(order: OrderDto): PaymentSubmissionDto
}