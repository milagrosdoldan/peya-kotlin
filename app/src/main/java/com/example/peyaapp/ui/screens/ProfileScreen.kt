package com.example.peyaapp.ui.screens
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.example.peyaapp.model.data.model.Profile
import com.example.peyaapp.ui.components.LoadImage
import com.example.peyaapp.viewModels.ProfileViewModel

@Composable
fun UserProfile(
//    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    val profile by profileViewModel.profile.collectAsState()
    var name by remember { mutableStateOf(profile.name) }
    var lastname by remember { mutableStateOf(profile.lastname) }
    var email by remember { mutableStateOf(profile.email) }
    var password by remember { mutableStateOf(profile.password) }
    var nationality by remember { mutableStateOf(profile.nationality) }

    val isImageUploading by profileViewModel.isImageUploading.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val shape = RoundedCornerShape(16.dp)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ){
        uri:Uri? -> imageUri = uri
    }

    Log.e("imageUri", imageUri.toString())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(isImageUploading) {
            AlertDialog(
                onDismissRequest = {  },
                confirmButton= {},
                title={Text("Subiendo Imagen")},
                text={
                    Column {
                        CircularProgressIndicator()
                    }
                }
            )
        }

        if(profile.image.isNotEmpty()) {
            Log.e("imageUri", "not empty")
            imageUri = null
            LoadImage(
                url = profile.image,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(150.dp)
                    .padding(16.dp)

            )
        } else {
            Card(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.Black)
                    .clickable { launcher.launch("image/*") },
            ) {
                if (imageUri != null) {
                    val bitmap = remember(imageUri) {
                        val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
                        ImageDecoder.decodeBitmap(source).asImageBitmap()
                    }
                    Image(
                        bitmap = bitmap,
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Log.e("imageUri", "empty 2")
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Seleccionar Imagen")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = profile.name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = shape
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = profile.lastname,
            onValueChange = { lastname = it },
            label = { Text("Apellido") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = shape
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = profile.email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
            shape = shape
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = profile.password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth(),
            shape = shape
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = profile.nationality,
            onValueChange = { nationality = it },
            label = { Text("Nacionalidad") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = shape
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                val updatedProfile = Profile(
                    name,
                    lastname,
                    email,
                    password,
                    nationality,
                    image = profile.image
                )
                profileViewModel.updateProfile(updatedProfile, imageUri)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = shape
        ) {
            Text("Guardar cambios")
        }
    }
}