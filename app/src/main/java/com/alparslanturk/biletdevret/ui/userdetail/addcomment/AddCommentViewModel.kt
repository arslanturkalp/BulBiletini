package com.alparslanturk.biletdevret.ui.userdetail.addcomment

import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.profilecomment.AddCommentRequest
import com.alparslanturk.biletdevret.domain.entities.responses.profilecomment.AddCommentResponse
import com.alparslanturk.biletdevret.domain.usecases.profilecomment.AddCommentUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCommentViewModel @Inject constructor(private val addCommentUseCase: AddCommentUseCase) : BaseViewModel() {

    private val _addCommentFlow: MutableStateFlow<Result<AddCommentResponse>> = MutableStateFlow(Result.Loading())
    val addCommentFlow: StateFlow<Result<AddCommentResponse>> = _addCommentFlow

    fun addComment(addCommentRequest: AddCommentRequest) = viewModelScope.launch {
        addCommentUseCase(addCommentRequest).collect {
            when (it) {
                is Result.Error -> _addCommentFlow.emit(it)
                is Result.Loading -> _addCommentFlow.emit(it)
                is Result.Success -> _addCommentFlow.emit(it)
            }
        }
    }
}