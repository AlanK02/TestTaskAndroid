package com.alan.feature_find_vacancy.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alan.core.models.Vacancy
import com.alan.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun getDataById(id: String): LiveData<List<Vacancy>> {
        return repository.getDataById(id)
    }

    fun changeStatus(status: Boolean, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeStatus(prevStatus = status, id = id)
        }
    }

}