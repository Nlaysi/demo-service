package com.itmo.microservices.demo.order.api.controller;

import com.itmo.microservices.demo.order.impl.service.CatalogItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    private final CatalogItemService service;

    @Autowired
    public CatalogController(CatalogItemService service) {
        this.service = service;
    }

    @PostMapping("{title}/{description}/{price}/{amount}")
    @Operation(
            summary = "Add catalogItem",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {@Content}),
                    @ApiResponse(description = "Something went wrong", responseCode = "400", content = {@Content})},
            security = {@SecurityRequirement(name = "bearerAuth")})
    public void addCatalogItem(@PathVariable("title") String title,
                               @PathVariable("description") String description,
                               @PathVariable("price") int price,
                               @PathVariable("amount") int amount) {
        service.addItem(title, description, price, amount);
    }
}
