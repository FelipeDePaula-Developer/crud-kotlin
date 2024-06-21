package com.example.thinkot_2.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
data class Client(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idClient: Int? = null,

    @Column
    var name: String? = null,

    @Column
    var email: String? = null,

    @Column(length = 10)
    var cpf: String? = null,

    @Column(columnDefinition = "CHAR(1) default 'T'", nullable = false)
    var status: String = "T",

    @Column(name = "insert_timestampp", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @CreationTimestamp
    var insertTimestamp: LocalDateTime? = null
)