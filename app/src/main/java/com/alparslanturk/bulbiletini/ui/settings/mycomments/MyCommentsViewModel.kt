package com.alparslanturk.bulbiletini.ui.settings.mycomments

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.profilecomment.GetMyCommentsResponse
import com.alparslanturk.bulbiletini.domain.usecases.profilecomment.GetMyCommentsUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCommentsViewModel @Inject constructor(private val getMyCommentsUseCase: GetMyCommentsUseCase) : BaseViewModel() {

    private val _getMyCommentsFlow: MutableStateFlow<Result<GetMyCommentsResponse>> = MutableStateFlow(Result.Loading())
    val getMyCommentsFlow: StateFlow<Result<GetMyCommentsResponse>> = _getMyCommentsFlow

    fun getMyComments(createdUserID: String) = viewModelScope.launch {
        getMyCommentsUseCase(createdUserID).collect {
            when (it) {
                is Result.Error -> _getMyCommentsFlow.emit(it)
                is Result.Loading -> _getMyCommentsFlow.emit(it)
                is Result.Success -> _getMyCommentsFlow.emit(it)
            }
        }
    }
}