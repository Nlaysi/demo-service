package com.itmo.microservices.demo.order.util.mapping;


import com.itmo.microservices.demo.order.api.dto.AbstractDto;
import com.itmo.microservices.demo.order.impl.entity.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);

    D toDto(E entity);
}
