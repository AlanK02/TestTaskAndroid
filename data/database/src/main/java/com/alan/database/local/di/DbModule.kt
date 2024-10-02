package com.alan.database.local.di

import android.app.Application
import androidx.room.Room
import com.alan.database.local.Converter
import com.alan.database.local.MainDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDb(app: Application): MainDB =
        Room.databaseBuilder(app, MainDB::class.java, "main_db")
            .addTypeConverter(Converter())
            .build()
}