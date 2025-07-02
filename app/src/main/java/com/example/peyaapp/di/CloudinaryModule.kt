package com.example.peyaapp.di

import com.cloudinary.Cloudinary
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.example.peyaapp.BuildConfig
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

object CloudinaryModule {
    @Provides
    @Singleton

    fun provideCloudinary(): Cloudinary {
        return Cloudinary(
            mapOf(
                "cloud_name" to BuildConfig.CLOUDINARY_CLOUD_NAME,
                "api_key" to BuildConfig.CLOUDINARY_API_KEY,
                "api_secret" to BuildConfig.CLOUDINARY_API_SECRET

            )
        )
    }
}