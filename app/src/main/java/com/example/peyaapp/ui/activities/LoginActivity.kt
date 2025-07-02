package com.example.peyaapp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peyaapp.model.data.local.SessionManager
import com.example.peyaapp.MainActivity
import com.example.peyaapp.ui.PeyaAppTheme
import com.example.peyaapp.ui.components.RegisterBottomSheet
import com.example.peyaapp.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionManager = SessionManager(this)
        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContent {
            PeyaAppTheme {
                Login(
                    onLoginSuccess = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun Login(onLoginSuccess: (String) -> Unit) {
    val loginViewModel: LoginViewModel = viewModel()
    val loginFormState by loginViewModel.loginFormState.observeAsState()
    val loginResult by loginViewModel.loginResult.observeAsState()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var showRegisterSheet by remember { mutableStateOf(false) }

    LaunchedEffect(loginResult) {
        loginResult?.let {
            loading = false
            it.success?.let { user ->
                onLoginSuccess(user.displayName)
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar sesión",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                loginViewModel.loginDataChanged(username, password)
            },
            label = { Text("Usuario") },
            isError = loginFormState?.usernameError != null,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                loginViewModel.loginDataChanged(username, password)
            },
            label = { Text("Contraseña") },
            isError = loginFormState?.passwordError != null,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                loading = true
                loginViewModel.login(username, password)
            }),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { showRegisterSheet = true }) {
            Text("¿No tenés cuenta? Registrate aquí")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                loading = true
                loginViewModel.login(username, password)
            },
            enabled = loginFormState?.isDataValid == true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Text("Ingresar")
            }
        }
    }

    if (showRegisterSheet) {
        RegisterBottomSheet(
            onDismiss = { showRegisterSheet = false },
        )
    }
}
