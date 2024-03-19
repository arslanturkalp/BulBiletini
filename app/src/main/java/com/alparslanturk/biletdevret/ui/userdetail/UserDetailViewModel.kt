package com.alparslanturk.biletdevret.ui.userdetail

import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.userblacklist.BlockUserRequest
import com.alparslanturk.biletdevret.domain.entities.responses.profilecomment.GetCommentsResponse
import com.alparslanturk.biletdevret.domain.entities.responses.user.blockuser.BlockUserResponse
import com.alparslanturk.biletdevret.domain.entities.responses.user.getuserdetail.GetUserDetailResponse
import com.alparslanturk.biletdevret.domain.entities.responses.userblacklist.getblockedusers.GetBlockedUsersResponse
import com.alparslanturk.biletdevret.domain.usecases.profilecomment.GetCommentsUseCase
import com.alparslanturk.biletdevret.domain.usecases.user.BlockUserUseCase
import com.alparslanturk.biletdevret.domain.usecases.user.GetBlockedUsersUseCase
import com.alparslanturk.biletdevret.domain.usecases.user.GetUserDetailUseCase
import com.alparslanturk.biletdevret.domain.usecases.user.UnBlockUserUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val commentsUseCase: GetCommentsUseCase,
    private val blockUserUseCase: BlockUserUseCase,
    private val unBlockUserUseCase: UnBlockUserUseCase,
    private val getUserDetailUseCase: GetUserDetailUseCase
) : BaseViewModel() {

    private val _getCommentsFlow: MutableStateFlow<Result<GetCommentsResponse>> = MutableStateFlow(Result.Loading())
    val getCommentsFlow: StateFlow<Result<GetCommentsResponse>> = _getCommentsFlow

    private val _blockUserFlow: MutableStateFlow<Result<BlockUserResponse>> = MutableStateFlow(Result.Loading())
    val blockUserFlow: StateFlow<Result<BlockUserResponse>> = _blockUserFlow

    private val _unBlockUserFlow: MutableStateFlow<Result<BlockUserResponse>> = MutableStateFlow(Result.Loading())
    val unBlockUserFlow: StateFlow<Result<BlockUserResponse>> = _unBlockUserFlow

    private val _getUserDetailFlow: MutableStateFlow<Result<GetUserDetailResponse>> = MutableStateFlow(Result.Loading())
    val getUserDetailFlow: StateFlow<Result<GetUserDetailResponse>> = _getUserDetailFlow

    fun getComments(userID: String) = viewModelScope.launch {
        commentsUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getCommentsFlow.emit(it)
                is Result.Loading -> _getCommentsFlow.emit(it)
                is Result.Success -> _getCommentsFlow.emit(it)
            }
        }
    }

    fun blockUser(blockedUserID: String, blockedByUserID: String) = viewModelScope.launch {
        blockUserUseCase(BlockUserRequest(blockedUserID, blockedByUserID)).collect {
            when (it) {
                is Result.Error -> _blockUserFlow.emit(it)
                is Result.Loading -> _blockUserFlow.emit(it)
                is Result.Success -> _blockUserFlow.emit(it)
            }
        }
    }

    fun unBlockUser(blockedUserID: String, blockedByUserID: String) = viewModelScope.launch {
        unBlockUserUseCase(BlockUserRequest(blockedUserID, blockedByUserID)).collect {
            when (it) {
                is Result.Error -> _unBlockUserFlow.emit(it)
                is Result.Loading -> _unBlockUserFlow.emit(it)
                is Result.Success -> _unBlockUserFlow.emit(it)
            }
        }
    }

    fun getUserDetail(userID: String) = viewModelScope.launch {
        getUserDetailUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getUserDetailFlow.emit(it)
                is Result.Loading -> _getUserDetailFlow.emit(it)
                is Result.Success -> _getUserDetailFlow.emit(it)
            }
        }
    }
}