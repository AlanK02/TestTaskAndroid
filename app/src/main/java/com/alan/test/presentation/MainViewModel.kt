package com.alan.test.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alan.core.models.Vacancy
import com.alan.feature_favorite.domain.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    fun getFavourites(): LiveData<List<Vacancy>> {
        return getFavoritesUseCase.execute()
    }
}
