package com.itmo.microservices.demo.warehouse.impl.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.itmo.microservices.demo.warehouse.impl.entity.WarehouseItem
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.QueryHints
import java.util.UUID
import javax.persistence.LockModeType
import javax.persistence.QueryHint

interface WarehouseItemRepository : JpaRepository<WarehouseItem?, UUID?> {
    override fun existsById(uuid: UUID): Boolean

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints(value = [QueryHint(name = "javax.persistence.lock.timeout", value = "1000")])
    fun findWarehouseItemById(uuid: UUID?): WarehouseItem?
}