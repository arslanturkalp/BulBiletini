package com.alparslanturk.biletdevret.ui.register

import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.biletdevret.domain.entities.responses.user.register.RegisterResponse
import com.alparslanturk.biletdevret.domain.usecases.user.RegisterUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) : BaseViewModel() {

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