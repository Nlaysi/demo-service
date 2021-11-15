package com.itmo.microservices.demo.order.impl.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AbstractEntity that = (AbstractEntity) o;
        return uuid != null && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
