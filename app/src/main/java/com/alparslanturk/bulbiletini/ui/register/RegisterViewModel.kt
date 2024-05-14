package com.alparslanturk.bulbiletini.ui.register

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.user.register.RegisterResponse
import com.alparslanturk.bulbiletini.domain.usecases.user.RegisterUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
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