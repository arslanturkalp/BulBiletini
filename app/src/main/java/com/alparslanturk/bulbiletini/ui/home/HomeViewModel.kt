package com.alparslanturk.bulbiletini.ui.home

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.club.ClubGetDetailWithClubIdRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.club.ClubGetListWithTicketsRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.LoginRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.UpdateNotificationTokenRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.club.clubgetdetailwithclubid.ClubGetDetailWithClubIdResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.login.LoginResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.updatenotificationtoken.UpdateNotificationTokenResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.usermessage.GetUserMessagesResponse
import com.alparslanturk.bulbiletini.domain.usecases.club.ClubGetDetailWithClubIdUseCase
import com.alparslanturk.bulbiletini.domain.usecases.club.ClubGetListWithTicketsUseCase
import com.alparslanturk.bulbiletini.domain.usecases.user.LoginUseCase
import com.alparslanturk.bulbiletini.domain.usecases.user.UpdateNotificationTokenUseCase
import com.alparslanturk.bulbiletini.domain.usecases.usermessage.GetUserMessagesUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val clubGetListWithTicketsUseCase: ClubGetListWithTicketsUseCase,
    private val getUserMessagesUseCase: GetUserMessagesUseCase,
    private val loginUseCase: LoginUseCase,
    private val updateNotificationTokenUseCase: UpdateNotificationTokenUseCase,
    private val clubGetDetailWithClubIdUseCase: ClubGetDetailWithClubIdUseCase,
) : BaseViewModel() {

    private val _clubsGetListWithTicketsFlow: MutableStateFlow<Result<ClubGetListWithTicketsResponse>> = MutableStateFlow(Result.Loading())
    val clubsGetListWithTicketsFlow: StateFlow<Result<ClubGetListWithTicketsResponse>> = _clubsGetListWithTicketsFlow

    private val _getUserMessagesFlow: MutableStateFlow<Result<GetUserMessagesResponse>> = MutableStateFlow(Result.Loading())
    val getUserMessagesFlow: StateFlow<Result<GetUserMessagesResponse>> = _getUserMessagesFlow

    private val _loginTestFlow: MutableStateFlow<Result<ResponseBody>> = MutableStateFlow(Result.Loading())
    val loginTestFlow: StateFlow<Result<ResponseBody>> = _loginTestFlow

    private val _loginFlow: MutableStateFlow<Result<LoginResponse>> = MutableStateFlow(Result.Loading())
    val loginFlow: StateFlow<Result<LoginResponse>> = _loginFlow

    private val _updateNotificationTokenFlow: MutableStateFlow<Result<UpdateNotificationTokenResponse>> = MutableStateFlow(Result.Loading())
    val updateNotificationTokenFlow: StateFlow<Result<UpdateNotificationTokenResponse>> = _updateNotificationTokenFlow

    private val _getClubDetailFlow: MutableStateFlow<Result<ClubGetDetailWithClubIdResponse>> = MutableStateFlow(Result.Loading())
    val getClubDetailFlow: StateFlow<Result<ClubGetDetailWithClubIdResponse>> = _getClubDetailFlow

    fun getClubDetail(clubGetDetailWithClubIdRequest: ClubGetDetailWithClubIdRequest) = viewModelScope.launch {
        clubGetDetailWithClubIdUseCase(clubGetDetailWithClubIdRequest).collect {
            when (it) {
                is Result.Error -> _getClubDetailFlow.emit(it)
                is Result.Loading -> _getClubDetailFlow.emit(it)
                is Result.Success -> _getClubDetailFlow.emit(it)
            }
        }
    }

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

    fun signIn(loginRequest: LoginRequest) = viewModelScope.launch(Dispatchers.Main) {
        loginUseCase(loginRequest).collect {
            when (it) {
                is Result.Error -> _loginFlow.emit(it)
                is Result.Loading -> _loginFlow.emit(it)
                is Result.Success -> _loginFlow.emit(it)
            }
        }
    }

    fun updateNotificationToken(updateNotificationTokenRequest: UpdateNotificationTokenRequest) = viewModelScope.launch(Dispatchers.Main) {
        updateNotificationTokenUseCase(updateNotificationTokenRequest).collect {
            when (it) {
                is Result.Error -> _updateNotificationTokenFlow.emit(it)
                is Result.Loading -> _updateNotificationTokenFlow.emit(it)
                is Result.Success -> _updateNotificationTokenFlow.emit(it)
            }
        }
    }
}