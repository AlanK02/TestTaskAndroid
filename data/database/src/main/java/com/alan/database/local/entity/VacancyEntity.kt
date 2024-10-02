package com.alan.database.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alan.core.models.Address
import com.alan.core.models.Experience
import com.alan.core.models.Salary

@Entity("vacancy")
data class VacancyEntity(
    val address: Address,
    val appliedNumber: Int,
    val company: String,
    val description: String?,
    val experience: Experience,
    @PrimaryKey
    val id: String,
    val isFavorite: Boolean = false,
    val lookingNumber: Int,
    val publishedDate: String,
    val questions: List<String>,
    val responsibilities: String,
    val salary: Salary,
    val schedules: List<String>,
    val title: String,
)