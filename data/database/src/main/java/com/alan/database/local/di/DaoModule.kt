package com.alan.database.local.di

import com.alan.database.local.MainDB
import com.alan.database.local.OfferDao
import com.alan.database.local.VacancyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun provideVacancyDao(
        database: MainDB
    ): VacancyDao = database.getVacancyDai()

    @Provides
    fun provideOfferDao(
        database: MainDB
    ): OfferDao = database.getOfferDao()

}