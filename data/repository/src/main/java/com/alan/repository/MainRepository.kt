package com.alan.repository

import androidx.lifecycle.LiveData
import com.alan.core.models.Offer
import com.alan.core.models.Result
import com.alan.core.models.Vacancy
import com.alan.core.utils.Resource
import com.alan.core.utils.callGenericRequest
import com.alan.database.local.AdviceDao
import com.alan.database.local.VacancyDao
import com.alan.network.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val api: ApiService,
    private val adviceDao: AdviceDao,
    private val vacancyDao: VacancyDao
) {

    suspend fun getDataRemotely(): Flow<Resource<Result?>> =
        callGenericRequest {
            api.getData()
        }

    fun getDataById(id: String): LiveData<List<Vacancy>> {
        return vacancyDao.getVacanciesById(id)
    }

    fun getAdviceLocally(): LiveData<List<Offer>> {
        return adviceDao.getAdvice()
    }

    suspend fun saveAdviceLocally(list: List<Offer?>) {
        if (list.isNotEmpty()) adviceDao.saveAdvice(list.map { it!! })
    }

    suspend fun saveVacancyLocally(list: List<Vacancy?>) {
        if (list.isNotEmpty()) vacancyDao.saveVacancies(list.map { it!! })
    }

    fun getVacanciesLocally(): LiveData<List<Vacancy>> {
        return vacancyDao.getVacancies()
    }

    suspend fun changeStatus(prevStatus: Boolean, id: String) {
        vacancyDao.changeStatus(!prevStatus, id)
    }

}
