package com.example.thinkot_2.repositories

import com.example.thinkot_2.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int>{
    fun saveOrUpdateByCPF(user: User){
        var cpf = user.cpf;
    }
}