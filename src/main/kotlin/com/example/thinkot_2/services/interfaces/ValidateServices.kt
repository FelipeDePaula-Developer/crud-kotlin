package com.example.thinkot_2.services.interfaces

import java.util.regex.Pattern

interface ValidateServices {
    fun checkEmail(email: String?): Boolean {
        val regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
        return Pattern.matches(regexEmail, email)
    }

    fun checkCPF(cpf: String): Boolean {
        var cpf = cpf
        cpf = cpf.replace("\\D+".toRegex(), "")
        val regexCPF = "^\\d{11}$"
        return Pattern.matches(regexCPF, cpf)
    }

    fun checkPassword(password: String?): Boolean {
        val regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
        return Pattern.matches(regexPassword, password)
    }

    fun checkUsername(login: String?): Boolean {
        val regexLogin = "^[a-zA-Z0-9_-]{3,20}$"
        return Pattern.matches(regexLogin, login)
    }

    fun checkDDI(phoneDDI: String?): Boolean {
        val regexDDI = "^(?:\\d{1,4}\\s?)?\\(?\\d{1,4}\\)?\\s?\\d{6,}$"
        return Pattern.matches(regexDDI, phoneDDI)
    }

    fun checkDDD(phoneDDD: String?): Boolean {
        val regexDDD = "^(1[1-9]|[2-9][0-9])$"
        return Pattern.matches(regexDDD, phoneDDD)
    }

    fun checkNumber(phoneNumber: String?): Boolean {
        val regexNumber = "^[0-9]{1,20}$"
        return Pattern.matches(regexNumber, phoneNumber)
    }

    fun checkTypeNumber(typeNumber: String): Boolean {
        val types = ArrayList<String>()
        types.add("RE")
        types.add("CO")
        types.add("CE")
        types.add("CN")
        return types.contains(typeNumber)
    }
}