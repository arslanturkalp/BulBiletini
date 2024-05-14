package com.alparslanturk.bulbiletini.ui.ticketdetail

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.AddFavouriteTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.RemoveFavouriteTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.favourite.AddFavouriteTicketResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.favourite.RemoveFavouriteTicketResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.userblacklist.getblockedusers.GetBlockedUsersResponse
import com.alparslanturk.bulbiletini.domain.usecases.favourite.AddFavouriteTicketUseCase
import com.alparslanturk.bulbiletini.domain.usecases.favourite.RemoveFavouriteTicketUseCase
import com.alparslanturk.bulbiletini.domain.usecases.user.GetBlockedUsersUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketDetailViewModel @Inject constructor(
    private val addFavouriteTicketUseCase: AddFavouriteTicketUseCase,
    private val removeFavouriteTicketUseCase: RemoveFavouriteTicketUseCase,
    private val getBlockedUsersUseCase: GetBlockedUsersUseCase
    ) : BaseViewModel() {

    private val _addFavouriteTicketFlow: MutableStateFlow<Result<AddFavouriteTicketResponse>> = MutableStateFlow(Result.Loading())
    val addFavouriteTicketFlow: StateFlow<Result<AddFavouriteTicketResponse>> = _addFavouriteTicketFlow

    private val _removeFavouriteTicketFlow: MutableStateFlow<Result<RemoveFavouriteTicketResponse>> = MutableStateFlow(Result.Loading())
    val removeFavouriteTicketFlow: StateFlow<Result<RemoveFavouriteTicketResponse>> = _removeFavouriteTicketFlow

    private val _getBlockedUsersFlow: MutableStateFlow<Result<GetBlockedUsersResponse>> = MutableStateFlow(Result.Loading())
    val getBlockedUsersFlow: StateFlow<Result<GetBlockedUsersResponse>> = _getBlockedUsersFlow

    fun addFavouriteTicket(addFavouriteTicketRequest: AddFavouriteTicketRequest) = viewModelScope.launch {
        addFavouriteTicketUseCase(addFavouriteTicketRequest).collect {
            when (it) {
                is Result.Error -> _addFavouriteTicketFlow.emit(it)
                is Result.Loading -> _addFavouriteTicketFlow.emit(it)
                is Result.Success -> _addFavouriteTicketFlow.emit(it)
            }
        }
    }

    fun removeFavouriteTicket(removeFavouriteTicketRequest: RemoveFavouriteTicketRequest) = viewModelScope.launch {
        removeFavouriteTicketUseCase(removeFavouriteTicketRequest).collect {
            when (it) {
                is Result.Error -> _removeFavouriteTicketFlow.emit(it)
                is Result.Loading -> _removeFavouriteTicketFlow.emit(it)
                is Result.Success -> _removeFavouriteTicketFlow.emit(it)
            }
        }
    }

    fun getBlockedUsers(userID: String) = viewModelScope.launch {
        getBlockedUsersUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getBlockedUsersFlow.emit(it)
                is Result.Loading -> _getBlockedUsersFlow.emit(it)
                is Result.Success -> _getBlockedUsersFlow.emit(it)
            }
        }
    }
}