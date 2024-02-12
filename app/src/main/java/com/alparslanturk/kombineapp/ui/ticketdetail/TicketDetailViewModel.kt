package com.alparslanturk.kombineapp.ui.ticketdetail

import androidx.lifecycle.viewModelScope
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.AddFavouriteTicketRequest
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.RemoveFavouriteTicketRequest
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.AddFavouriteTicketResponse
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.RemoveFavouriteTicketResponse
import com.alparslanturk.kombineapp.domain.usecases.AddFavouriteTicketUseCase
import com.alparslanturk.kombineapp.domain.usecases.RemoveFavouriteTicketUseCase
import com.alparslanturk.kombineapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketDetailViewModel @Inject constructor(
    private val addFavouriteTicketUseCase: AddFavouriteTicketUseCase,
    private val removeFavouriteTicketUseCase: RemoveFavouriteTicketUseCase
) : BaseViewModel() {

    private val _addFavouriteTicketFlow: MutableStateFlow<Result<AddFavouriteTicketResponse>> = MutableStateFlow(Result.Loading())
    val addFavouriteTicketFlow: StateFlow<Result<AddFavouriteTicketResponse>> = _addFavouriteTicketFlow

    private val _removeFavouriteTicketFlow: MutableStateFlow<Result<RemoveFavouriteTicketResponse>> = MutableStateFlow(Result.Loading())
    val removeFavouriteTicketFlow: StateFlow<Result<RemoveFavouriteTicketResponse>> = _removeFavouriteTicketFlow

    fun addFavouriteTicket(addFavouriteTicketRequest: AddFavouriteTicketRequest) = viewModelScope.launch {
        addFavouriteTicketUseCase(addFavouriteTicketRequest).collect {
            when (it) {
                is Result.Error -> _addFavouriteTicketFlow.emit(it)
                is Result.Loading -> _addFavouriteTicketFlow.emit(it)
                is Result.Success -> _addFavouriteTicketFlow.emit(it)
                is Result.Auth -> _addFavouriteTicketFlow.emit(it)
            }
        }
    }

    fun removeFavouriteTicket(removeFavouriteTicketRequest: RemoveFavouriteTicketRequest) = viewModelScope.launch {
        removeFavouriteTicketUseCase(removeFavouriteTicketRequest).collect {
            when (it) {
                is Result.Error -> _removeFavouriteTicketFlow.emit(it)
                is Result.Loading -> _removeFavouriteTicketFlow.emit(it)
                is Result.Success -> _removeFavouriteTicketFlow.emit(it)
                is Result.Auth -> _removeFavouriteTicketFlow.emit(it)
            }
        }
    }
}