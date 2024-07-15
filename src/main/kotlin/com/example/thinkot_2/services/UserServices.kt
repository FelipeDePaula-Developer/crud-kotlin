package com.example.thinkot_2.services

import com.example.thinkot_2.entities.User
import com.example.thinkot_2.forms.UserForm
import com.example.thinkot_2.forms.results.UserFormResult
import com.example.thinkot_2.repositories.UserRepository
import com.example.thinkot_2.services.interfaces.PhoneServicesInterface
import com.example.thinkot_2.services.interfaces.UserServicesInterface
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServices(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val phoneNumberServices: PhoneNumberServices,
    @Autowired private val credentialService: CredentialService
) : UserServicesInterface,
    PhoneServicesInterface {

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

        if (!credentialService.loginExists(userForm.credential)) {

            val retCredential: Map<String, String> = validateCredential(userForm.credential);
            retCredential.forEach() { (campo, erro) ->
                userFormResult.addUserError(campo, erro)
            }
        } else {
            userFormResult.addUserError("duplicate_login", "O login ${userForm.credential.login} já está em uso")
        }


        return userFormResult;
    }

    @Transactional
    fun saveOrUpdateByCpf(user: User) {
        val existingUser = user.cpf?.let { userRepository.findByCpf(it) }
        if (existingUser != null) {
            existingUser.name = user.name
            existingUser.email = user.email
            existingUser.status = user.status
            userRepository.save(existingUser)
        } else {
            userRepository.save(user)
        }
    }
}