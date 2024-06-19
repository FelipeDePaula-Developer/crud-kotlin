package com.example.thinkot_2.services.interfaces

import com.example.thinkot_2.entities.Credential
import com.example.thinkot_2.entities.PhoneNumber
import com.example.thinkot_2.entities.User
import java.util.function.Consumer

interface UserServicesInterface:ValidateServices {

    fun validateUser(user: User): Map<String, String> {
        val emailCheck = checkEmail(user.email)
        val ret = mutableMapOf<String, String>()

        if (!emailCheck) {
            ret["email"] = "Email ${user.email} is invalid"
        }

        val cpfCheck = checkCPF(user.cpf)
        if (!cpfCheck) {
            ret["cpf"] = "CPF ${user.cpf} is invalid"
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

    fun validatePhones(phonesNumbers: List<PhoneNumber>): Map<String, String> {
        val ret = mutableMapOf<String, String>()
        phonesNumbers.forEach { phoneNumber ->
            val ddiCheck = checkDDI(phoneNumber.phoneDDI)
            if (!ddiCheck) {
                ret["ddi"] = "DDI ${phoneNumber.phoneDDI} is invalid"
            }

            val dddCheck = checkDDD(phoneNumber.phoneDDD)
            if (!dddCheck) {
                ret["ddd"] = "DDD ${phoneNumber.phoneDDD} is invalid"
            }

            val numberCheck = checkNumber(phoneNumber.phoneNumber)
            if (!numberCheck) {
                ret["phone_number"] = "Phone number ${phoneNumber.phoneNumber} is invalid"
            }

            val typeCheck = checkTypeNumber(phoneNumber.phoneType)
            if (!typeCheck) {
                ret["type_number"] = "Phone type ${phoneNumber.phoneType} is invalid"
            }
        }

        return ret
    }

}