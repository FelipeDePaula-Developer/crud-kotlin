package com.example.thinkot_2.services

import com.example.thinkot_2.entities.Credential
import com.example.thinkot_2.repositories.CredentialRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CredentialService(@Autowired private val credentialRepository: CredentialRepository) {
    fun loginExists(credential: Credential): Boolean {
        return credentialRepository.existsByLogin(credential.login);
    }
}