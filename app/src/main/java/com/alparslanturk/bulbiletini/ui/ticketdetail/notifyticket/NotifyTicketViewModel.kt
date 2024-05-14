package com.alparslanturk.bulbiletini.ui.ticketdetail.notifyticket

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.NotifyTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.NotifyTicketResponse
import com.alparslanturk.bulbiletini.domain.usecases.ticket.NotifyTicketUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyTicketViewModel @Inject constructor(private val notifyTicketUseCase: NotifyTicketUseCase): BaseViewModel() {

    private val _notifyTicketFlow: MutableStateFlow<Result<NotifyTicketResponse>> = MutableStateFlow(Result.Loading())
    val notifyTicketFlow: StateFlow<Result<NotifyTicketResponse>> = _notifyTicketFlow

    fun notifyTicket(notifyTicketRequest: NotifyTicketRequest) = viewModelScope.launch {
        notifyTicketUseCase(notifyTicketRequest).collect {
            when (it) {
                is Result.Error -> _notifyTicketFlow.emit(it)
                is Result.Loading -> _notifyTicketFlow.emit(it)
                is Result.Success -> _notifyTicketFlow.emit(it)
            }
        }
    }
}