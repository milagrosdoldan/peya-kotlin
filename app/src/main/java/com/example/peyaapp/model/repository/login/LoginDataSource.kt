package com.example.peyaapp.model.repository.login

import com.example.peyaapp.model.data.model.LoggedInUser
import com.example.peyaapp.model.data.model.Result

interface LoginDataSource {
    fun login(email: String, password: String): Result<LoggedInUser>
    fun logout()
}