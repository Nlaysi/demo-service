package com.itmo.microservices.demo.order.api.dto;

import lombok.*;
import org.springframework.context.annotation.Lazy;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Lazy
public class OrderItemDto extends AbstractDto {
    private UUID uuid;
    private UUID catalogItemId;
    private Integer amount;
}
