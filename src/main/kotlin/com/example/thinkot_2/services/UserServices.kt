package com.example.thinkot_2.services

import com.example.thinkot_2.entities.User
import com.example.thinkot_2.forms.UserForm
import com.example.thinkot_2.forms.results.UserFormResult
import com.example.thinkot_2.repositories.UserRepository
import com.example.thinkot_2.services.interfaces.PhoneServicesInterface
import com.example.thinkot_2.services.interfaces.UserServicesInterface
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class UserServices(@Autowired private val userRepository: UserRepository, @Autowired private val phoneNumberServices: PhoneNumberServices):UserServicesInterface,
    PhoneServicesInterface {

    fun validateUserForm(userForm: UserForm): UserFormResult {
        val userFormResult = UserFormResult();

        val retUser: Map<String, String> = validateUser(userForm.user);

        retUser.forEach() {(campo, erro) ->
            userFormResult.addUserError(campo, erro)
        }

        userForm.phone.forEach { phoneNumber ->
            if (phoneNumberServices.phoneNumberExists(phoneNumber)){
                userFormResult.addUserError("duplicate_number", "Combinação de DDI, DDD e Numero já existem na base de dados")
            }
        }

        val retPhones: Map<String, String>  = validatePhones(userForm.phone);

        retPhones.forEach() {(campo, erro) ->
            userFormResult.addUserError(campo, erro)
        }

        val retCredential: Map<String, String>  = validateCredential(userForm.credential);

        retCredential.forEach() {(campo, erro) ->
            userFormResult.addUserError(campo, erro)
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