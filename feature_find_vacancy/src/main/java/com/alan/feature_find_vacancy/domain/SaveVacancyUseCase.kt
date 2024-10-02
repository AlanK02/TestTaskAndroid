package com.alan.feature_find_vacancy.domain

import com.alan.core.models.Vacancy
import com.alan.repository.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveVacancyUseCase @Inject constructor(private val repository: MainRepository
) {
    suspend fun execute(vacancy: List<Vacancy?>) = repository.saveVacancyLocally(vacancy)
}
