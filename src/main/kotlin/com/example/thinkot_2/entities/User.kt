package com.example.thinkot_2.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idUser: Int? = null,

    @Column
    var name: String? = null,

    @Column
    var email: String? = null,

    @Column(columnDefinition = "CHAR", length = 10)
    var cpf: String? = null,

    @Column(columnDefinition = "CHAR default 'T'", length = 1, nullable = false)
    var status: String? = "T",

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP", name = "insert_timestampp")
    @CreationTimestamp
    var insertTimestamp: LocalDateTime? = null
)