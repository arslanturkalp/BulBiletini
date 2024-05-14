package com.alparslanturk.bulbiletini.ui.login.forgotpassword

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.user.ForgotPasswordRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.user.forgotpassword.ForgotPasswordResponse
import com.alparslanturk.bulbiletini.domain.usecases.user.ForgotPasswordUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val forgotPasswordUseCase: ForgotPasswordUseCase) : BaseViewModel() {

    private val _forgotPasswordFlow: MutableStateFlow<Result<ForgotPasswordResponse>> = MutableStateFlow(Result.Loading())
    val forgotPasswordFlow: StateFlow<Result<ForgotPasswordResponse>> = _forgotPasswordFlow

    fun changePassword(forgotPasswordRequest: ForgotPasswordRequest) = viewModelScope.launch {
        forgotPasswordUseCase(forgotPasswordRequest).collect {
            when (it) {
                is Result.Error -> _forgotPasswordFlow.emit(it)
                is Result.Loading -> _forgotPasswordFlow.emit(it)
                is Result.Success -> _forgotPasswordFlow.emit(it)
            }
        }
    }
}