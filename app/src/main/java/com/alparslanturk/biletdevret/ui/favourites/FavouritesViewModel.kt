package com.alparslanturk.biletdevret.ui.favourites

import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.biletdevret.domain.usecases.favourite.GetFavouritesUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val getFavouritesUseCase: GetFavouritesUseCase) : BaseViewModel() {

    private val _getFavouritesFlow: MutableStateFlow<Result<ClubGetListWithTicketsResponse>> = MutableStateFlow(Result.Loading())
    val getFavouritesFlow: StateFlow<Result<ClubGetListWithTicketsResponse>> = _getFavouritesFlow

    fun getFavourites(userID: String) = viewModelScope.launch {
        getFavouritesUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getFavouritesFlow.emit(it)
                is Result.Loading -> _getFavouritesFlow.emit(it)
                is Result.Success -> _getFavouritesFlow.emit(it)
            }
        }
    }
}