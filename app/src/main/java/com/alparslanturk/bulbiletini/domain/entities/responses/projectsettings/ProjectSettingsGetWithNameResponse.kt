package com.alparslanturk.bulbiletini.domain.entities.responses.projectsettings

data class ProjectSettingsGetWithNameResponse(
    val data: ProjectSettingsGetWithNameResponseItem,
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)

data class ProjectSettingsGetWithNameResponseItem(
    val id: String,
    val settingName: String,
    val settingValue: String,
    val createdDate: String,
    val updatedDate: String,
    val isActive: Boolean
)
