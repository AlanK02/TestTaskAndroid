package com.alan.database.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alan.core.models.Offer
import com.alan.database.local.entity.OfferEntity

@Dao
interface OfferDao {

    @Query("SELECT * FROM offer")
    fun getOffers(): LiveData<List<Offer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = OfferEntity::class)
    suspend fun saveOffers(offer: List<Offer>)
}