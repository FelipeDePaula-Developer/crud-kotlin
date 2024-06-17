package com.example.thinkot_2.forms

import com.example.thinkot_2.entities.Credential
import com.example.thinkot_2.entities.PhoneNumber
import org.springframework.stereotype.Component

@Component
data class UserForm(
    var name: String = "",
    var email: String = "",
    var cpf: String = "",
    var phone: List<PhoneNumber> = emptyList(),
    var credential: Credential? = null
)
