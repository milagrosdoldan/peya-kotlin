
package com.example.peyaapp.model.repository.register
import com.example.peyaapp.model.data.model.Result
import javax.inject.Inject

class RegisterDataSourceImpl @Inject constructor() : RegisterDataSource {
    override fun register(name: String, email: String, password: String): Result<String> {
        return try {
            if (email == "existing@example.com") {
                Result.Error(Exception("Usuario ya existe"))
            } else {
                Result.Success(name)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
