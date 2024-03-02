package com.alparslanturk.kombineapp.ui.login

import androidx.lifecycle.viewModelScope
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.user.LoginRequest
import com.alparslanturk.kombineapp.domain.entities.responses.user.login.LoginResponse
import com.alparslanturk.kombineapp.domain.usecases.LoginUseCase
import com.alparslanturk.kombineapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    private val _loginFlow: MutableStateFlow<Result<LoginResponse>> = MutableStateFlow(Result.Loading())
    val loginFlow: StateFlow<Result<LoginResponse>> = _loginFlow

    fun signIn(loginRequest: LoginRequest) = viewModelScope.launch(Dispatchers.Main) {
        loginUseCase(loginRequest).collect {
            when (it) {
                is Result.Error -> _loginFlow.emit(it)
                is Result.Loading -> _loginFlow.emit(it)
                is Result.Success -> _loginFlow.emit(it)
                is Result.Auth -> _loginFlow.emit(it)
            }
        }
    }
}