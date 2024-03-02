package com.alparslanturk.kombineapp.ui.userdetail

import androidx.lifecycle.viewModelScope
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.responses.profilecomment.GetCommentsResponse
import com.alparslanturk.kombineapp.domain.usecases.GetCommentsUseCase
import com.alparslanturk.kombineapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(private val commentsUseCase: GetCommentsUseCase) : BaseViewModel() {

    private val _getCommentsFlow: MutableStateFlow<Result<GetCommentsResponse>> = MutableStateFlow(Result.Loading())
    val getCommentsFlow: StateFlow<Result<GetCommentsResponse>> = _getCommentsFlow

    init {
        getComments(getUserID())
    }

    private fun getComments(userID: String) = viewModelScope.launch {
        commentsUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getCommentsFlow.emit(it)
                is Result.Loading -> _getCommentsFlow.emit(it)
                is Result.Success -> _getCommentsFlow.emit(it)
                is Result.Auth -> _getCommentsFlow.emit(it)
            }
        }
    }
}