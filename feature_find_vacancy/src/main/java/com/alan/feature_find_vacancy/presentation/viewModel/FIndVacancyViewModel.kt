package com.alan.feature_find_vacancy.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alan.core.models.Offer
import com.alan.core.models.Vacancy
import com.alan.core.utils.Resource
import com.alan.feature_find_vacancy.domain.GetAdviceUseCase
import com.alan.feature_find_vacancy.domain.GetDataRemotelyUseCase
import com.alan.feature_find_vacancy.domain.GetVacanciesUseCase
import com.alan.feature_find_vacancy.domain.UpdateFavouriteFromFindUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindVacancyViewModel @Inject constructor(
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val getAdviceUseCase: GetAdviceUseCase,
    private val getDataRemotelyUseCase: GetDataRemotelyUseCase,
    private val updateFavouriteUseCase: UpdateFavouriteFromFindUseCase
) : ViewModel() {

    var isLoading = MutableLiveData(false)
        private set

    var vacancies: List<Vacancy> = listOf()
        private set

    var isExpanded = MutableLiveData<Boolean>(null)

    fun getVacancies(): LiveData<List<Vacancy>> = getVacanciesUseCase.execute()

    fun getAdvice(): LiveData<List<Offer>> = getAdviceUseCase.execute()

    fun getDataRemotely() {
        viewModelScope.launch(Dispatchers.Main) {
            getDataRemotelyUseCase.execute().collect { res ->
                when (res) {
                    is Resource.Success -> {
                        val data = res.data
                        val offers = data?.offers
                        val vacancies = data?.vacancies

                        if (!offers.isNullOrEmpty()) {
                            viewModelScope.launch(Dispatchers.IO) {
                                getAdviceUseCase.execute()
                            }
                        }

                        if (vacancies != null) {
                            viewModelScope.launch(Dispatchers.IO) {
                                getVacanciesUseCase.execute()
                            }
                        }

                        isLoading.value = false
                    }

                    is Resource.Loading -> {
                        isLoading.value = true
                    }

                    is Resource.Error -> {
                        isLoading.value = false
                    }
                }
            }
        }
    }

    fun changeStatus(prevStatus: Boolean, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateFavouriteUseCase.execute(prevStatus, id)
        }
    }

    fun saveVacanciesInViewModel(res: List<Vacancy>?) {
        if (res != null) vacancies = res
    }
}
