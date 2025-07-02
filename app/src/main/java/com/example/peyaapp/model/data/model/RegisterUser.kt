package com.example.peyaapp.model.data.model

data class RegisterResult(
    val success: RegisteredUserView? = null,
    val error: Int? = null
)

data class RegisteredUserView(
    val displayName: String
)
data class RegisterFormState(
    val nameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val confirmPasswordError: Int? = null,
    val isDataValid: Boolean = false
)
