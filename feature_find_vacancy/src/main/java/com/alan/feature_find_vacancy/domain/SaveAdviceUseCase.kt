package com.alan.feature_find_vacancy.domain

import com.alan.core.models.Offer
import com.alan.repository.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveAdviceUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend fun execute(offers: List<Offer?>) = repository.saveAdviceLocally(offers)
}
