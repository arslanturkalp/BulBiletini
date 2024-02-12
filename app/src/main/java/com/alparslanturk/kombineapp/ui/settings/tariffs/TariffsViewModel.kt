package com.alparslanturk.kombineapp.ui.settings.tariffs

import androidx.lifecycle.viewModelScope
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.tariff.PurchaseTariffRequest
import com.alparslanturk.kombineapp.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.GetTariffListResponse
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.PurchaseTariffResponse
import com.alparslanturk.kombineapp.domain.usecases.GetTariffListUseCase
import com.alparslanturk.kombineapp.domain.usecases.PurchaseTariffUseCase
import com.alparslanturk.kombineapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TariffsViewModel @Inject constructor(
    private val getTariffListUseCase: GetTariffListUseCase,
    private val purchaseTariffUseCase: PurchaseTariffUseCase
) : BaseViewModel() {

    private val _getTariffListFlow: MutableStateFlow<Result<GetTariffListResponse>> = MutableStateFlow(Result.Loading())
    val getTariffListFlow: StateFlow<Result<GetTariffListResponse>> = _getTariffListFlow

    private val _purchaseTariffFlow: MutableStateFlow<Result<PurchaseTariffResponse>> = MutableStateFlow(Result.Loading())
    val purchaseTariffFlow: StateFlow<Result<PurchaseTariffResponse>> = _purchaseTariffFlow

    fun getTariffList() = viewModelScope.launch {
        getTariffListUseCase().collect {
            when (it) {
                is Result.Error -> _getTariffListFlow.emit(it)
                is Result.Loading -> _getTariffListFlow.emit(it)
                is Result.Success -> _getTariffListFlow.emit(it)
                is Result.Auth -> _getTariffListFlow.emit(it)
            }
        }
    }

    fun purchaseTariff(purchaseTariffRequest: PurchaseTariffRequest) = viewModelScope.launch {
        purchaseTariffUseCase(purchaseTariffRequest).collect {
            when (it) {
                is Result.Error -> _purchaseTariffFlow.emit(it)
                is Result.Loading -> _purchaseTariffFlow.emit(it)
                is Result.Success -> _purchaseTariffFlow.emit(it)
                is Result.Auth -> _purchaseTariffFlow.emit(it)
            }
        }
    }
}