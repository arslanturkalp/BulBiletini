package com.alparslanturk.biletdevret.ui.messages.chat

import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.application.SessionManager.getUserID
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.usermessage.SendMessageRequest
import com.alparslanturk.biletdevret.domain.entities.responses.usermessage.RetrieveMessagesResponse
import com.alparslanturk.biletdevret.domain.entities.responses.usermessage.SendMessageResponse
import com.alparslanturk.biletdevret.domain.usecases.usermessage.RetrieveMessagesUseCase
import com.alparslanturk.biletdevret.domain.usecases.usermessage.SendMessageUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val retrieveMessagesUseCase: RetrieveMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : BaseViewModel() {

    private val _retrieveMessagesFlow: MutableStateFlow<Result<RetrieveMessagesResponse>> = MutableStateFlow(Result.Loading())
    val retrieveMessagesFlow: StateFlow<Result<RetrieveMessagesResponse>> = _retrieveMessagesFlow

    private val _sendMessageFlow: MutableStateFlow<Result<SendMessageResponse>> = MutableStateFlow(Result.Loading())
    val sendMessageFlow: StateFlow<Result<SendMessageResponse>> = _sendMessageFlow

    fun retrieveMessages(user: String) = viewModelScope.launch {
        retrieveMessagesUseCase(listOf(getUserID(), user)).collect {
            when (it) {
                is Result.Error -> _retrieveMessagesFlow.emit(it)
                is Result.Loading -> _retrieveMessagesFlow.emit(it)
                is Result.Success -> _retrieveMessagesFlow.emit(it)
            }
        }
    }

    fun sendMessage(sendMessageRequest: SendMessageRequest) = viewModelScope.launch {
        sendMessageUseCase(sendMessageRequest).collect {
            when (it) {
                is Result.Error -> _sendMessageFlow.emit(it)
                is Result.Loading -> _sendMessageFlow.emit(it)
                is Result.Success -> _sendMessageFlow.emit(it)
            }
        }
    }
}