package com.alparslanturk.kombineapp.ui.matchdetail

import androidx.lifecycle.viewModelScope
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.AddFavouriteClubRequest
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.RemoveFavouriteClubRequest
import com.alparslanturk.kombineapp.domain.entities.requests.ticket.GetMatchTicketsRequest
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.AddFavouriteClubResponse
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.RemoveFavouriteClubResponse
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.GetTicketsResponse
import com.alparslanturk.kombineapp.domain.usecases.GetMatchTicketsUseCase
import com.alparslanturk.kombineapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    private val getMatchTicketsUseCase: GetMatchTicketsUseCase,
) : BaseViewModel() {

    private val _getMatchTicketsFlow: MutableStateFlow<Result<GetTicketsResponse>> = MutableStateFlow(Result.Loading())
    val getMatchTicketsFlow: StateFlow<Result<GetTicketsResponse>> = _getMatchTicketsFlow

    fun getMatchTickets(matchID: String) = viewModelScope.launch {
        getMatchTicketsUseCase(GetMatchTicketsRequest(matchID, getUserID())).collect {
            when (it) {
                is Result.Error -> _getMatchTicketsFlow.emit(it)
                is Result.Loading -> _getMatchTicketsFlow.emit(it)
                is Result.Success -> _getMatchTicketsFlow.emit(it)
                is Result.Auth -> _getMatchTicketsFlow.emit(it)
            }
        }
    }
}