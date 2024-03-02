package com.alparslanturk.kombineapp.ui.home.createticket.selecttariff

import androidx.lifecycle.viewModelScope
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.data.entities.models.SelectionDialogItem
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.GetTariffListResponse
import com.alparslanturk.kombineapp.domain.usecases.GetTariffListUseCase
import com.alparslanturk.kombineapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectTariffViewModel @Inject constructor(private val getTariffListUseCase: GetTariffListUseCase) : BaseViewModel() {

    private val _getTariffListFlow: MutableStateFlow<Result<GetTariffListResponse>> = MutableStateFlow(Result.Loading())
    val getTariffListFlow: StateFlow<Result<GetTariffListResponse>> = _getTariffListFlow

    private val tariffList: MutableList<SelectionDialogItem> = mutableListOf()
    fun getTariffList() = tariffList

    fun requestGetTariffList() = viewModelScope.launch {
        getTariffListUseCase().collect {
            when (it) {
                is Result.Error -> _getTariffListFlow.emit(it)
                is Result.Loading -> _getTariffListFlow.emit(it)
                is Result.Success -> _getTariffListFlow.emit(it)
                is Result.Auth -> _getTariffListFlow.emit(it)
            }
        }
    }
}