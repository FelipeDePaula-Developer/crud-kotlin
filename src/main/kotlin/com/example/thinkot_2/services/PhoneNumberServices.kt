package com.example.thinkot_2.services

import com.example.thinkot_2.entities.PhoneNumber
import com.example.thinkot_2.repositories.PhoneNumberRepository
import com.example.thinkot_2.repositories.UserRepository
import com.example.thinkot_2.services.interfaces.PhoneServicesInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PhoneNumberServices(@Autowired private val phoneNumberRepository: PhoneNumberRepository):PhoneServicesInterface {
    fun phoneNumberExists(phoneNumber: PhoneNumber) : Boolean{
        return phoneNumberRepository.existsByPhoneDDIAndPhoneDDDAndPhoneNumber(
            phoneNumber.phoneDDI ?: "",
            phoneNumber.phoneDDD ?: "",
            phoneNumber.phoneNumber ?: "")
    }
}