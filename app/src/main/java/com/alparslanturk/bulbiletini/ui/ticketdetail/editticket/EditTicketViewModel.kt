package com.alparslanturk.bulbiletini.ui.ticketdetail.editticket

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.UpdateTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.UpdateTicketResponse
import com.alparslanturk.bulbiletini.domain.usecases.ticket.UpdateTicketUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTicketViewModel @Inject constructor(private val updateTicketUseCase: UpdateTicketUseCase): BaseViewModel() {

    private val _updateTicketFlow: MutableStateFlow<Result<UpdateTicketResponse>> = MutableStateFlow(Result.Loading())
    val updateTicketFlow: StateFlow<Result<UpdateTicketResponse>> = _updateTicketFlow

    fun updateTicket(updateTicketRequest: UpdateTicketRequest) = viewModelScope.launch {
        updateTicketUseCase(updateTicketRequest).collect {
            when (it) {
                is Result.Error -> _updateTicketFlow.emit(it)
                is Result.Loading -> _updateTicketFlow.emit(it)
                is Result.Success -> _updateTicketFlow.emit(it)
            }
        }
    }
}