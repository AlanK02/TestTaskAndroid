package com.alan.network.remote.di

import com.alan.network.remote.MainApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SearchModule {

    private val BASE_URL = "https://drive.usercontent.google.com/"

    @Provides
    @Singleton
    fun provideApi(): MainApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainApi::class.java)
    }
}