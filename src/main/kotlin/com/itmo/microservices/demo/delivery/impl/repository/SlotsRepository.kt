package com.itmo.microservices.demo.delivery.impl.repository

import com.itmo.microservices.demo.delivery.impl.entity.Slots
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SlotsRepository: JpaRepository<Slots, String> {
}