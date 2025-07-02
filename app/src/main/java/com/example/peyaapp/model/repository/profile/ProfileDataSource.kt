package com.example.peyaapp.model.repository.profile

import com.example.peyaapp.model.data.model.Profile

interface ProfileDataSource {
    fun getProfileInfo() : Profile
}