package com.alparslanturk.biletdevret.ui.home.createticket.selectmatch

import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.data.entities.models.SelectionDialogItem
import com.alparslanturk.biletdevret.domain.entities.responses.match.GetMatchListResponse
import com.alparslanturk.biletdevret.domain.usecases.match.GetMatchListUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectMatchViewModel @Inject constructor(private val getMatchListUseCase: GetMatchListUseCase) : BaseViewModel() {

    private val _getMatchListFlow: MutableStateFlow<Result<GetMatchListResponse>> = MutableStateFlow(Result.Loading())
    val getMatchListFlow: StateFlow<Result<GetMatchListResponse>> = _getMatchListFlow

    private val matchList: MutableList<SelectionDialogItem> = mutableListOf()
    fun getMatchList() = matchList

    fun requestGetMatchList() = viewModelScope.launch {
        getMatchListUseCase().collect {
            when (it) {
                is Result.Error -> _getMatchListFlow.emit(it)
                is Result.Loading -> _getMatchListFlow.emit(it)
                is Result.Success -> _getMatchListFlow.emit(it)
            }
        }
    }
}