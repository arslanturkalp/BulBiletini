package com.alparslanturk.biletdevret.ui.settings.updateuser

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.data.entities.enums.ChangeProfilePhotoActionType
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.data.entities.models.SelectionDialogItem
import com.alparslanturk.biletdevret.domain.entities.requests.user.UpdateUserInfoRequest
import com.alparslanturk.biletdevret.domain.entities.responses.user.getuserdetail.GetUserDetailResponse
import com.alparslanturk.biletdevret.domain.entities.responses.user.updateuser.UpdateUserInfoResponse
import com.alparslanturk.biletdevret.domain.usecases.user.GetUserDetailUseCase
import com.alparslanturk.biletdevret.domain.usecases.user.UpdateUserInfoUseCase
import com.alparslanturk.biletdevret.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateUserInfoViewModel @Inject constructor(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val getUserDetailUseCase: GetUserDetailUseCase
) : BaseViewModel() {

    private val _updateUserInfoFlow: MutableStateFlow<Result<UpdateUserInfoResponse>> = MutableStateFlow(Result.Loading())
    val updateUserInfoFlow: StateFlow<Result<UpdateUserInfoResponse>> = _updateUserInfoFlow

    private val _getUserDetailFlow: MutableStateFlow<Result<GetUserDetailResponse>> = MutableStateFlow(Result.Loading())
    val getUserDetailFlow: StateFlow<Result<GetUserDetailResponse>> = _getUserDetailFlow

    var selectedProfilePhoto: Uri? = null

    fun updateProfilePhoto(photo: Uri) {
        selectedProfilePhoto = photo
    }

    fun requestUpdateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest) = viewModelScope.launch(Dispatchers.Main) {
        updateUserInfoUseCase(updateUserInfoRequest).collect {
            when (it) {
                is Result.Error -> _updateUserInfoFlow.emit(it)
                is Result.Loading -> _updateUserInfoFlow.emit(it)
                is Result.Success -> _updateUserInfoFlow.emit(it)
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