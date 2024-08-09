package com.example.thinkot_2.services.interfaces

import com.example.thinkot_2.entities.Credential
import com.example.thinkot_2.entities.PhoneNumber
import com.example.thinkot_2.entities.User
import com.example.thinkot_2.entities.interfaces.Person
import java.util.function.Consumer

interface UserServicesInterface:ValidateServices {

    fun validatePerson(person: Person): Map<String, String> {
        val emailCheck = checkEmail(person.email)
        val ret = mutableMapOf<String, String>()

        if (!emailCheck) {
            ret["email"] = "Email ${person.email} is invalid"
        }

        val cpfCheck = checkCPF(person.cpf)
        if (!cpfCheck) {
            ret["cpf"] = "CPF ${person.cpf} is invalid"
        }

        return ret
    }

    fun validateCredential(credential: Credential): Map<String, String> {
        val ret = mutableMapOf<String, String>()

        val usernameCheck = checkUsername(credential.login)
        if (!usernameCheck) {
            ret["login"] = "Login ${credential.login} is invalid"
        }

        val passwordCheck = checkPassword(credential.password)
        if (!passwordCheck) {
            ret["password"] = "Password ${credential.password} is invalid"
        }

        return ret
    }

}