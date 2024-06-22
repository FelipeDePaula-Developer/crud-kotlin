package com.example.thinkot_2.repositories

import com.example.thinkot_2.entities.Credential
import org.springframework.data.repository.CrudRepository

interface CredentialRepository : CrudRepository<Credential, Int>