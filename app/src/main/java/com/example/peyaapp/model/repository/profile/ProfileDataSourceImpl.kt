package com.example.peyaapp.model.repository.profile

import com.example.peyaapp.model.data.model.Profile
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor() : ProfileDataSource {

    val profile = Profile(
        name = "John",
        lastname = "Doe",
        email = "william.henry.harrison@example-pet-store.com",
        password = "password123",
        nationality = "USA",
    )

    override  fun getProfileInfo() : Profile = profile
}