package com.itmo.microservices.demo.users.impl.entity

import com.itmo.microservices.demo.users.api.model.Status
import javax.persistence.*

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null
    var name: String? = null
    var password: String? = null
    var status: Status? = null

    constructor()

    constructor(name: String?, password: String?, status: Status?) {
        this.name = name
        this.password = password
        this.status = status
    }
}