package com.example.peyaapp.model.data.model


/**
 * Resultado del intento de login, puede ser éxito o error.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)

/**
 * Modelo para mostrar información de usuario en la UI.
 */
data class LoggedInUserView(
    val displayName: String
    // Otros campos accesibles a la UI, como email, foto, etc.
)

/**
 * Modelo interno que representa un usuario logueado, con info más completa.
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String
)

data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String = when (this) {
        is Success -> "Success[data=$data]"
        is Error -> "Error[exception=$exception]"
        else -> "Unknown"
    }
}

