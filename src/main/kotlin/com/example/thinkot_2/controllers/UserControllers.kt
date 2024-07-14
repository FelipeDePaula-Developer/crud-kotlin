package com.example.thinkot_2.controllers

import com.example.thinkot_2.forms.UserForm
import com.example.thinkot_2.forms.results.UserFormResult
import com.example.thinkot_2.repositories.CredentialRepository
import com.example.thinkot_2.repositories.PhoneNumberRepository
import com.example.thinkot_2.repositories.UserRepository
import com.example.thinkot_2.services.UserServices
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserControllers(private val userServices: UserServices, private val userRepository: UserRepository, private val credentialRepository: CredentialRepository, private val phoneNumberRepository: PhoneNumberRepository) {
    @PostMapping("cad/user")
    fun cadUser(@RequestBody userForm: UserForm): ResponseEntity<Any> {
        val userFormResult: UserFormResult = userServices.validateUserForm(userForm);

        return if (userFormResult.hasErrors()){
            ResponseEntity(userFormResult.getAllErrors(), HttpStatus.BAD_REQUEST)
        }else{
            userRepository.saveOrUpdateByCPF(userForm.user)
            credentialRepository.save(userForm.credential)
            phoneNumberRepository.saveAll(userForm.phone)
            ResponseEntity("Validation passed", HttpStatus.OK)
        }
    }
}