package com.alparslanturk.kombineapp.ui.home

import androidx.lifecycle.viewModelScope
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.club.ClubGetListWithTicketsRequest
import com.alparslanturk.kombineapp.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.kombineapp.domain.usecases.ClubGetListWithTicketsUseCase
import com.alparslanturk.kombineapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val clubGetListWithTicketsUseCase: ClubGetListWithTicketsUseCase) : BaseViewModel() {

    private val _clubsGetListWithTicketsFlow: MutableStateFlow<Result<ClubGetListWithTicketsResponse>> = MutableStateFlow(Result.Loading())
    val clubsGetListWithTicketsFlow: StateFlow<Result<ClubGetListWithTicketsResponse>> = _clubsGetListWithTicketsFlow

    fun getClubsAndTickets(clubGetListWithTicketsRequest: ClubGetListWithTicketsRequest) = viewModelScope.launch {
        clubGetListWithTicketsUseCase(clubGetListWithTicketsRequest).collect {
            when (it) {
                is Result.Error -> _clubsGetListWithTicketsFlow.emit(it)
                is Result.Loading -> _clubsGetListWithTicketsFlow.emit(it)
                is Result.Success -> _clubsGetListWithTicketsFlow.emit(it)
                is Result.Auth -> _clubsGetListWithTicketsFlow.emit(it)
            }
        }
    }
}