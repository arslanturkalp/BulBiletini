package com.alparslanturk.bulbiletini.ui.splash

import androidx.lifecycle.viewModelScope
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.projectsettings.ProjectSettingsGetWithNameResponse
import com.alparslanturk.bulbiletini.domain.usecases.projectsettings.ProjectSettingsGetWithNameUseCase
import com.alparslanturk.bulbiletini.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val projectSettingUseCase: ProjectSettingsGetWithNameUseCase,
) : BaseViewModel() {

    private val _projectSettingFlow: MutableStateFlow<Result<ProjectSettingsGetWithNameResponse>> = MutableStateFlow(Result.Loading())
    val projectSettingFlow: StateFlow<Result<ProjectSettingsGetWithNameResponse>> = _projectSettingFlow

    fun getProjectSettings() = viewModelScope.launch {
        projectSettingUseCase("Version").collect {
            when (it) {
                is Result.Error -> _projectSettingFlow.emit(it)
                is Result.Loading -> _projectSettingFlow.emit(it)
                is Result.Success -> _projectSettingFlow.emit(it)
            }
        }
    }
}