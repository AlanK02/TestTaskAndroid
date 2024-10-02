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
import com.alan.feature_find_vacancy.domain.SaveAdviceUseCase
import com.alan.feature_find_vacancy.domain.SaveVacancyUseCase
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
    private val updateFavouriteUseCase: UpdateFavouriteFromFindUseCase,
    private val saveAdviceUseCase: SaveAdviceUseCase,
    private val saveVacancyUseCase: SaveVacancyUseCase
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
                        res.data?.offers?.takeIf { it.isNotEmpty() }?.let {
                            saveAdviceUseCase.execute(it)
                        }
                        res.data?.vacancies?.let {
                            saveVacancyUseCase.execute(it)
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
