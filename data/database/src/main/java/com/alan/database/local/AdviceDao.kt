package com.alan.database.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alan.core.models.Offer
import com.alan.database.local.entity.AdviceEntity

@Dao
interface AdviceDao {

    @Query("SELECT * FROM offers")
    fun getAdvice(): LiveData<List<Offer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = AdviceEntity::class)
    suspend fun saveAdvice(offers: List<Offer>)
}