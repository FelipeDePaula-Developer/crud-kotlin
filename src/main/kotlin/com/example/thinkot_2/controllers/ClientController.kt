package com.example.thinkot_2.controllers

import com.example.thinkot_2.forms.AuthUserForm
import com.example.thinkot_2.forms.ClientForm
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
class ClientController(private val userServices: UserServices) {
    @PostMapping("cad/client")
    fun cadClient(@RequestBody clientForm : ClientForm): ResponseEntity<Any> {
        val personFormResult: PersonFormResult = userServices.registerClient(clientForm);

        return if (personFormResult.hasErrors()){
            ResponseEntity(personFormResult.getAllErrors(), HttpStatus.BAD_REQUEST)
        }else{
            ResponseEntity("Validation passed", HttpStatus.OK)
        }
    }
}