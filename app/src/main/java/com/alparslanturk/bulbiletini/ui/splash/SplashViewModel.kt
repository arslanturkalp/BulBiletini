package com.alparslanturk.bulbiletini.ui.splash

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.user.LoginRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.projectsettings.ProjectSettingsGetWithNameResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.login.LoginResponse
import com.alparslanturk.bulbiletini.domain.usecases.projectsettings.ProjectSettingsGetWithNameUseCase
import com.alparslanturk.bulbiletini.domain.usecases.user.LoginUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val projectSettingUseCase: ProjectSettingsGetWithNameUseCase
) : BaseViewModel() {

    private val _projectSettingFlow: MutableStateFlow<Result<ProjectSettingsGetWithNameResponse>> = MutableStateFlow(Result.Loading())
    val projectSettingFlow: StateFlow<Result<ProjectSettingsGetWithNameResponse>> = _projectSettingFlow

    private val _loginFlow: MutableStateFlow<Result<LoginResponse>> = MutableStateFlow(Result.Loading())
    val loginFlow: StateFlow<Result<LoginResponse>> = _loginFlow

    private val _requiredUpdateFlow: MutableStateFlow<Result<ProjectSettingsGetWithNameResponse>> = MutableStateFlow(Result.Loading())
    val requiredUpdateFlow: StateFlow<Result<ProjectSettingsGetWithNameResponse>> = _requiredUpdateFlow

    private var isFromAdmin: Boolean = false

    fun getIsFromAdmin() = isFromAdmin

    private fun updateIsFromAdmin(value: Boolean) {
        isFromAdmin = value
    }

    fun signIn(loginRequest: LoginRequest, fromAdmin: Boolean = false) = viewModelScope.launch(Dispatchers.Main) {
        loginUseCase(loginRequest).collect {
            when (it) {
                is Result.Error -> _loginFlow.emit(it)
                is Result.Loading -> _loginFlow.emit(it)
                is Result.Success -> {
                    updateIsFromAdmin(fromAdmin)
                    _loginFlow.emit(it)
                }
            }
        }
    }

    fun getProjectSettings() = viewModelScope.launch {
        projectSettingUseCase("Version").collect {
            when (it) {
                is Result.Error -> _projectSettingFlow.emit(it)
                is Result.Loading -> _projectSettingFlow.emit(it)
                is Result.Success -> _projectSettingFlow.emit(it)
            }
        }
    }

    fun isRequiredUpdate() = viewModelScope.launch {
        projectSettingUseCase("IsRequiredUpdateAndroid").collect {
            when (it) {
                is Result.Error -> _requiredUpdateFlow.emit(it)
                is Result.Loading -> _requiredUpdateFlow.emit(it)
                is Result.Success -> _requiredUpdateFlow.emit(it)
            }
        }
    }
}