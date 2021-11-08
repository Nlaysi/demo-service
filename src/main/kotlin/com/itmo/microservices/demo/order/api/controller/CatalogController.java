package com.itmo.microservices.demo.order.api.controller;

import com.itmo.microservices.demo.order.api.dto.CatalogItemDto;
import com.itmo.microservices.demo.order.impl.service.CatalogItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    private final CatalogItemService service;

    @Autowired
    public CatalogController(CatalogItemService service) {
        this.service = service;
    }

    @PostMapping("")
    @Operation(
            summary = "Add catalogItem",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {@Content}),
                    @ApiResponse(description = "Something went wrong", responseCode = "400", content = {@Content})},
            security = {@SecurityRequirement(name = "bearerAuth")})
    public CatalogItemDto addCatalogItem(@RequestParam(value = "title", defaultValue = "") String title,
                                         @RequestParam(value = "description", defaultValue = "") String description,
                                         @RequestParam(value = "price", defaultValue = "0") int price,
                                         @RequestParam(value = "amount", defaultValue = "0") int amount) {
        return service.addItem(title, description, price, amount);
    }

    @GetMapping
    @Operation(
            summary = "Get all catalog items",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {@Content}),
                    @ApiResponse(description = "Something went wrong", responseCode = "400", content = {@Content})},
            security = {@SecurityRequirement(name = "bearerAuth")})
    public List<CatalogItemDto> getCatalogItems() {
        return service.getAllItems();
    }
}
