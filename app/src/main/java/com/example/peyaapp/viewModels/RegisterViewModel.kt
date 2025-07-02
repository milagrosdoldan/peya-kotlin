package com.example.peyaapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.peyaapp.model.data.model.RegisterFormState
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.peyaapp.R
import com.example.peyaapp.model.data.local.SessionManager
import com.example.peyaapp.model.data.model.RegisterResult
import com.example.peyaapp.model.data.model.RegisteredUserView
import com.example.peyaapp.model.data.model.Result
import com.example.peyaapp.model.repository.register.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    var name: String = ""
    var email: String = ""
    var password: String = ""
    var confirmPassword: String = ""

    fun register(name: String, email: String, password: String) {
        val result = registerRepository.register(name, email, password)

        if (result is Result.Success) {
            _registerResult.value =
                RegisterResult(success = RegisteredUserView(displayName = name))
            sessionManager.setLoggedInUser()
        } else {
            _registerResult.value = RegisterResult(error = R.string.register_failed)
        }
    }

    fun registerDataChanged(name: String, email: String, password: String, confirmPassword: String) {
        this.name = name
        this.email = email
        this.password = password
        this.confirmPassword = confirmPassword

        when {
            name.isBlank() ->
                _registerForm.value = RegisterFormState(nameError = R.string.invalid_name)
            !isEmailValid(email) ->
                _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
            !isPasswordValid(password) ->
                _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
            password != confirmPassword ->
                _registerForm.value = RegisterFormState(confirmPasswordError = R.string.passwords_dont_match)
            else ->
                _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }
}
