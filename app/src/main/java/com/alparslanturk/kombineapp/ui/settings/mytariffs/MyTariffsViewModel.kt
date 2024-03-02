package com.alparslanturk.kombineapp.ui.settings.mytariffs

import androidx.lifecycle.viewModelScope
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.GetMyTariffsResponse
import com.alparslanturk.kombineapp.domain.usecases.GetMyTariffsUseCase
import com.alparslanturk.kombineapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTariffsViewModel @Inject constructor(private val myTariffsUseCase: GetMyTariffsUseCase) : BaseViewModel() {

    private val _getMyTariffsFlow: MutableStateFlow<Result<GetMyTariffsResponse>> = MutableStateFlow(Result.Loading())
    val getMyTariffsFlow: StateFlow<Result<GetMyTariffsResponse>> = _getMyTariffsFlow

    fun getMyTariffs(userID: String) = viewModelScope.launch {
        myTariffsUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getMyTariffsFlow.emit(it)
                is Result.Loading -> _getMyTariffsFlow.emit(it)
                is Result.Success -> _getMyTariffsFlow.emit(it)
                is Result.Auth -> _getMyTariffsFlow.emit(it)
            }
        }
    }
}