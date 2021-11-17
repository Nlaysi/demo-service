package com.itmo.microservices.demo.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto extends AbstractDto {
    private UUID id;
    private Set<UUID> failedItems = new HashSet<>();

    public BookingDto(UUID id) {
        this.id = id;
    }
}
