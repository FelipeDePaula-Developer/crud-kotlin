package com.example.thinkot_2.repositories

import com.example.thinkot_2.entities.Credential
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CredentialRepository : CrudRepository<Credential, Int>{
     @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END from Credential c where c.login = :login")
     fun existsByLogin(login:String?) : Boolean

     @Query("SELECT c FROM Credential c WHERE c.login = :login")
     fun findByLogin(login: String?): Credential?
}