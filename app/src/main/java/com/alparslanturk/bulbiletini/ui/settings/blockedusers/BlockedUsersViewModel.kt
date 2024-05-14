package com.alparslanturk.bulbiletini.ui.settings.blockedusers

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.userblacklist.getblockedusers.GetBlockedUsersResponse
import com.alparslanturk.bulbiletini.domain.usecases.user.GetBlockedUsersUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockedUsersViewModel @Inject constructor(
    private val getBlockedUsersUseCase: GetBlockedUsersUseCase,
): BaseViewModel() {

    private val _getBlockedUsersFlow: MutableStateFlow<Result<GetBlockedUsersResponse>> = MutableStateFlow(Result.Loading())
    val getBlockedUsersFlow: StateFlow<Result<GetBlockedUsersResponse>> = _getBlockedUsersFlow

    fun getBlockedUsers(userID: String) = viewModelScope.launch {
        getBlockedUsersUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getBlockedUsersFlow.emit(it)
                is Result.Loading -> _getBlockedUsersFlow.emit(it)
                is Result.Success -> _getBlockedUsersFlow.emit(it)
            }
        }
    }
}