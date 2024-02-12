package com.alparslanturk.kombineapp.ui.home.createticket

import androidx.lifecycle.viewModelScope
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.data.entities.models.SelectionDialogItem
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.GetTariffListResponse
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.CreateTicketRequest
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.CreateTicketResponse
import com.alparslanturk.kombineapp.domain.usecases.CreateTicketUseCase
import com.alparslanturk.kombineapp.domain.usecases.GetTariffListUseCase
import com.alparslanturk.kombineapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTicketViewModel @Inject constructor(
    private val createTicketUseCase: CreateTicketUseCase,
    private val getTariffListUseCase: GetTariffListUseCase
) : BaseViewModel() {

    private val _createTicketFlow: MutableStateFlow<Result<CreateTicketResponse>> = MutableStateFlow(Result.Loading())
    val createTicketFlow: StateFlow<Result<CreateTicketResponse>> = _createTicketFlow

    private val _getTariffListFlow: MutableStateFlow<Result<GetTariffListResponse>> = MutableStateFlow(Result.Loading())
    val getTariffListFlow: StateFlow<Result<GetTariffListResponse>> = _getTariffListFlow

    private val tariffList: MutableList<SelectionDialogItem> = mutableListOf()
    fun getTariffList() = tariffList

    private var selectedTariffID: String = ""
    fun getSelectedTariffID() = selectedTariffID

    fun updateSelectedTariffID(value: String) {
        selectedTariffID = value
    }

    init {
        requestGetTariffList()
    }

    fun createTicket(createTicketRequest: CreateTicketRequest) = viewModelScope.launch {
        createTicketUseCase(createTicketRequest).collect {
            when (it) {
                is Result.Error -> _createTicketFlow.emit(it)
                is Result.Loading -> _createTicketFlow.emit(it)
                is Result.Success -> _createTicketFlow.emit(it)
                is Result.Auth -> _createTicketFlow.emit(it)
            }
        }
    }

    private fun requestGetTariffList() = viewModelScope.launch {
        getTariffListUseCase().collect {
            when (it) {
                is Result.Error -> _getTariffListFlow.emit(it)
                is Result.Loading -> _getTariffListFlow.emit(it)
                is Result.Success -> _getTariffListFlow.emit(it)
                is Result.Auth -> _getTariffListFlow.emit(it)
            }
        }
    }
}