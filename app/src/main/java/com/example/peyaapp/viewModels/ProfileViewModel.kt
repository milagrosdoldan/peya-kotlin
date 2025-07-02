package com.example.peyaapp.viewModels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.example.peyaapp.model.data.model.Profile
import com.example.peyaapp.model.repository.profile.ProfileDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(
    val myApplication: Application,
    private val profileDataSource: ProfileDataSource,
    val cloudinary: Cloudinary
) : AndroidViewModel(myApplication) {

    private val _isImageUploading = MutableStateFlow(false)
    val isImageUploading: MutableStateFlow<Boolean> = _isImageUploading
    private val _profile = MutableStateFlow(Profile())
    val profile: MutableStateFlow<Profile> = _profile



    init {
        loadProfile()
    }

    private fun loadProfile() : Unit{
        viewModelScope.launch {
            delay(1000)
            _profile.value = profileDataSource.getProfileInfo()
        }
    }

    fun updateProfile(newProfile: Profile, imageUri: Uri?) {
        _profile.value = newProfile
        if(imageUri !== null) {
            uploadImage(imageUri)
        }
    }

    private fun uploadImage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _isImageUploading.value = true
            try {
                val inputStream = getApplication<Application>().contentResolver.openInputStream(uri)
                val uploadResult = cloudinary.uploader().upload(inputStream, mapOf("upload_preset" to "peya-example"))
                val imageUrl = uploadResult["secure_url"] as String
                val updaptedProfile = _profile.value.copy(image = imageUrl)
                _profile.value = updaptedProfile

            } catch (e:Exception) {
                Log.e("Cloudinary", "Error updating image")
            } finally {
                _isImageUploading.value = false
            }
        }
    }
}