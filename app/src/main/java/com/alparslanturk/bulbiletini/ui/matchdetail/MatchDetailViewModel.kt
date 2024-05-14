package com.alparslanturk.bulbiletini.ui.matchdetail

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.GetMatchTicketsRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.GetTicketsResponse
import com.alparslanturk.bulbiletini.domain.usecases.ticket.GetMatchTicketsUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
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
            }
        }
    }
}