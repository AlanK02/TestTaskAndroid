package com.alan.feature_find_vacancy.domain

import com.alan.core.models.Result
import com.alan.core.utils.Resource
import com.alan.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDataRemotelyUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend fun execute(): Flow<Resource<Result?>> {
        return repository.getDataRemotely()
    }
}
