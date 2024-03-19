package com.alparslanturk.biletdevret.ui.home

import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.club.ClubGetListWithTicketsRequest
import com.alparslanturk.biletdevret.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.biletdevret.domain.entities.responses.user.login.LoginResponse
import com.alparslanturk.biletdevret.domain.entities.responses.usermessage.GetUserMessagesResponse
import com.alparslanturk.biletdevret.domain.usecases.LoginTestUseCase
import com.alparslanturk.biletdevret.domain.usecases.club.ClubGetListWithTicketsUseCase
import com.alparslanturk.biletdevret.domain.usecases.usermessage.GetUserMessagesUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val clubGetListWithTicketsUseCase: ClubGetListWithTicketsUseCase,
    private val getUserMessagesUseCase: GetUserMessagesUseCase,
    private val loginTestUseCase: LoginTestUseCase
) : BaseViewModel() {

    private val _clubsGetListWithTicketsFlow: MutableStateFlow<Result<ClubGetListWithTicketsResponse>> = MutableStateFlow(Result.Loading())
    val clubsGetListWithTicketsFlow: StateFlow<Result<ClubGetListWithTicketsResponse>> = _clubsGetListWithTicketsFlow

    private val _getUserMessagesFlow: MutableStateFlow<Result<GetUserMessagesResponse>> = MutableStateFlow(Result.Loading())
    val getUserMessagesFlow: StateFlow<Result<GetUserMessagesResponse>> = _getUserMessagesFlow

    private val _loginTestFlow: MutableStateFlow<Result<ResponseBody>> = MutableStateFlow(Result.Loading())
    val loginTestFlow: StateFlow<Result<ResponseBody>> = _loginTestFlow

    fun getClubsAndTickets(clubGetListWithTicketsRequest: ClubGetListWithTicketsRequest) = viewModelScope.launch {
        clubGetListWithTicketsUseCase(clubGetListWithTicketsRequest).collect {
            when (it) {
                is Result.Error -> _clubsGetListWithTicketsFlow.emit(it)
                is Result.Loading -> _clubsGetListWithTicketsFlow.emit(it)
                is Result.Success -> _clubsGetListWithTicketsFlow.emit(it)
            }
        }
    }

    fun getUserMessages(userID: String) = viewModelScope.launch {
        getUserMessagesUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getUserMessagesFlow.emit(it)
                is Result.Loading -> _getUserMessagesFlow.emit(it)
                is Result.Success -> _getUserMessagesFlow.emit(it)
            }
        }
    }

    fun loginTest() = viewModelScope.launch {
        loginTestUseCase().collect {
            when (it) {
                is Result.Error -> _loginTestFlow.emit(it)
                is Result.Loading -> _loginTestFlow.emit(it)
                is Result.Success -> _loginTestFlow.emit(it)
            }
        }
    }
}