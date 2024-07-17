package com.alparslanturk.bulbiletini.domain.usecases.user

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.user.UpdateNotificationTokenRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.projectsettings.ProjectSettingsGetWithNameResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.updatenotificationtoken.UpdateNotificationTokenResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class UpdateNotificationTokenUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<UpdateNotificationTokenRequest, UpdateNotificationTokenResponse>() {
    override suspend fun getData(params: UpdateNotificationTokenRequest?): Result<UpdateNotificationTokenResponse> = repository.userUpdateNotificationToken(params?.userId.orEmpty(), params?.notificationToken.orEmpty())
}