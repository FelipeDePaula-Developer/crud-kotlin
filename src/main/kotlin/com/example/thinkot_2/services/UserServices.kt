package com.example.thinkot_2.services

import com.example.thinkot_2.entities.PhoneNumber
import com.example.thinkot_2.entities.User
import com.example.thinkot_2.entities.interfaces.Person
import com.example.thinkot_2.forms.ClientForm
import com.example.thinkot_2.forms.UserForm
import com.example.thinkot_2.forms.results.PersonFormResult
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
    fun registerUser(userForm: UserForm): PersonFormResult {
        val personFormResult: PersonFormResult = validateUserForm(userForm)

        if (personFormResult.hasErrors()) {
            return personFormResult
        }

        val savedUser = saveOrUpdateByCpf(userForm.user)
        userForm.phone.forEach() { phone ->
            phone.user = savedUser
        }
        phoneNumberRepository.saveAll(userForm.phone)

        userForm.credential.user = savedUser
        credentialServices.saveCredential(userForm.credential)

        return personFormResult
    }
    
    fun registerClient(clientForm: ClientForm):PersonFormResult{
        val personFormResult: PersonFormResult = validateCommomFields(clientForm.client, clientForm.phone)
        if ()
    }

    fun validateUserForm(userForm: UserForm): PersonFormResult {
        val personFormResult = validateCommomFields(userForm.user, userForm.phone)

        if (!credentialServices.loginExists(userForm.credential)) {

            val retCredential: Map<String, String> = validateCredential(userForm.credential);
            retCredential.forEach() { (campo, erro) ->
                personFormResult.addUserError(campo, erro)
            }
        } else {
            personFormResult.addUserError("duplicate_login", "O login ${userForm.credential.login} já está em uso")
        }


        return personFormResult;
    }

    fun validateCommomFields(person: Person,  phone: List<PhoneNumber>) : PersonFormResult{
        val personFormResult = PersonFormResult();

        val retUser: Map<String, String> = validatePerson(person);

        retUser.forEach() { (campo, erro) ->
            personFormResult.addUserError(campo, erro)
        }

        var skipPhoneValidation = false;
        phone.forEach { phoneNumber ->
            if (phoneNumberServices.phoneNumberExists(phoneNumber)) {
                personFormResult.addUserError(
                    "duplicate_number",
                    "Combinação de DDI (${phoneNumber.phoneDDI}), DDD (${phoneNumber.phoneDDD}) e Numero ${phoneNumber.phoneNumber} já existem na base de dados"
                )
                skipPhoneValidation = true;
            }
        }

        if (!skipPhoneValidation) {
            val retPhones: Map<String, String> = validatePhones(phone);
            retPhones.forEach() { (campo, erro) ->
                personFormResult.addUserError(campo, erro)
            }
        }

        return personFormResult
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