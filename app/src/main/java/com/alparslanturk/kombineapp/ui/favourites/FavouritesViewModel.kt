package com.alparslanturk.kombineapp.ui.favourites

import androidx.lifecycle.viewModelScope
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.kombineapp.domain.usecases.GetFavouritesUseCase
import com.alparslanturk.kombineapp.ui.base.BaseViewModel
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
                is Result.Auth -> _getFavouritesFlow.emit(it)
            }
        }
    }
}