package com.alan.feature_favorite.domain

import androidx.lifecycle.LiveData
import com.alan.core.models.Vacancy
import com.alan.repository.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFavoritesUseCase @Inject constructor(
    private val repository: MainRepository
) {
    fun execute(): LiveData<List<Vacancy>> {
        return repository.getVacanciesLocally()
    }
}
