package com.alan.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alan.core.models.Button

@Entity("offers")
data class AdviceEntity(
    val button: Button?,
    val id: String?,
    val link: String,
    val title: String,
    @PrimaryKey(autoGenerate = true)
    val myId: Int? = null
)