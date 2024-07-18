package com.alparslanturk.bulbiletini.ui.settings.suggestionandcomplaint

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.suggestionandcomplaint.SendSuggestionAndComplaintRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.suggestionandcomplaint.SendSuggestionAndComplaintResponse
import com.alparslanturk.bulbiletini.domain.usecases.suggestionandcomplaint.SendSuggestionAndComplaintUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestionAndComplaintViewModel @Inject constructor(private val sendSuggestionAndComplaintUseCase: SendSuggestionAndComplaintUseCase) : BaseViewModel() {

    private val _sendSuggestionAndComplaintFlow: MutableStateFlow<Result<SendSuggestionAndComplaintResponse>> = MutableStateFlow(Result.Loading())
    val sendSuggestionAndComplaintFlow: StateFlow<Result<SendSuggestionAndComplaintResponse>> = _sendSuggestionAndComplaintFlow

    fun sendSuggestionAndComplaint(sendSuggestionAndComplaintRequest: SendSuggestionAndComplaintRequest) = viewModelScope.launch {
        sendSuggestionAndComplaintUseCase(sendSuggestionAndComplaintRequest).collect {
            when (it) {
                is Result.Error -> _sendSuggestionAndComplaintFlow.emit(it)
                is Result.Loading -> _sendSuggestionAndComplaintFlow.emit(it)
                is Result.Success -> _sendSuggestionAndComplaintFlow.emit(it)
            }
        }
    }
}