package com.example.peyaapp.model.repository.register

import com.example.peyaapp.model.data.model.Result

interface RegisterDataSource {
    fun register(name: String, email: String, password: String): Result<String>
}