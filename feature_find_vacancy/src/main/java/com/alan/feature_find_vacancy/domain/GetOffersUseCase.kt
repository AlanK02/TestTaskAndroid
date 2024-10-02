package com.alan.feature_find_vacancy.domain

import com.alan.repository.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAdviceUseCase @Inject constructor(
    private val repository: MainRepository
) {
    fun execute() = repository.getOffersLocally()
}
