package com.example.thinkot_2.services

import com.example.thinkot_2.forms.UserForm
import com.example.thinkot_2.forms.results.UserFormResult
import com.example.thinkot_2.services.interfaces.UserServicesInterface
import org.springframework.stereotype.Component

@Component
class UserServices:UserServicesInterface {

    fun validateUserForm(userForm: UserForm): UserFormResult {
        val userFormResult = UserFormResult();

        val retUser: Map<String, String> = validateUser(userForm.user);

        retUser.forEach() {(campo, erro) ->
            userFormResult.addUserError(campo, erro)
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
}