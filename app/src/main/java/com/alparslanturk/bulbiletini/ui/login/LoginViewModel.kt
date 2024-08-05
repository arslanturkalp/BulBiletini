package com.alparslanturk.bulbiletini.ui.login

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.user.LoginRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.user.login.LoginResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.register.RegisterResponse
import com.alparslanturk.bulbiletini.domain.usecases.user.LoginUseCase
import com.alparslanturk.bulbiletini.domain.usecases.user.RegisterUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : BaseViewModel() {

    private val _loginFlow: MutableStateFlow<Result<LoginResponse>> = MutableStateFlow(Result.Loading())
    val loginFlow: StateFlow<Result<LoginResponse>> = _loginFlow

    private val _loginAdminFlow: MutableStateFlow<Result<LoginResponse>> = MutableStateFlow(Result.Loading())
    val loginAdminFlow: StateFlow<Result<LoginResponse>> = _loginAdminFlow

    private var isFromGmail: Boolean = false
    fun getIsFromGmail() = isFromGmail

    fun updateIsFromGmail(value: Boolean) {
        isFromGmail = value
    }

    fun signIn(loginRequest: LoginRequest) = viewModelScope.launch(Dispatchers.Main) {
        loginUseCase(loginRequest).collect {
            when (it) {
                is Result.Error -> _loginFlow.emit(it)
                is Result.Loading -> _loginFlow.emit(it)
                is Result.Success -> _loginFlow.emit(it)
            }
        }
    }

    fun signInAdmin(loginRequest: LoginRequest) = viewModelScope.launch(Dispatchers.Main) {
        loginUseCase(loginRequest).collect {
            when (it) {
                is Result.Error -> _loginAdminFlow.emit(it)
                is Result.Loading -> _loginAdminFlow.emit(it)
                is Result.Success -> _loginAdminFlow.emit(it)
            }
        }
    }

    private val _registerFlow: MutableStateFlow<Result<RegisterResponse>> = MutableStateFlow(Result.Loading())
    val registerFlow: StateFlow<Result<RegisterResponse>> = _registerFlow

    fun register(registerRequest: RegisterRequest) = viewModelScope.launch {
        registerUseCase(registerRequest).collect {
            when (it) {
                is Result.Error -> _registerFlow.emit(it)
                is Result.Loading -> _registerFlow.emit(it)
                is Result.Success -> _registerFlow.emit(it)
            }
        }
    }
}