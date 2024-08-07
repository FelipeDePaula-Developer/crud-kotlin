package com.example.thinkot_2.controllers

import com.example.thinkot_2.forms.AuthUserForm
import com.example.thinkot_2.forms.UserForm
import com.example.thinkot_2.forms.results.PersonFormResult
import com.example.thinkot_2.services.CredentialServices
import com.example.thinkot_2.services.UserServices
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userServices: UserServices, private val authServices: CredentialServices) {
    @PostMapping("cad/user")
    fun cadUser(@RequestBody userForm: UserForm): ResponseEntity<Any> {
        val personFormResult: PersonFormResult = userServices.registerUser(userForm);

        return if (personFormResult.hasErrors()){
            ResponseEntity(personFormResult.getAllErrors(), HttpStatus.BAD_REQUEST)
        }else{
            ResponseEntity("Validation passed", HttpStatus.OK)
        }
    }

    @PostMapping("cad/client")
    fun cadClient(@RequestBody userForm: UserForm): ResponseEntity<Any> {
        val personFormResult: PersonFormResult = userServices.registerUser(userForm);

        return if (personFormResult.hasErrors()){
            ResponseEntity(personFormResult.getAllErrors(), HttpStatus.BAD_REQUEST)
        }else{
            ResponseEntity("Validation passed", HttpStatus.OK)
        }
    }

    @PostMapping("auth/user")
    fun authUser(@RequestBody authUserForm: AuthUserForm): ResponseEntity<Any> {
       return if (authServices.authenticate(authUserForm)){
            ResponseEntity("Logged", HttpStatus.OK)
        }else{
            ResponseEntity("Login Fail", HttpStatus.BAD_REQUEST)
        }
    }
}