package com.example.thinkot_2.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "app_user")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idUser: Int? = null,

    @Column(length = 11)
    var cpf: String? = null,

    @Column
    var email: String? = null,

    @Column(name = "insert_timestamp", updatable = false)
    @CreationTimestamp
    var insertTimestamp: LocalDateTime? = null,

    @Column
    var name: String? = null,

    @Column(length = 1, nullable = false)
    var status: String = "T"
)
