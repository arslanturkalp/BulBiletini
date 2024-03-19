package com.alparslanturk.biletdevret.ui.messages

import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.usermessage.GetUserMessagesResponse
import com.alparslanturk.biletdevret.domain.usecases.usermessage.GetUserMessagesUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val getUserMessagesUseCase: GetUserMessagesUseCase,
) : BaseViewModel() {

    private val _getUserMessagesFlow: MutableStateFlow<Result<GetUserMessagesResponse>> = MutableStateFlow(Result.Loading())
    val getUserMessagesFlow: StateFlow<Result<GetUserMessagesResponse>> = _getUserMessagesFlow

    fun getUserMessages(userID: String) = viewModelScope.launch {
        getUserMessagesUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getUserMessagesFlow.emit(it)
                is Result.Loading -> _getUserMessagesFlow.emit(it)
                is Result.Success -> _getUserMessagesFlow.emit(it)
            }
        }
    }
}