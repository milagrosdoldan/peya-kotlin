package com.example.peyaapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peyaapp.viewModels.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBottomSheet(
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val registerViewModel: RegisterViewModel = viewModel()
    val registerFormState by registerViewModel.registerFormState.observeAsState()
    var name by remember { mutableStateOf(registerViewModel.name) }
    var email by remember { mutableStateOf(registerViewModel.email) }
    var password by remember { mutableStateOf(registerViewModel.password) }
    var confirmPassword by remember { mutableStateOf(registerViewModel.confirmPassword) }

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Registro de usuario", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    registerViewModel.registerDataChanged(name, email, password, confirmPassword)
                },
                label = { Text("Nombre completo") },
                isError = registerFormState?.nameError != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )
            registerFormState?.nameError?.let {
                Text(stringResource(id = it), color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    registerViewModel.registerDataChanged(name, email, password, confirmPassword)
                },
                label = { Text("Email") },
                isError = registerFormState?.emailError != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )
            registerFormState?.emailError?.let {
                Text(stringResource(id = it), color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    registerViewModel.registerDataChanged(name, email, password, confirmPassword)
                },
                label = { Text("Contraseña") },
                isError = registerFormState?.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )
            registerFormState?.passwordError?.let {
                Text(stringResource(id = it), color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    registerViewModel.registerDataChanged(name, email, password, confirmPassword)
                },
                label = { Text("Confirmar contraseña") },
                isError = registerFormState?.confirmPasswordError != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )
            registerFormState?.confirmPasswordError?.let {
                Text(stringResource(id = it), color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    registerViewModel.register(name, email, password)
                    onDismiss()
                },
                enabled = registerFormState?.isDataValid == true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Registrarse")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = { onDismiss() }) {
                Text("Cerrar")
            }
        }
    }
}
