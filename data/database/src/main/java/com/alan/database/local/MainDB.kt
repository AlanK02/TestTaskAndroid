package com.alan.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alan.database.local.entity.OfferEntity
import com.alan.database.local.entity.VacancyEntity

@Database(entities = [OfferEntity::class, VacancyEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class MainDB : RoomDatabase() {

    abstract fun getVacancyDai(): VacancyDao

    abstract fun getOfferDao(): OfferDao

}