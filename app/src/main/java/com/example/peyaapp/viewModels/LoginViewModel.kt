package com.example.peyaapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.peyaapp.model.repository.login.LoginRepository
import com.example.peyaapp.R
import com.example.peyaapp.model.data.local.SessionManager
import com.example.peyaapp.model.data.model.LoggedInUserView
import com.example.peyaapp.model.data.model.LoginFormState
import com.example.peyaapp.model.data.model.LoginResult
import com.example.peyaapp.model.data.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(
    private val sessionManager: SessionManager,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _logoutEvent = MutableLiveData<Boolean>()
    val logoutEvent: LiveData<Boolean> get() = _logoutEvent

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = loginRepository.login(username, password)
            delay(1000)
            if (result is Result.Success) {
                sessionManager.setLoggedInUser()
                _loginResult.value = LoginResult(
                    success = LoggedInUserView(displayName = result.data.displayName)
                )
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }


    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    fun logout() {
        loginRepository.logout()
        _logoutEvent.value = true
    }
}