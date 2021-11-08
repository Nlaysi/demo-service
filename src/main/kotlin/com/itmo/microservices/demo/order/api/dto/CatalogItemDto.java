package com.itmo.microservices.demo.order.api.dto;

import com.itmo.microservices.demo.order.impl.entity.CatalogItemEntity;
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
public class CatalogItemDto {
    private UUID uuid;
    private String title;
    private String description;
    private int price;
    private int amount;

    public CatalogItemEntity toEntity() {
        return new CatalogItemEntity(this.uuid, this.title, this.description, this.price, this.amount);
    }
}
