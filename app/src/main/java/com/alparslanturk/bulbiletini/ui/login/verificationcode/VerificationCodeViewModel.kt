package com.alparslanturk.bulbiletini.ui.login.verificationcode

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.user.verificationcode.VerificationCodeResponse
import com.alparslanturk.bulbiletini.domain.usecases.user.VerificationCodeUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationCodeViewModel @Inject constructor(private val verificationCodeUseCase: VerificationCodeUseCase) : BaseViewModel() {

    private val _verificationCodeFlow: MutableStateFlow<Result<VerificationCodeResponse>> = MutableStateFlow(Result.Loading(isLoading = false))
    val verificationCodeFlow: StateFlow<Result<VerificationCodeResponse>> = _verificationCodeFlow

    fun getVerificationCode(email: String) = viewModelScope.launch {
        verificationCodeUseCase(email).collect {
            when (it) {
                is Result.Error -> _verificationCodeFlow.emit(it)
                is Result.Loading -> _verificationCodeFlow.emit(it)
                is Result.Success -> _verificationCodeFlow.emit(it)
            }
        }
    }
}