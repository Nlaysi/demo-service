package com.itmo.microservices.demo.warehouse.api.controller

import com.itmo.microservices.demo.warehouse.api.exception.ItemIsNotExistException
import com.itmo.microservices.demo.warehouse.api.exception.ItemQuantityException
import com.itmo.microservices.demo.warehouse.impl.service.WarehouseService
import com.itmo.microservices.demo.warehouse.api.model.ItemQuantityRequestDTO
import com.itmo.microservices.demo.warehouse.api.model.ItemResponseDTO
import com.itmo.microservices.demo.warehouse.api.model.WarehouseItemDTO
import org.springframework.http.ResponseEntity
import com.itmo.microservices.demo.warehouse.impl.entity.CatalogItem
import com.itmo.microservices.demo.warehouse.impl.entity.WarehouseItem
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/warehouse")
class WarehouseController(private val service: WarehouseService) {

    @PutMapping(
        value = ["/income"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    @Operation(
        summary = "Execute a request to income items",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun income(@Valid @RequestBody items: List<@Valid ItemQuantityRequestDTO>): ResponseEntity<ItemResponseDTO> {
        try {
            service.checkAllItems(items)

            for (item in items) {
                service.income(item)
            }
        }
        catch (e: ItemIsNotExistException) {
            return ResponseEntity(ItemResponseDTO(404, e.message!!), HttpStatus.NOT_FOUND)
        }
        catch (e: Exception) {
            return ResponseEntity(ItemResponseDTO(400, e.message!!), HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(ItemResponseDTO(200, "Request executed successfully"), HttpStatus.OK)
    }

    @PutMapping(
        value = ["/outcome"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    @Operation(
        summary = "Execute a request to outcome items",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun outcome(@Valid @RequestBody items: List<@Valid ItemQuantityRequestDTO>): ResponseEntity<ItemResponseDTO> {
        try {
            service.checkAllItems(items)
            service.checkAllQuantity(items)

            for (item in items) {
                service.outcome(item)
            }
        }
        catch (e: ItemQuantityException) {
            return ResponseEntity(ItemResponseDTO(400, e.message!!), HttpStatus.NOT_FOUND)
        }
        catch (e: ItemIsNotExistException) {
            return ResponseEntity(ItemResponseDTO(404, e.message!!), HttpStatus.NOT_FOUND)
        }
        catch (e: Exception) {
            return ResponseEntity(ItemResponseDTO(400, e.message!!), HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(ItemResponseDTO(200, "Request executed successfully"), HttpStatus.OK)
    }

    @PostMapping(
        value = ["/book"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    @Operation(
        summary = "Execute a request to book items",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun book(@Valid @RequestBody items: List<@Valid ItemQuantityRequestDTO>): ResponseEntity<ItemResponseDTO> {
        try {
            service.checkAllItems(items)
            service.checkAllQuantity(items)

            for (item in items) {
                service.book(item)
            }
        }
        catch (e: ItemQuantityException) {
            return ResponseEntity(ItemResponseDTO(400, e.message!!), HttpStatus.NOT_FOUND)
        }
        catch (e: ItemIsNotExistException) {
            return ResponseEntity(ItemResponseDTO(404, e.message!!), HttpStatus.NOT_FOUND)
        }
        catch (e: Exception) {
            return ResponseEntity(ItemResponseDTO(400, e.message!!), HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(ItemResponseDTO(200, "Request executed successfully"), HttpStatus.OK)
    }

    @PostMapping(
        value = ["/unbook"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    @Operation(
        summary = "Execute a request to unbook items",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun unbook(@Valid @RequestBody items: List<@Valid ItemQuantityRequestDTO>): ResponseEntity<ItemResponseDTO> {
        try {
            service.checkAllItems(items)
            service.checkAllBookQuantity(items)

            for (item in items) {
                service.unbook(item)
            }
        }
        catch (e: ItemQuantityException) {
            return ResponseEntity(ItemResponseDTO(400, e.message!!), HttpStatus.NOT_FOUND)
        }
        catch (e: ItemIsNotExistException) {
            return ResponseEntity(ItemResponseDTO(404, e.message!!), HttpStatus.NOT_FOUND)
        }
        catch (e: Exception) {
            return ResponseEntity(ItemResponseDTO(400, e.message!!), HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(ItemResponseDTO(200, "Request executed successfully"), HttpStatus.OK)
    }

    @PostMapping(
        value = ["/addItem"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    @Operation(
        summary = "Execute a request to add new item",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun addItem(@Valid @RequestBody item: CatalogItem): ResponseEntity<ItemResponseDTO> {
        try {
            service.addItem(item)
        }
        catch (e: Exception) {
            return ResponseEntity(ItemResponseDTO(400, e.message!!), HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(ItemResponseDTO(200, "Request executed successfully"), HttpStatus.OK)
    }

    @GetMapping("/getItems")
    @Operation(
        summary = "Get a list of all added items",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getItems(): ResponseEntity<List<CatalogItem?>> {
        val list: List<CatalogItem?>
        try {
            list = service.getItems()
        }
        catch (e: Exception) {
            return ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(list, HttpStatus.OK)
    }

    @GetMapping("/getItem")
    @Operation(
        summary = "Get a item by id",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getItem(@RequestParam("id") id: UUID?): ResponseEntity<CatalogItem> {
        val item: CatalogItem
        try {
            item = service.getItem(id)
        }
        catch (e: ItemIsNotExistException) {
            return ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
        catch (e: Exception) {
            return ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(item, HttpStatus.OK)
    }

    @GetMapping(value = ["/getItemQuantity"])
    @Operation(
        summary = "Get a item quantity by id",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getItemQuantity(@RequestParam("id") id: UUID?): ResponseEntity<WarehouseItemDTO> {
        val itemModel: WarehouseItemDTO
        try {
            val item: WarehouseItem = service.getItemQuantity(id)
            itemModel = WarehouseItemDTO(id!!, item.amount!!, item.booked!!)
        }
        catch (e: ItemIsNotExistException) {
            return ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
        catch (e: Exception) {
            return ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(itemModel, HttpStatus.OK)
    }
}