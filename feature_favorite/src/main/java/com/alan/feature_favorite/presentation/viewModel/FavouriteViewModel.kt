package com.alan.feature_favorite.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alan.feature_favorite.domain.GetFavoritesUseCase
import com.alan.feature_favorite.domain.UpdateFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    fun getFavourites() = getFavoritesUseCase.execute()

    fun updateFavourite(status: Boolean, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateFavouriteUseCase.execute(status, id)
        }
    }
}