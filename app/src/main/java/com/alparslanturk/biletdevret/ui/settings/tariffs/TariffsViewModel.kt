package com.alparslanturk.biletdevret.ui.settings.tariffs

import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.tariff.PurchaseTariffRequest
import com.alparslanturk.biletdevret.domain.entities.responses.tariff.GetTariffListResponse
import com.alparslanturk.biletdevret.domain.entities.responses.tariff.PurchaseTariffResponse
import com.alparslanturk.biletdevret.domain.usecases.tariff.GetTariffListUseCase
import com.alparslanturk.biletdevret.domain.usecases.purchaseflow.PurchaseTariffUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
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
            }
        }
    }

    fun purchaseTariff(purchaseTariffRequest: PurchaseTariffRequest) = viewModelScope.launch {
        purchaseTariffUseCase(purchaseTariffRequest).collect {
            when (it) {
                is Result.Error -> _purchaseTariffFlow.emit(it)
                is Result.Loading -> _purchaseTariffFlow.emit(it)
                is Result.Success -> _purchaseTariffFlow.emit(it)
            }
        }
    }
}