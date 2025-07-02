package com.example.peyaapp.model.repository.register

import com.example.peyaapp.model.data.model.Result
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val registerDataSource: RegisterDataSource
) {
    fun register(name: String, email: String, password: String): Result<String> {
        return registerDataSource.register(name, email, password)
    }
}