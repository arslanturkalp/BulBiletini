package com.alparslanturk.biletdevret.ui.settings.userdelete

import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.biletdevret.domain.entities.responses.user.deleteuser.UserDeleteResponse
import com.alparslanturk.biletdevret.domain.usecases.user.UserDeleteUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDeleteViewModel @Inject constructor(private val userDeleteUseCase: UserDeleteUseCase) : BaseViewModel() {

    private val _userDeleteFlow: MutableStateFlow<Result<UserDeleteResponse>> = MutableStateFlow(Result.Loading())
    val userDeleteFlow: StateFlow<Result<UserDeleteResponse>> = _userDeleteFlow

    fun deleteUser(userDeleteRequest: UserDeleteRequest) = viewModelScope.launch {
        userDeleteUseCase(userDeleteRequest).collect {
            when (it) {
                is Result.Error -> _userDeleteFlow.emit(it)
                is Result.Loading -> _userDeleteFlow.emit(it)
                is Result.Success -> _userDeleteFlow.emit(it)
            }
        }
    }
}