package com.example.peyaapp.model.repository.login

import com.example.peyaapp.model.data.local.SessionManager
import com.example.peyaapp.model.data.model.LoggedInUser
import com.example.peyaapp.model.data.model.Result
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val sessionManager: SessionManager
) : LoginDataSource {

    override fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            val fakeUser = LoggedInUser(UUID.randomUUID().toString(), username)
            Result.Success(fakeUser)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    override fun logout() {
        sessionManager.clearSession()
    }

}