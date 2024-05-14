package com.alparslanturk.bulbiletini.ui.home.createticket

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.MyTariff
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.TicketMatch
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.CreateTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.CreateTicketResponse
import com.alparslanturk.bulbiletini.domain.usecases.ticket.CreateTicketUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTicketViewModel @Inject constructor(
    private val createTicketUseCase: CreateTicketUseCase,
) : BaseViewModel() {

    private val _createTicketFlow: MutableStateFlow<Result<CreateTicketResponse>> = MutableStateFlow(Result.Loading())
    val createTicketFlow: StateFlow<Result<CreateTicketResponse>> = _createTicketFlow

    private var selectedMatch: TicketMatch? = null

    fun getSelectedMatch() = selectedMatch

    fun updateSelectedMatch(match: TicketMatch?) {
        selectedMatch = match
    }

    private var selectedTariff: MyTariff? = null

    fun getSelectedTariff() = selectedTariff

    fun updateSelectedTariff(tariff: MyTariff?) {
        selectedTariff = tariff
    }

    fun requestCreateTicket(createTicketRequest: CreateTicketRequest) = viewModelScope.launch(Dispatchers.Main) {
        createTicketUseCase(createTicketRequest).collect {
            when (it) {
                is Result.Error -> _createTicketFlow.emit(it)
                is Result.Loading -> _createTicketFlow.emit(it)
                is Result.Success -> _createTicketFlow.emit(it)
            }
        }
    }
}