package com.alparslanturk.bulbiletini.ui.teamdetail

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.club.ClubGetDetailWithClubIdRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.AddFavouriteClubRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.RemoveFavouriteClubRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.club.clubgetdetailwithclubid.ClubGetDetailWithClubIdResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.favourite.AddFavouriteClubResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.favourite.RemoveFavouriteClubResponse
import com.alparslanturk.bulbiletini.domain.usecases.club.ClubGetDetailWithClubIdUseCase
import com.alparslanturk.bulbiletini.domain.usecases.favourite.AddFavouriteClubUseCase
import com.alparslanturk.bulbiletini.domain.usecases.favourite.RemoveFavouriteClubUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val clubGetDetailWithClubIdUseCase: ClubGetDetailWithClubIdUseCase,
    private val addFavouriteClubUseCase: AddFavouriteClubUseCase,
    private val removeFavouriteClubUseCase: RemoveFavouriteClubUseCase
) : BaseViewModel() {

    private val _getClubDetailFlow: MutableStateFlow<Result<ClubGetDetailWithClubIdResponse>> = MutableStateFlow(Result.Loading())
    val getClubDetailFlow: StateFlow<Result<ClubGetDetailWithClubIdResponse>> = _getClubDetailFlow

    private val _addFavouriteClubFlow: MutableStateFlow<Result<AddFavouriteClubResponse>> = MutableStateFlow(Result.Loading())
    val addFavouriteClubFlow: StateFlow<Result<AddFavouriteClubResponse>> = _addFavouriteClubFlow

    private val _removeFavouriteClubFlow: MutableStateFlow<Result<RemoveFavouriteClubResponse>> = MutableStateFlow(Result.Loading())
    val removeFavouriteClubFlow: StateFlow<Result<RemoveFavouriteClubResponse>> = _removeFavouriteClubFlow

    fun getClubDetail(clubGetDetailWithClubIdRequest: ClubGetDetailWithClubIdRequest) = viewModelScope.launch {
        clubGetDetailWithClubIdUseCase(clubGetDetailWithClubIdRequest).collect {
            when (it) {
                is Result.Error -> _getClubDetailFlow.emit(it)
                is Result.Loading -> _getClubDetailFlow.emit(it)
                is Result.Success -> _getClubDetailFlow.emit(it)
            }
        }
    }

    fun addFavouriteClub(addFavouriteClubRequest: AddFavouriteClubRequest) = viewModelScope.launch {
        addFavouriteClubUseCase(addFavouriteClubRequest).collect {
            when (it) {
                is Result.Error -> _addFavouriteClubFlow.emit(it)
                is Result.Loading -> _addFavouriteClubFlow.emit(it)
                is Result.Success -> _addFavouriteClubFlow.emit(it)
            }
        }
    }

    fun removeFavouriteClub(removeFavouriteClubRequest: RemoveFavouriteClubRequest) = viewModelScope.launch {
        removeFavouriteClubUseCase(removeFavouriteClubRequest).collect {
            when (it) {
                is Result.Error -> _removeFavouriteClubFlow.emit(it)
                is Result.Loading -> _removeFavouriteClubFlow.emit(it)
                is Result.Success -> _removeFavouriteClubFlow.emit(it)
            }
        }
    }
}