package com.alan.database.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alan.core.models.Vacancy
import com.alan.database.local.entity.VacancyEntity

@Dao
interface VacancyDao {

    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVacancies(list: List<Vacancy>)

    @Query("select * from vacancy")
    fun getVacancies(): LiveData<List<Vacancy>>

    @Query("SELECT * FROM vacancy where id = :id")
    fun getVacanciesById(id: String): LiveData<List<Vacancy>>

    @Query("UPDATE vacancy SET isFavorite =:prevStatus WHERE id = :id")
    suspend fun changeStatus(prevStatus: Boolean, id: String)

}