package com.itmo.microservices.demo.order.api.dto;

import com.itmo.microservices.demo.order.impl.entity.OrderItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Lazy
public class OrderItemDto {
    private UUID uuid;
    private UUID catalogItemId;
    private Integer amount;

    public OrderItemEntity toEntity() {
        return new OrderItemEntity(this.uuid, this.catalogItemId, this.amount);
    }
}
