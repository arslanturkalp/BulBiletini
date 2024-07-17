package com.alparslanturk.bulbiletini.domain.usecases.projectsettings

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.projectsettings.ProjectSettingsGetWithNameResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class ProjectSettingsGetWithNameUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, ProjectSettingsGetWithNameResponse>() {
    override suspend fun getData(params: String?): Result<ProjectSettingsGetWithNameResponse> = repository.projectSettingsGetWithName(params.orEmpty())
}