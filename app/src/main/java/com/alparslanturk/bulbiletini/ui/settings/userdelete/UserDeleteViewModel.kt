package com.alparslanturk.bulbiletini.ui.settings.userdelete

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.user.deleteuser.UserDeleteResponse
import com.alparslanturk.bulbiletini.domain.usecases.user.UserDeleteUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
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