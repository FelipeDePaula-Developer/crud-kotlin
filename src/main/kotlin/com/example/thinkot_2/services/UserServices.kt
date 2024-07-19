package com.example.thinkot_2.services

import com.example.thinkot_2.entities.User
import com.example.thinkot_2.forms.UserForm
import com.example.thinkot_2.forms.results.UserFormResult
import com.example.thinkot_2.repositories.CredentialRepository
import com.example.thinkot_2.repositories.PhoneNumberRepository
import com.example.thinkot_2.repositories.UserRepository
import com.example.thinkot_2.services.interfaces.PhoneServicesInterface
import com.example.thinkot_2.services.interfaces.UserServicesInterface
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserServices(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val phoneNumberServices: PhoneNumberServices,
    @Autowired private val credentialServices: CredentialServices,
    private val phoneNumberRepository: PhoneNumberRepository,
) : UserServicesInterface,
    PhoneServicesInterface {

    @Transactional
    fun registerUser(userForm: UserForm): UserFormResult {
        val userFormResult: UserFormResult = validateUserForm(userForm)

        if (userFormResult.hasErrors()) {
            return userFormResult
        }

        val savedUser = saveOrUpdateByCpf(userForm.user)
        userForm.phone.forEach() { phone ->
            phone.user = savedUser
        }
        phoneNumberRepository.saveAll(userForm.phone)

        userForm.credential.user = savedUser
        credentialServices.saveCredential(userForm.credential)

        return userFormResult
    }

    fun validateUserForm(userForm: UserForm): UserFormResult {
        val userFormResult = UserFormResult();

        val retUser: Map<String, String> = validateUser(userForm.user);

        retUser.forEach() { (campo, erro) ->
            userFormResult.addUserError(campo, erro)
        }

        var skipPhoneValidation = false;
        userForm.phone.forEach { phoneNumber ->
            if (phoneNumberServices.phoneNumberExists(phoneNumber)) {
                userFormResult.addUserError(
                    "duplicate_number",
                    "Combinação de DDI (${phoneNumber.phoneDDI}), DDD (${phoneNumber.phoneDDD}) e Numero ${phoneNumber.phoneNumber} já existem na base de dados"
                )
                skipPhoneValidation = true;
            }
        }

        if (!skipPhoneValidation) {
            val retPhones: Map<String, String> = validatePhones(userForm.phone);
            retPhones.forEach() { (campo, erro) ->
                userFormResult.addUserError(campo, erro)
            }
        }

        if (!credentialServices.loginExists(userForm.credential)) {

            val retCredential: Map<String, String> = validateCredential(userForm.credential);
            retCredential.forEach() { (campo, erro) ->
                userFormResult.addUserError(campo, erro)
            }
        } else {
            userFormResult.addUserError("duplicate_login", "O login ${userForm.credential.login} já está em uso")
        }


        return userFormResult;
    }

    fun saveOrUpdateByCpf(user: User) : User {
        val existingUser = user.cpf?.let { userRepository.findByCpf(it) }
        val savedUser : User;
        if (existingUser != null) {
            existingUser.name = user.name
            existingUser.email = user.email
            existingUser.status = user.status
           savedUser = userRepository.save(existingUser)
        } else {
           savedUser = userRepository.save(user)
        }

        return savedUser
    }
}