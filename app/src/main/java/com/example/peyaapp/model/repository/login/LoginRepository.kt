package com.example.peyaapp.model.repository.login

import android.util.Log
import com.example.peyaapp.model.data.model.LoggedInUser
import javax.inject.Inject
import com.example.peyaapp.model.data.model.Result


class LoginRepository @Inject constructor(val loginDataSource: LoginDataSource) {
    var user: LoggedInUser? = null
        private set
    val isLoggedIn: Boolean get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
        loginDataSource.logout()
    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        val result = loginDataSource.login(username, password)
        if (result is Result.Success) setLoggedInUser(result.data)
        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}