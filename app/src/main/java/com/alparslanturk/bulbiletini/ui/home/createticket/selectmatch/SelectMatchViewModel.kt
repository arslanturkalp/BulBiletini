package com.alparslanturk.bulbiletini.ui.home.createticket.selectmatch

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.SelectionDialogItem
import com.alparslanturk.bulbiletini.domain.entities.responses.match.GetMatchListResponse
import com.alparslanturk.bulbiletini.domain.usecases.match.GetMatchListUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
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