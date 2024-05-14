package com.alparslanturk.bulbiletini.ui.userdetail.rateuser

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.userrate.RateUserRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.userrate.RateUserResponse
import com.alparslanturk.bulbiletini.domain.usecases.userrate.RateUserUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RateUserViewModel @Inject constructor(private val rateUserUseCase: RateUserUseCase) : BaseViewModel() {

    private val _rateUserFlow: MutableStateFlow<Result<RateUserResponse>> = MutableStateFlow(Result.Loading())
    val rateUserFlow: StateFlow<Result<RateUserResponse>> = _rateUserFlow

    fun rateUser(rateUserRequest: RateUserRequest) = viewModelScope.launch {
        rateUserUseCase(rateUserRequest).collect {
            when (it) {
                is Result.Error -> _rateUserFlow.emit(it)
                is Result.Loading -> _rateUserFlow.emit(it)
                is Result.Success -> _rateUserFlow.emit(it)
            }
        }
    }
}