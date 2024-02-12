package com.alparslanturk.kombineapp.ui.teamdetail

import androidx.lifecycle.viewModelScope
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.AddFavouriteClubRequest
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.RemoveFavouriteClubRequest
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.AddFavouriteClubResponse
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.RemoveFavouriteClubResponse
import com.alparslanturk.kombineapp.domain.usecases.AddFavouriteClubUseCase
import com.alparslanturk.kombineapp.domain.usecases.RemoveFavouriteClubUseCase
import com.alparslanturk.kombineapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val addFavouriteClubUseCase: AddFavouriteClubUseCase,
    private val removeFavouriteClubUseCase: RemoveFavouriteClubUseCase
) : BaseViewModel() {

    private val _addFavouriteClubFlow: MutableStateFlow<Result<AddFavouriteClubResponse>> = MutableStateFlow(Result.Loading())
    val addFavouriteClubFlow: StateFlow<Result<AddFavouriteClubResponse>> = _addFavouriteClubFlow

    private val _removeFavouriteClubFlow: MutableStateFlow<Result<RemoveFavouriteClubResponse>> = MutableStateFlow(Result.Loading())
    val removeFavouriteClubFlow: StateFlow<Result<RemoveFavouriteClubResponse>> = _removeFavouriteClubFlow

    fun addFavouriteClub(addFavouriteClubRequest: AddFavouriteClubRequest) = viewModelScope.launch {
        addFavouriteClubUseCase(addFavouriteClubRequest).collect {
            when (it) {
                is Result.Error -> _addFavouriteClubFlow.emit(it)
                is Result.Loading -> _addFavouriteClubFlow.emit(it)
                is Result.Success -> _addFavouriteClubFlow.emit(it)
                is Result.Auth -> _addFavouriteClubFlow.emit(it)
            }
        }
    }

    fun removeFavouriteClub(removeFavouriteClubRequest: RemoveFavouriteClubRequest) = viewModelScope.launch {
        removeFavouriteClubUseCase(removeFavouriteClubRequest).collect {
            when (it) {
                is Result.Error -> _removeFavouriteClubFlow.emit(it)
                is Result.Loading -> _removeFavouriteClubFlow.emit(it)
                is Result.Success -> _removeFavouriteClubFlow.emit(it)
                is Result.Auth -> _removeFavouriteClubFlow.emit(it)
            }
        }
    }
}