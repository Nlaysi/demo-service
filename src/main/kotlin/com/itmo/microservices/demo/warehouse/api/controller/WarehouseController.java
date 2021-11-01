package com.itmo.microservices.demo.warehouse.api.controller;

import com.itmo.microservices.demo.warehouse.api.model.ItemQuantityChangeRequest;
import com.itmo.microservices.demo.warehouse.impl.entity.CatalogItem2;
import com.itmo.microservices.demo.warehouse.impl.entity.WarehouseItem;
import com.itmo.microservices.demo.warehouse.impl.service.WarehouseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/warehouse")
@SecurityRequirement(name = "bearerAuth")
public class WarehouseController {

    private final WarehouseService service;

    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @PostMapping(value = "/income", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> income(@RequestBody ItemQuantityChangeRequest values) {
        return service.income(values);
    }

    @PostMapping(value = "/outcome", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> outcome(@RequestBody ItemQuantityChangeRequest values) {
        return service.outcome(values);
    }

    @PostMapping(value = "/book", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> book(@RequestBody ItemQuantityChangeRequest values) {
        return service.book(values);
    }

    @PostMapping(value = "/unbook", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> unbook(@RequestBody ItemQuantityChangeRequest values) {
        return service.unbook(values);
    }

    @PostMapping(value = "/addItem", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addItem(@RequestBody CatalogItem2 item) {
        return service.addItem(item);
    }

    @GetMapping(value = "/getItems")
    public ResponseEntity<List<CatalogItem2>> getItemsList() {
        System.out.println();
        return service.getItemsList();
    }

    @GetMapping(value = "/getItem")
    public ResponseEntity<CatalogItem2> getItem(@RequestParam("id") UUID id) {
        return service.getItem(id);
    }

    @GetMapping(value = "/getItemQuantity")
    public ResponseEntity<WarehouseItem> getItemQuantity(@RequestParam("id") UUID id) {
        return service.getItemQuantity(id);
    }
}
