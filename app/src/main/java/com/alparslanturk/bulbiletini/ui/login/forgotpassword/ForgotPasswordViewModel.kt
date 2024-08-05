package com.alparslanturk.bulbiletini.ui.login.forgotpassword

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.user.ForgotPasswordRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.LoginRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.user.forgotpassword.ForgotPasswordResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.login.LoginResponse
import com.alparslanturk.bulbiletini.domain.usecases.user.ForgotPasswordUseCase
import com.alparslanturk.bulbiletini.domain.usecases.user.LoginUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _forgotPasswordFlow: MutableStateFlow<Result<ForgotPasswordResponse>> = MutableStateFlow(Result.Loading())
    val forgotPasswordFlow: StateFlow<Result<ForgotPasswordResponse>> = _forgotPasswordFlow

    private val _loginFlow: MutableStateFlow<Result<LoginResponse>> = MutableStateFlow(Result.Loading())
    val loginFlow: StateFlow<Result<LoginResponse>> = _loginFlow

    fun signIn(loginRequest: LoginRequest) = viewModelScope.launch(Dispatchers.Main) {
        loginUseCase(loginRequest).collect {
            when (it) {
                is Result.Error -> _loginFlow.emit(it)
                is Result.Loading -> _loginFlow.emit(it)
                is Result.Success -> _loginFlow.emit(it)
            }
        }
    }

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