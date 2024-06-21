package com.example.thinkot_2.forms

import com.example.thinkot_2.entities.Credential
import com.example.thinkot_2.entities.PhoneNumber
import com.example.thinkot_2.entities.User
import org.springframework.stereotype.Component

@Component
data class UserForm(
    var user: User = User(),
    var phone: List<PhoneNumber> = emptyList(),
    var credential: Credential = Credential()
)