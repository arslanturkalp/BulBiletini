package com.alparslanturk.bulbiletini.ui.home.createticket.selecttariff

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.tariff.GetMyTariffsResponse
import com.alparslanturk.bulbiletini.domain.usecases.purchaseflow.GetMyTariffsUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectTariffViewModel @Inject constructor(private val myTariffsUseCase: GetMyTariffsUseCase) : BaseViewModel() {

    private val _getMyTariffsFlow: MutableStateFlow<Result<GetMyTariffsResponse>> = MutableStateFlow(Result.Loading())
    val getMyTariffsFlow: StateFlow<Result<GetMyTariffsResponse>> = _getMyTariffsFlow

    fun requestGetMyTariffs(userID: String) = viewModelScope.launch {
        myTariffsUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getMyTariffsFlow.emit(it)
                is Result.Loading -> _getMyTariffsFlow.emit(it)
                is Result.Success -> _getMyTariffsFlow.emit(it)
            }
        }
    }
}