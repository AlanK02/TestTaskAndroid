package com.alan.feature_favorite.domain

import com.alan.repository.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateFavouriteUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend fun execute(prevStatus: Boolean, id: String) {
        repository.changeStatus(prevStatus, id)
    }
}