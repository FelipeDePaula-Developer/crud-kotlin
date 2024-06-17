package com.example.thinkot_2.entities

import jakarta.persistence.*

data class Credential(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idCredential : Int? = null,

    @Column
    var login: String? = null,

    @Column
    var password: String? = null,

    @Column(columnDefinition = "CHAR default 'T'", length = 1, nullable = false)
    var status: String? = "T",

    @Column(columnDefinition = "CHAR default 'F'", length = 1, nullable = false)
    var logged: String? = null,

    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    var user: User? = null,

    @ManyToOne
    @JoinColumn(name = "idclient", referencedColumnName = "idclient")
    val client: Client? = null
)