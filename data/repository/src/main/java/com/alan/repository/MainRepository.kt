package com.alan.repository

import androidx.lifecycle.LiveData
import com.alan.core.models.Offer
import com.alan.core.models.Result
import com.alan.core.models.Vacancy
import com.alan.core.utils.Resource
import com.alan.core.utils.callGenericRequest
import com.alan.database.local.OfferDao
import com.alan.database.local.VacancyDao
import com.alan.network.remote.MainApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val api: MainApi,
    private val offerDao: OfferDao,
    private val vacancyDao: VacancyDao
) {

    suspend fun getDataRemotely(): Flow<Resource<Result?>> =
        callGenericRequest {
            api.getData()
        }

    fun getDataById(id: String): LiveData<List<Vacancy>> {
        return vacancyDao.getVacanciesById(id)
    }

    fun getOffersLocally(): LiveData<List<Offer>> {
        return offerDao.getOffers()
    }

    fun getVacanciesLocally(): LiveData<List<Vacancy>> {
        return vacancyDao.getVacancies()
    }

    suspend fun saveOffersLocally(list: List<Offer?>) {
        if (list.isNotEmpty()) offerDao.saveOffers(list.map { it!! })
    }

    suspend fun saveVacancyLocally(list: List<Vacancy?>) {
        if (list.isNotEmpty()) vacancyDao.saveVacancies(list.map { it!! })
    }

    suspend fun changeStatus(prevStatus: Boolean, id: String) {
        vacancyDao.changeStatus(!prevStatus, id)
    }

}
