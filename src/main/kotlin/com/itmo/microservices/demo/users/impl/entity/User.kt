package com.itmo.microservices.demo.users.impl.entity

import com.itmo.microservices.demo.users.api.model.Status
import javax.persistence.*

@Entity
@Table(name="users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="user_id")
    var id: Int? = null
    @Column(name="user_name")
    var name: String? = null
    @Column(name="user_password")
    var password: String? = null
    @Column(name="user_status")
    var status: Status? = null

    constructor()

    constructor(name: String?, password: String?, status: Status?) {
        this.name = name
        this.password = password
        this.status = status
    }
}