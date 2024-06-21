package com.example.thinkot_2.controllers

import com.example.thinkot_2.forms.UserForm
import com.example.thinkot_2.forms.results.UserFormResult
import com.example.thinkot_2.services.UserServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserControllers(private val userServices: UserServices) {
    @PostMapping("cad/user")
    fun cadUser(@RequestBody userForm: UserForm): ResponseEntity<Any> {
        println("RECEBIDO");
        var userFormResult: UserFormResult = userServices.validateUserForm(userForm);
        if (userFormResult.hasErrors()){
            println("ERRO");
            return ResponseEntity(userFormResult.getAllErrors(), HttpStatus.BAD_REQUEST)
        }else{
            return ResponseEntity("Validation passed", HttpStatus.OK)
        }

    }
}