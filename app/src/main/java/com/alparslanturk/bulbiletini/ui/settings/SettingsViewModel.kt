package com.alparslanturk.bulbiletini.ui.settings

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.data.entities.enums.ChangeProfilePhotoActionType
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.SelectionDialogItem
import com.alparslanturk.bulbiletini.domain.entities.requests.user.UpdateUserInfoRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.user.getuserdetail.GetUserDetailResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.updateuser.UpdateUserInfoResponse
import com.alparslanturk.bulbiletini.domain.usecases.user.GetUserDetailUseCase
import com.alparslanturk.bulbiletini.domain.usecases.user.UpdateUserInfoUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val getUserDetailUseCase: GetUserDetailUseCase
) : BaseViewModel() {

    private var selectedProfilePhoto: Uri? = null

    fun updateProfilePhoto(photo: Uri) {
        selectedProfilePhoto = photo
    }

    init {
        requestGetUserDetail(getUserID())
    }

    private val _updateUserInfoFlow: MutableStateFlow<Result<UpdateUserInfoResponse>> = MutableStateFlow(Result.Loading())
    val updateUserInfoFlow: StateFlow<Result<UpdateUserInfoResponse>> = _updateUserInfoFlow

    private val _getUserDetailFlow: MutableStateFlow<Result<GetUserDetailResponse>> = MutableStateFlow(Result.Loading())
    val getUserDetailFlow: StateFlow<Result<GetUserDetailResponse>> = _getUserDetailFlow

    fun requestUpdateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest) = viewModelScope.launch(Dispatchers.Main) {
        updateUserInfoUseCase(updateUserInfoRequest).collect {
            when (it) {
                is Result.Error -> _updateUserInfoFlow.emit(it)
                is Result.Loading -> _updateUserInfoFlow.emit(it)
                is Result.Success -> _updateUserInfoFlow.emit(it)
            }
        }
    }

    fun requestGetUserDetail(userID: String) = viewModelScope.launch(Dispatchers.Main) {
        getUserDetailUseCase(userID).collect {
            when (it) {
                is Result.Error -> _getUserDetailFlow.emit(it)
                is Result.Loading -> _getUserDetailFlow.emit(it)
                is Result.Success -> _getUserDetailFlow.emit(it)
            }
        }
    }

    fun getOptionList(): List<SelectionDialogItem> {
        val selectionList: ArrayList<SelectionDialogItem> = arrayListOf()

        selectionList.apply {
            clear()
            add(SelectionDialogItem("", id = ChangeProfilePhotoActionType.CAMERA.id, resId = R.string.camera))
            add(SelectionDialogItem("", id = ChangeProfilePhotoActionType.GALLERY.id, resId = R.string.gallery))
        }

        return selectionList
    }
}