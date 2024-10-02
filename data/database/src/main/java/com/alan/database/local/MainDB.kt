package com.alan.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alan.database.local.entity.AdviceEntity
import com.alan.database.local.entity.VacancyEntity

@Database(entities = [AdviceEntity::class, VacancyEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class MainDB : RoomDatabase() {

    abstract fun getVacancyDai(): VacancyDao

    abstract fun getAdviceDao(): AdviceDao

}