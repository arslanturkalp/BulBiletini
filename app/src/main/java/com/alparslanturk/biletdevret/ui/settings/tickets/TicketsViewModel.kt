package com.alparslanturk.biletdevret.ui.settings.tickets

import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.ticket.GetTicketsResponse
import com.alparslanturk.biletdevret.domain.usecases.ticket.GetTicketsUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(private val getTicketsUseCase: GetTicketsUseCase) : BaseViewModel() {

    private val _getTicketsFlow: MutableStateFlow<Result<GetTicketsResponse>> = MutableStateFlow(Result.Loading())
    val getTicketsFlow: StateFlow<Result<GetTicketsResponse>> = _getTicketsFlow

    fun getTickets(userID: String) = viewModelScope.launch {
        getTicketsUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getTicketsFlow.emit(it)
                is Result.Loading -> _getTicketsFlow.emit(it)
                is Result.Success -> _getTicketsFlow.emit(it)
            }
        }
    }
}