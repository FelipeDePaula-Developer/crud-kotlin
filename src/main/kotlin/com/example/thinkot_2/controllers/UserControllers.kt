package com.example.thinkot_2.controllers

import com.example.thinkot_2.forms.UserForm
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserControllers {


    @PostMapping("cad/user")
    public fun cadUser(@RequestBody UserForm:UserForm){
        try {

        }catch (_:Exception){

        }
    }
}