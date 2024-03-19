package com.alparslanturk.biletdevret.ui.userdetail.usercomments

import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.profilecomment.GetCommentsResponse
import com.alparslanturk.biletdevret.domain.usecases.profilecomment.GetCommentsUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCommentsViewModel @Inject constructor(private val commentsUseCase: GetCommentsUseCase) : BaseViewModel() {

    private val _getCommentsFlow: MutableStateFlow<Result<GetCommentsResponse>> = MutableStateFlow(Result.Loading())
    val getCommentsFlow: StateFlow<Result<GetCommentsResponse>> = _getCommentsFlow

    fun getComments(userID: String) = viewModelScope.launch {
        commentsUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getCommentsFlow.emit(it)
                is Result.Loading -> _getCommentsFlow.emit(it)
                is Result.Success -> _getCommentsFlow.emit(it)
            }
        }
    }
}